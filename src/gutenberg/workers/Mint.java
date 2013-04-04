package gutenberg.workers;

import gutenberg.blocs.AssignmentType;
import gutenberg.blocs.EntryType;
import gutenberg.blocs.ManifestType;
import gutenberg.blocs.PageType;
import gutenberg.blocs.QuizType;

import java.io.BufferedReader;
import java.io.File;
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
        String atmKey = quiz.getQuiz().getValue();
        Path quizDir = null, staging = null, downloads = null, preview = null;
        String quizDirSubpath = String.format("q%s/answer-key", quizId);
        
        if (!Files.exists(mintPath.resolve(quizDirSubpath))) {
            quizDir = Files.createDirectories(mintPath.resolve(quizDirSubpath));
            staging = Files.createDirectory(quizDir.resolve(stagingDirName));
            downloads = Files.createDirectory(quizDir.resolve(downloadsDirName));
            preview = Files.createDirectory(quizDir.resolve(previewDirName));                       
        } else {
            quizDir = mintPath.resolve(quizDirSubpath);
            staging = quizDir.resolve(stagingDirName);
            downloads = quizDir.resolve(downloadsDirName);
            preview = quizDir.resolve(previewDirName);            
        }
        
        Path blueprintTex = staging.resolve("blueprintTex");
        Path answerkeyTex = staging.resolve("answer-key.tex");
        Path rubricTex = staging.resolve("rubric.tex");        

        DocumentWriter blueprintDoc = new DocumentWriter(blueprintTex);
        PageType[] pages = quiz.getPage();
        for (int i = 0; i < pages.length; i++) {

            EntryType[] questions = pages[i].getQuestion();
            if (questions == null)
                continue;

            Path questionTex = null;
            for (EntryType question : questions) {
                String questionId = question.getId();
                blueprintDoc.setCounter(0);
                questionTex = vaultPath.resolve(questionId).resolve("question.tex");
                blueprintDoc.writeTemplate(questionTex);
                Path plotfile = vaultPath.resolve(questionId).resolve("figure.gnuplot");
                Files.copy(plotfile, staging.resolve(String.format("%s.gnuplot", questionId)),
                    StandardCopyOption.REPLACE_EXISTING);
            }
            blueprintDoc.newPage();
        }
        blueprintDoc.close();
        
        DocumentWriter answerkeyDoc = new DocumentWriter(answerkeyTex);        
        answerkeyDoc.writePreamble(quiz.getQuiz().getName());
        answerkeyDoc.printAuthor(quiz.getTeacher().getName());
        answerkeyDoc.beginQuiz();
        answerkeyDoc.printAnswers();        
        answerkeyDoc.writeTemplate(blueprintTex);
        answerkeyDoc.endQuiz();
        answerkeyDoc.close();
        
        Path toMakefile = staging.relativize(sharedPath).
                resolve("makefiles");
        if (!Files.exists(staging.resolve("Makefile")))
            Files.createSymbolicLink(staging.resolve("Makefile"),
                    toMakefile.resolve("quiz.mk"));

        if (make(staging, downloads, preview) != 0) {
            throw new Exception("Problemo! Non-zero return code from make");
        }
        Files.delete(answerkeyTex);
        
        //non-critical activity
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
            make(staging, downloads, null);
        } catch (Exception e) {}
        Files.delete(rubricTex);
        
        return prepareManifest(atmKey, quizDir, downloads, 
            preview);
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
        String testpaperId = assignment.getInstance().getId();
        String atmKey = assignment.getInstance().getValue();
        
        Path quizDir = mintPath.resolve(String.format("q%s", quizId));
        Path assignmentDir = Files.createDirectory(quizDir.resolve(testpaperId));
        
        Path staging = Files.createDirectory(assignmentDir.resolve(stagingDirName));
        Path downloads = Files.createDirectory(assignmentDir.resolve(downloadsDirName));
        
        Path blueprintTex = quizDir.resolve("answer-key").resolve(stagingDirName).
            resolve("blueprintTex");
        File[] plotfiles = quizDir.resolve("answer-key").resolve(stagingDirName)
                .toFile().listFiles(new NameFilter(".gnuplot"));        
        for (int i = 0; i < plotfiles.length; i++) {
            Files.copy(plotfiles[i].toPath(), staging.resolve(plotfiles[i].getName()));
        }

        Path compositeTex = staging.resolve(String.format(nameFormat,
            "assignment", quizId, testpaperId, "tex"));
        DocumentWriter compositeDoc = new DocumentWriter(compositeTex);
        compositeDoc.writePreamble(assignment.getInstance().getName());
        compositeDoc.beginQuiz();

        Path studentDir, studentTestpaperDir;
        EntryType[] students = assignment.getStudents();        
        for (int i = 0; i < students.length; i++) {
            
            String studentId = students[i].getId();
            String studentKey = students[i].getValue();
            String studentName = students[i].getName();
            
            studentDir = mintPath.resolve(String.format("s%s", studentId));
            studentTestpaperDir = Files.createDirectory(studentDir.resolve(
                    String.format("%s-%s", quizId, testpaperId)));
            Path studentStaging = Files.createDirectory(studentTestpaperDir.
                    resolve(stagingDirName));

            Path individualTex = studentStaging.resolve(String.format(nameFormat,
                    studentId, quizId, testpaperId, "tex"));
            DocumentWriter individualDoc = new DocumentWriter(individualTex);
            individualDoc.writePreamble(assignment.getInstance().getName());
            individualDoc.beginQuiz();                       
            
            // QRKey = [TestPaperId(6)][studentIdx(3)][pageNum(1)]            
            String QRKey = String.format("%s%s", atmKey, studentKey);
            
            compositeDoc.printAuthor(studentName);
            individualDoc.printAuthor(studentName);
            
            HashMap<String,String> params = new HashMap<String,String>();
            params.put(ITagLib.insertQR, QRKey);
            params.put(ITagLib.setCounter, "0");            
            Path questionsTex = staging.resolve("questionsTex");            
            DocumentWriter questionsDoc = new DocumentWriter(questionsTex);            
            questionsDoc.writeTemplate(blueprintTex, params);
            
            compositeDoc.writeTemplate(questionsTex);
            individualDoc.writeTemplate(questionsTex);
            
            for (int j = 0; j < plotfiles.length; j++) {
                Files.copy(plotfiles[j].toPath(),
                        studentStaging.resolve(plotfiles[j].getName()));
            }

            int totalPages = quizDir.resolve("answer-key").
                resolve(previewDirName).toFile().list().length;
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
        
        if (make(staging, downloads, null) != 0)
            throw new Exception("Problem! Non-zero return code from make");

        return prepareManifest(atmKey, assignmentDir, downloads, null);
    }
    
    /**
     * Publishes an assignment so that individual students can download 
     * it from their own *home* directories
     * 
     *  @param assignmentId (quiz, assignment)
     *  @return manifest
     *  @throws Exception
     */
    public ManifestType prepTest(AssignmentType assignmentId)
            throws Exception {

        EntryType quizId = assignmentId.getQuiz();
        EntryType instanceId = assignmentId.getInstance();
        EntryType[] studentIds = assignmentId.getStudents();

        ManifestType manifest = new ManifestType();
        manifest.setRoot(mintPath.toString());
        
        Path testpaperDir, stagingDir, downloadsDir,
            previewDir;
        for (EntryType studentId: studentIds) {
            
            testpaperDir = mintPath.resolve(String.format("s%s/%s-%s",
                    studentId.getId(), quizId.getId(), instanceId.getId()));            
            stagingDir = testpaperDir.resolve(stagingDirName);
            downloadsDir = Files.createDirectory(
                    testpaperDir.resolve(downloadsDirName)); 
            previewDir = Files.createDirectory(
                    testpaperDir.resolve(previewDirName));
            
            // 3. Link Makefiles and make the PDFs
            Path rel = stagingDir.relativize(sharedPath);
            Files.createSymbolicLink(stagingDir.resolve("Makefile"),
                    rel.resolve("makefiles/quiz.mk"));
            
            if (make(stagingDir, downloadsDir, previewDir) == 0) {
                EntryType PDF = new EntryType();
                PDF.setId(String.format("%s-%s-%s.pdf", studentId.getId(), 
                        quizId.getId(), instanceId.getId()));
                manifest.addDocument(PDF);
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
        
    private ManifestType prepareManifest(String atmKey, Path root, 
        Path downloads, Path preview) throws Exception {
        ManifestType manifest = atm.deposit(root, atmKey);        
        
        if (downloads != null) {
            Path rel = root.relativize(downloads);            
            EntryType[] documents = new EntryType[1];
            if (!Files.exists(downloads.resolve("answer-key.pdf"))) {
                throw new Exception("answer-key.pdf is missing!");
            }
            documents[0] = new EntryType();
            documents[0].setId(rel.resolve("answer-key.pdf").toString());
            manifest.setDocument(documents);
        }
        
        if (preview != null) {            
            Path rel = root.relativize(preview);
            String[] filenames = preview.toFile().list();
            if (filenames.length == 0)
                throw new Exception("All preview files not prepared");
            
            EntryType[] images = new EntryType[filenames.length];
            for (int i = 0; i < images.length; i++) {
                images[i] = new EntryType();
                images[i].setId(rel.resolve(filenames[i]).toString());
            }
            manifest.setImage(images);
        }

        return manifest;
    }
    
    private final String stagingDirName = "staging",
        downloadsDirName = "downloads", 
        previewDirName = "preview",
        nameFormat = "%s-%s-%s.%s";

}
