package gutenberg.workers;

import gutenberg.blocs.AssignmentType;
import gutenberg.blocs.EntryType;
import gutenberg.blocs.ManifestType;
import gutenberg.blocs.PageType;
import gutenberg.blocs.QuizType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

public class Mint {
    
    public Mint(Config config) throws Exception {
        sharedPath = config.getPath(Resource.shared);
        mintPath = config.getPath(Resource.mint);
        vaultPath = config.getPath(Resource.vault);
        atm = ATM.instance(config);
    }

    /**
     * Creates PDFs and preview jpegs for a quiz. Steps are: 0. Set up
     * directories 1. Write latex 2. Link Makefiles 3. Make (the PDFs) 4.
     * Populate and return manifest
     * 
     * @param quiz
     * @return manifest containing generated files
     * @throws Exception
     * 
     */
    public ManifestType generate(QuizType quiz) throws Exception {

        String quizId = quiz.getQuiz().getId();
        String quizKey = quiz.getQuiz().getValue();
        
        Path quizDir = mintPath.resolve("quiz").resolve(quizKey);
        Path staging = quizDir.resolve(stagingDirName);
        Path preview = quizDir.resolve(previewDirName);
        
        if (!Files.exists(quizDir)) {
            Files.createDirectory(quizDir);
            Files.createDirectory(staging);
            Files.createDirectory(preview);
        }
        
        Path blueprintTex = staging.resolve("blueprintTex");
        DocumentWriter blueprintDoc = new DocumentWriter(blueprintTex);
        PageType[] pages = quiz.getPage();
        for (int i = 0; i < pages.length; i++) {

            EntryType[] questions = pages[i].getQuestion();
            if (questions == null)
                continue;

            for (EntryType question : questions) {
                blueprintDoc.setCounter(0);
                copyQuestion(staging, blueprintDoc, question.getId());
            }
            blueprintDoc.newPage();
        }
        blueprintDoc.close();
        
        Path answerkeyTex = staging.resolve(String.format(nameFormat,
            "answer", "key", quizId, "tex"));
        DocumentWriter answerkeyDoc = new DocumentWriter(answerkeyTex);        
        answerkeyDoc.writePreamble(quiz.getQuiz().getName());
        answerkeyDoc.printAuthor(quiz.getTeacher().getName());
        answerkeyDoc.beginQuiz();
        answerkeyDoc.printAnswers();        
        answerkeyDoc.writeTemplate(blueprintTex);
        answerkeyDoc.endQuiz();
        answerkeyDoc.close();
        
        Path toMakefile = staging.relativize(sharedPath).resolve("makefiles");
        if (!Files.exists(staging.resolve("Makefile")))
            Files.createSymbolicLink(staging.resolve("Makefile"),
                    toMakefile.resolve("quiz.mk"));

        if (make(staging, quizDir, preview) != 0) {
            throw new Exception("Problemo! Non-zero return code from make");
        }
        Files.delete(answerkeyTex);
        
        //non-critical activity
        Path rubricTex = staging.resolve(String.format(nameFormat,
            "rubric", "key", quizId, "tex"));
        DocumentWriter rubricDoc = new DocumentWriter(rubricTex);        
        rubricDoc.writePreamble(quiz.getQuiz().getName());
        rubricDoc.printAuthor(quiz.getTeacher().getName());
        rubricDoc.beginQuiz();
        rubricDoc.printRubric();
        rubricDoc.printAnswers();        
        rubricDoc.writeTemplate(blueprintTex);
        rubricDoc.endQuiz();
        rubricDoc.close();
        try {
            make(staging, quizDir, null);
        } catch (Exception e) {}
        Files.delete(rubricTex);
        
        ManifestType manifest = new ManifestType();
        manifest.setRoot(mintPath.relativize(quizDir).toString());
        
        return prepareManifest(quizDir, quizDir, preview);
    }

    /**
     * Create an assignment (test paper). Steps: 0. Set up directories 1. Copy
     * blueprint from answer-key.tex 2. Write composite and per student latex
     * and QRCode key files 3. Make (the PDFs) 4. Populate and return manifest
     * 
     * @param assignment
     * @return manifest
     * @throws Exception
     */
    public ManifestType generate(AssignmentType assignment) throws Exception {

        String quizId = assignment.getQuiz().getId();
        String quizKey = assignment.getQuiz().getValue();
        String testpaperId = assignment.getInstance().getId();
        String assignmentKey = assignment.getInstance().getValue();
        
        Path quizDir = mintPath.resolve("quiz").resolve(quizKey);
        Path assignmentDir = mintPath.resolve("ws").resolve(assignmentKey);
        Path staging = assignmentDir.resolve(stagingDirName);
        Path studentsDir = assignmentDir.resolve("students");
        
        Files.createDirectory(assignmentDir);        
        Files.createDirectory(staging);
        
        Path quizStaging = quizDir.resolve(stagingDirName);
        DirectoryStream<Path> plotfiles = 
            Files.newDirectoryStream(quizStaging, "*.gnuplot");
        for (Path plotfile : plotfiles) {
            Files.copy(plotfile, staging.resolve(plotfile.getFileName()));
        }
        plotfiles.close();
        
        Path compositeTex = staging.resolve(String.format(nameFormat,
            "assignment", quizId, testpaperId, "tex"));
        DocumentWriter compositeDoc = new DocumentWriter(compositeTex);
        compositeDoc.writePreamble(assignment.getQuiz().getName());
        compositeDoc.beginQuiz();

        Path studentDir, studentStaging;
        Path blueprintTex = quizStaging.resolve("blueprintTex");
        EntryType[] students = assignment.getStudents();        
        for (int i = 0; i < students.length; i++) {
            
            String studentId = students[i].getId();
            String studentKey = students[i].getValue();
            String studentName = students[i].getName();
            
            studentDir = studentsDir.resolve(studentKey);
            studentStaging = studentDir.resolve(stagingDirName);
            Files.createDirectories(studentStaging);
            
            plotfiles = Files.newDirectoryStream(quizStaging, "*.gnuplot");
            for (Path plotfile : plotfiles) {
                Files.copy(plotfile,
                    studentStaging.resolve(plotfile.getFileName()));
            }
            plotfiles.close();

            Path individualTex = studentStaging.resolve(
                String.format(nameFormat, studentId, quizId, testpaperId, "tex"));
            DocumentWriter individualDoc = new DocumentWriter(individualTex);
            individualDoc.writePreamble(assignment.getInstance().getName());
            individualDoc.beginQuiz();                       
            
            compositeDoc.printAuthor(studentName);
            individualDoc.printAuthor(studentName);
            
            // QRKey = [TestPaperId(6)][studentIdx(3)][pageNum(1)]            
            String QRKey = String.format("%s%s", assignmentKey, studentKey);
            
            HashMap<String,String> params = new HashMap<String,String>();
            params.put(ITagLib.insertQR, QRKey);
            params.put(ITagLib.setCounter, "0");
            Path questionsTex = staging.resolve("questionsTex");            
            DocumentWriter questionsDoc = new DocumentWriter(questionsTex);            
            questionsDoc.writeTemplate(blueprintTex, params);
            questionsDoc.close();
            
            compositeDoc.writeTemplate(questionsTex);
            individualDoc.writeTemplate(questionsTex);
            
            int totalPages = quizDir.resolve(previewDirName).
                toFile().list().length;
            if (totalPages % 2 != 0) compositeDoc.insertBlankPage();

            compositeDoc.resetQuestionNumbering();
            compositeDoc.resetPageNumbering();
            individualDoc.close();
        }
        compositeDoc.endQuiz();
        compositeDoc.close();

        // 3. Link Makefiles and make the PDFs
        Path toMakefile = staging.relativize(sharedPath).
                resolve("makefiles");
        Files.createSymbolicLink(staging.resolve("Makefile"),
                toMakefile.resolve("quiz.mk"));
        
        if (make(staging, assignmentDir, null) != 0)
            throw new Exception("Problem! Non-zero return code from make");

        return prepareManifest(assignmentDir, assignmentDir, null);
    }
    
    /**
     * Publishes an assignment so that individual students can download 
     * it from their own *home* directories
     * 
     *  @param assignmentId (quiz, assignment)
     *  @return manifest
     *  @throws Exception
     */
    public ManifestType prepTest(AssignmentType assignment)
            throws Exception {

        String assignmentValue = assignment.getInstance().getValue();

        ManifestType manifest = null;
        Path assignmentDir = mintPath.resolve("ws").resolve(assignmentValue);
        Path studentDir, stagingDir, previewDir;        
        for (EntryType student: assignment.getStudents()) {
            
            String studentKey = student.getValue();
            studentDir = assignmentDir.resolve("student").resolve(studentKey);
            stagingDir = studentDir.resolve(stagingDirName);
            previewDir = studentDir.resolve(previewDirName);
            
            Files.createDirectory(previewDir);
            
            Path rel = stagingDir.relativize(sharedPath);
            Files.createSymbolicLink(stagingDir.resolve("Makefile"),
                    rel.resolve("makefiles/quiz.mk"));
            
            if (make(stagingDir, assignmentDir, previewDir) == 0) {
                manifest = prepareManifest(assignmentDir, studentDir, previewDir);
            } else {
                throw new Exception("Eeee! Make returned non-zero!");
            }
        }
        return manifest;    
    }

    public ManifestType generateStudentCode(EntryType student) 
            throws Exception {
        Path studentDir = mintPath.resolve(String.format("s%s", student.getId()));
        if (!Files.exists(studentDir)) {
            Files.createDirectory(studentDir);
        }
        return atm.deposit(studentDir);
    }
    
    private Path  sharedPath, mintPath, vaultPath;
    private ATM   atm;
    
    private int make(Path workingDir, Path downloadsDir, Path previewsDir)
            throws Exception {
        ProcessBuilder pb = new ProcessBuilder("make");
        if (previewsDir == null) {
            pb.command().add("pdf");
        }

        pb.directory(workingDir.toFile());
        pb.redirectErrorStream(true);

        Process process = pb.start();
        BufferedReader messages = new BufferedReader(new InputStreamReader(
                process.getInputStream()));

        String line = null;
        while ((line = messages.readLine()) != null) {
            System.out.println(line);
        }
        
        int retCode = 0;
        if ((retCode = process.waitFor()) == 0) {
            
            Path target = null;
            DirectoryStream<Path> stream = 
                    Files.newDirectoryStream(workingDir, "*.pdf");
            for (Path entry: stream) {
                target = downloadsDir.resolve(entry.getFileName());
                Files.move(entry, target, StandardCopyOption.REPLACE_EXISTING);
            }
            stream.close();
            
            if (previewsDir != null) {
                stream = Files.newDirectoryStream(previewsDir, "*.jpeg");
                for (Path entry: stream) Files.delete(entry);
                stream.close();            
                
                stream = Files.newDirectoryStream(workingDir, "*.jpeg");
                for (Path entry: stream) {
                    target = previewsDir.resolve(entry.getFileName());
                    Files.move(entry, target);
                }
                stream.close();
            }
        }
        
        ProcessBuilder pClean = new ProcessBuilder("make", "clean");
        pClean.directory(workingDir.toFile());
        Process clean = pClean.start();
        clean.waitFor();

        return retCode;
    }
        
    private void copyQuestion(Path staging, DocumentWriter blueprintDoc,
        String questionId) throws Exception {
        Path questionTex, plotfile, target;
        Path questionDir = vaultPath.resolve(questionId);
        questionTex = questionDir.resolve("question.tex");
        blueprintDoc.writeTemplate(questionTex);
        plotfile = questionDir.resolve("figure.gnuplot");
        if (Files.exists(plotfile)) {
            target = staging.resolve(
                String.format("%s.gnuplot", questionId));
            Files.copy(plotfile, target,
                StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private ManifestType prepareManifest(Path root,  
        Path downloads, Path preview) throws Exception {        
        ManifestType manifest = new ManifestType();
        manifest.setRoot(mintPath.relativize(root).toString());
        
        if (downloads != null) {
            DirectoryStream<Path> PDFs = 
                Files.newDirectoryStream(downloads, "*.pdf");
            for (Path PDF : PDFs) {
                EntryType document = new EntryType();
                document.setId(root.relativize(PDF).toString());
                manifest.addDocument(document);
            }
            PDFs.close();
            if (manifest.getDocument().length == 0)
                throw new Exception("Problema! No documents for download");
        }
        
        if (preview != null) {
            DirectoryStream<Path> jpegs = 
                Files.newDirectoryStream(downloads, "*.jpeg");
            for (Path jpeg : jpegs) {
                EntryType img = new EntryType();
                img.setId(root.relativize(jpeg).toString());
                manifest.addImage(img);
            }
            jpegs.close();
            if (manifest.getDocument().length == 0)
                throw new Exception("Problema! No images for preview");
        }

        return manifest;
    }
    
    private final String stagingDirName = "staging",
        previewDirName = "preview",
        nameFormat = "%s-%s-%s.%s";

}
