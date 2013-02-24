package gutenberg.workers;

import gutenberg.blocs.AssignmentType;
import gutenberg.blocs.EntryType;
import gutenberg.blocs.ManifestType;
import gutenberg.blocs.PageType;
import gutenberg.blocs.QuizType;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Mint {

    public Mint(Config config) throws Exception {
        sharedPath = new File(config.getPath(Resource.shared)).toPath();
        mintPath = new File(config.getPath(Resource.mint)).toPath();
        vault = new Vault(config);
        atm = ATM.instance(config);
        locker = new Locker(config);
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

        // 0. Set up directories
        String quizId = quiz.getQuiz().getId();
        Path quizDir = null, staging = null, downloads = null, preview = null;
        if (!Files.exists(mintPath.resolve(quizId))) {
            quizDir = Files.createDirectories(mintPath.resolve(quizId)
                    .resolve("answer-key"));
            staging = Files.createDirectory(quizDir.resolve("staging"));
            downloads = Files.createDirectory(quizDir.resolve("downloads"));
            preview = Files.createDirectory(quizDir.resolve("preview"));                       
        } else {
            quizDir = mintPath.resolve(quizId).resolve("answer-key");
            staging = quizDir.resolve("staging");
            downloads = quizDir.resolve("downloads");
            preview = quizDir.resolve("preview");                       
        }
        
        // generate symbolic link to quiz in ATM
        ManifestType manifest = atm.deposit(quizDir.getParent());

        // 1. write latex
        PrintWriter blueprintTex = new PrintWriter(Files.newBufferedWriter(
                staging.resolve("blueprintTex"), StandardCharsets.UTF_8,
                StandardOpenOption.CREATE));
        
        writePreamble(blueprintTex, quiz.getSchool().getName(), quiz
                .getTeacher().getName());
        
        beginDoc(blueprintTex);
        blueprintTex.println(printanswers);

        PageType[] pages = quiz.getPage();        
        for (int i = 0; i < pages.length; i++) {

            EntryType[] questions = pages[i].getQuestion();
            if (questions == null)
                continue;

            for (EntryType question : questions) {
                setQuestion(question.getId(), blueprintTex, staging);
            }
            blueprintTex.println(newpage);
        }
        endDoc(blueprintTex);
        blueprintTex.close();
        
        // 2. Link Makefiles and make PDFs
        Path toMakefile = staging.relativize(sharedPath).
                resolve("makefiles");
        if (!Files.exists(staging.resolve("Makefile")))
            Files.createSymbolicLink(staging.resolve("Makefile"),
                    toMakefile.resolve("quiz.mk"));

        Files.copy(staging.resolve("blueprintTex"), 
                staging.resolve("answer-key.tex"));
        if (make(staging, downloads, preview) != 0) {
            throw new Exception("Problemo! Non-zero return code from make");
        }
        Files.delete(staging.resolve("answer-key.tex"));

        // 3. Populate manifest
        String[] filenames = preview.toFile().list();
        if (filenames.length == 0) {
            throw new Exception("All preview files not prepared");
        }
        EntryType[] images = new EntryType[filenames.length];
        for (int i = 0; i < images.length; i++) {
            images[i] = new EntryType();
            images[i].setId(filenames[i]);
        }
        manifest.setImage(images);

        EntryType[] documents = new EntryType[1];
        if (!Files.exists(downloads.resolve("answer-key.pdf"))) {
            throw new Exception("answer-key.pdf is missing!");
        }
        documents[0] = new EntryType();
        documents[0].setId("answer-key.pdf");
        manifest.setDocument(documents);
        
        // write other TeX files for supplementary document generation
        List<String> lines = Files.readAllLines(
                staging.resolve("blueprintTex"), StandardCharsets.UTF_8);
        PrintWriter rubricTex = new PrintWriter(Files.newBufferedWriter(
                staging.resolve("rubric.tex"), StandardCharsets.UTF_8,
                StandardOpenOption.CREATE));
        for (String line : lines) {
            rubricTex.println(line);
            if (line.startsWith(printanswers)) {
                rubricTex.println(printRubric);
            }
        }
        rubricTex.close();
        make(staging, downloads, null);
        Files.delete(staging.resolve("rubric.tex"));
        
        return manifest;
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

        // 0. Set up directories
        String quizId = assignment.getQuiz().getId();
        String testpaperId = assignment.getInstance().getId();
        
        Path quizDir = mintPath.resolve(quizId);
        Path assignmentDir = Files.createDirectory(mintPath.
                resolve(quizId).resolve(testpaperId));
        Path staging = Files.createDirectory(assignmentDir.
                resolve("staging"));
        Path downloads = Files.createDirectory(assignmentDir.
                resolve("downloads"));
        // generate symbolic link to test paper in ATM
        ManifestType manifest = atm.deposit(assignmentDir);

        // 1. Copy blueprint answer-key.tex
        List<String> linesList = Files.readAllLines(
                quizDir.resolve("answer-key").resolve("staging").
                resolve("blueprintTex"), StandardCharsets.UTF_8);
        String[] lines = linesList.toArray(new String[linesList.size()]);

        // copy plot files from blueprint folder
        File[] plotfiles = quizDir.resolve("answer-key").resolve("staging")
                .toFile().listFiles(new NameFilter(".gnuplot"));
        for (int i = 0; i < plotfiles.length; i++) {
            Files.copy(plotfiles[i].toPath(), staging.resolve(plotfiles[i].getName()));
        }

        // 2. Write latex files for each student's test paper
        String nameFormat = "%s-%s-%s.%s";
        Path compositeTex = staging.resolve(String.format(nameFormat,
                "assignment", quizId, testpaperId, "tex"));
        PrintWriter composite = new PrintWriter(
                Files.newBufferedWriter(compositeTex, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE));

        // Randomized manifest root is needed for key file
        String atmKey = new File(manifest.getRoot()).toPath().getFileName()
                .toString();

        Path keyFile = assignmentDir.resolve("keyFile");
        PrintWriter keyFileWriter = new PrintWriter(Files.newBufferedWriter(
                keyFile, StandardCharsets.UTF_8, StandardOpenOption.CREATE));

        HashSet<String> keys = new HashSet<String>();
        int MAX_2_DIG_BASE36_NUM = 1296;
        Random random = new Random();
        Random dice = new Random();
        
        String pseudoStudentId = null; 
        EntryType[] students = assignment.getStudents();

        for (int i = 0; i < students.length; i++) {
            
            Path studentDir = this.mintPath.resolve("s" + students[i].getId());
            if (!Files.exists(studentDir))
                Files.createDirectory(studentDir);
            Path testpaperDir = Files.createDirectory(studentDir.resolve(
                    String.format("%s-%s", quizId, testpaperId)));
            Path studentStaging = Files.createDirectory(testpaperDir.
                    resolve("staging"));

            Path singleTex = studentStaging.resolve(String.format(nameFormat,
                    students[i].getId(), quizId, testpaperId, "tex"));
            PrintWriter single = new PrintWriter(Files.newBufferedWriter(
                    singleTex, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE));
            
            pseudoStudentId = Integer.toString(
                    random.nextInt(MAX_2_DIG_BASE36_NUM), Character.MAX_RADIX);
            while (keys.contains(pseudoStudentId)) {
                pseudoStudentId = Integer.toString(
                random.nextInt(MAX_2_DIG_BASE36_NUM), Character.MAX_RADIX);
            }
            keys.add(pseudoStudentId);
            pseudoStudentId = Integer.toString(i, Character.MAX_RADIX);

            // QRKey = [TestPaperId(6)][studentIdx(2)][pageNum(1|2)]            
            String QRKey = String.format("%s%2s", atmKey, pseudoStudentId)
                    .replace(' ', '0');
            
            replicateBlueprint(lines, composite, single, QRKey, 
                    students[i].getName(), (i == 0), dice) ;
            
            for (int j = 0; j < plotfiles.length; j++) {
                Files.copy(plotfiles[j].toPath(),
                        studentStaging.resolve(plotfiles[j].getName()));
            }

            String baseQR = baseQR(students[i], assignment);
            String QRKeyVal = null;
            int totalPages = quizDir.resolve("answer-key").resolve("preview").
                    toFile().list().length;
            for (int j = 0; j < totalPages; j++) {
                QRKeyVal = String.format("%s%s:%s-0-%s-%s", QRKey, j+1, baseQR, 
                        j+1, totalPages);
                keyFileWriter.println(QRKeyVal);
            }

            if (totalPages % 2 != 0) {
                insertBlankPage(composite);
            }

            resetQuestionNumbering(composite);
            resetPageNumbering(composite);
        }
        endDoc(composite);
        composite.close();
        keyFileWriter.close();

        // 3. Link Makefiles and make the PDFs
        Path toMakefile = staging.relativize(sharedPath).
                resolve("makefiles");
        Files.createSymbolicLink(staging.resolve("Makefile"),
                toMakefile.resolve("quiz.mk"));
        
        if (make(staging, downloads, null) != 0)
            throw new Exception("Problem! Non-zero return code from make");

        // 4. Prepare manifest
        EntryType[] documents = new EntryType[1];
        String compositePDF = String.format(nameFormat, 
                "assignment", quizId, testpaperId, "pdf");
        if (!Files.exists(downloads.resolve(compositePDF))) {
            throw new Exception(String.format("Composite %s + missing!",
                    compositePDF));
        }
        documents[0] = new EntryType();
        documents[0].setId(compositePDF);
        manifest.setDocument(documents);
        
        // 5. Make room in the locker for receiving scans
        locker.makeRoom(quizId, testpaperId);
        
        return manifest;
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
            stagingDir = testpaperDir.resolve("staging");
            downloadsDir = Files.createDirectory(
                    testpaperDir.resolve("downloads")); 
            previewDir = Files.createDirectory(
                    testpaperDir.resolve("preview"));
            
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
        Path studentDir = Files.createDirectory(
                mintPath.resolve("s" + student.getId()));        
        return atm.deposit(studentDir);
    }
    
    private Path  sharedPath, mintPath;
    private Locker locker;
    private ATM   atm;
    private Vault vault;

    private void setQuestion(String questionId, PrintWriter writer,
            Path staging) throws Exception {
        writer.println("\\setcounter{rolldice}{0}");
        String content = vault.getContent(questionId, "question.tex")[0];
        writer.println(content);
        Path plotfile = vault.getFiles(questionId, "figure.gnuplot")[0]
                .toPath();
        Files.copy(plotfile, staging.resolve(questionId + ".gnuplot"), StandardCopyOption.REPLACE_EXISTING);
    }

    private void replicateBlueprint(String[] lines, PrintWriter composite,
            PrintWriter single, String baseQRKey, String author,
            boolean firstPass, Random dice) 
   {
        for (int j = 0; j < lines.length; j++) {

            String line = lines[j];
            String trimmed = line.trim();

            if (trimmed.startsWith("%")) { // => a comment
                continue;
            } else if (trimmed.startsWith(printanswers)) {
                continue;              
            } else if (trimmed.startsWith("\\setcounter{rolldice}")) {
                line = String.format("\\setcounter{rolldice}{%d}", 
                        dice.nextInt(4));
            } else if (trimmed.startsWith(insertQR)) {
                line = line.replace("QRC", baseQRKey + pageNumber);
            } else if (trimmed.startsWith(docAuthor)) {
                line = docAuthor + "{" + author + "}"; // change the name
            } 
            
            // This is the only chance the per-student TeX has to
            // get content. So, grab it ...
            single.println(line);

            if (trimmed.startsWith(beginDocument)
                    || trimmed.startsWith(beginQuestions)
                    || trimmed.startsWith(docClass)
                    || trimmed.startsWith(fancyfoot)
                    || trimmed.startsWith(school)
                    || trimmed.startsWith(usepackage)) {
                if (firstPass)
                    composite.println(line);
            } else if (trimmed.startsWith(endDocument)
                    || trimmed.startsWith(endQuestions)) {
                continue; // will be printed once, later
            } else {
                composite.println(line);
            }
        }
        single.close();
        composite.flush();
    }

    private void writePreamble(PrintWriter writer, String school, String author)
            throws Exception {
        List<String> file = Files.readAllLines(
                sharedPath.resolve("templates/preamble.tex"), StandardCharsets.UTF_8);
        String[] lines = new String[file.size()];
        lines = file.toArray(lines);

        for (int j = 0; j < lines.length; j++) {
            String line = lines[j];
            String trimmed = line.trim();

            if (trimmed.startsWith(this.school)) {
                writer.println(this.school + "{" + school + "}");
            } else if (trimmed.startsWith(docAuthor)) {
                if (author == null)
                    author = "Gutenberg";
                writer.println(docAuthor + "{" + author + "}");
            } else { // write whatever else is in preamble.tex
                writer.println(line);
            }
        }
    }

    private void beginDoc(PrintWriter writer) throws Exception {
        writer.println(beginDocument);
        writer.println(beginQuestions);
    }

    private void endDoc(PrintWriter writer) throws Exception {
        writer.println(endQuestions);
        writer.println(endDocument);
    }

    private void resetPageNumbering(PrintWriter writer) throws Exception {
        writer.println("\\setcounter{page}{1}");
    }

    private void resetQuestionNumbering(PrintWriter writer) throws Exception {
        writer.println("\\setcounter{question}{0}");
    }

    private void insertBlankPage(PrintWriter writer) {
        writer.print("\\begingroup");
        writer.print("\\centering For rough work only. Will NOT be graded \\\\ ");
        writer.println("\\endgroup");
        writer.println(insertQR + BLANK_PAGE_CODE);
        writer.println(newpage);
    }
    
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
                stream = Files.newDirectoryStream(workingDir, "page-*.jpeg");
                for (Path entry: stream) {
                    target = previewsDir.resolve(
                            entry.getFileName().toString().replace("anskey", ""));
                    Files.move(entry, target, StandardCopyOption.REPLACE_EXISTING);
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

    private String baseQR(EntryType student, AssignmentType assignment)
            throws Exception {
        String quiz = assignment.getQuiz().getId();
        String testpaper = assignment.getInstance().getId();
        String name = student.getName().trim().toLowerCase().
                replaceAll("\\s+", "-");
        String[] tokens = name.split("-");
        switch (tokens.length) {
        case 1:
            name = String.format("%s-%s", tokens[0], tokens[0]);
            break;
        case 2:
            break;
        default:            
            name = String.format("%s-%s", tokens[0], tokens[1]);
        }
        String id = student.getId();
        return String.format("%s-%s-%s-%s", id, name, quiz, testpaper);
    }

    private final String printanswers = "\\printanswers",
            pageNumber = "\\thepage", docAuthor = "\\DocAuthor",
            newpage = "\\nextpg", usepackage = "\\usepackage",
            fancyfoot = "\\fancyfoot", beginDocument = "\\begin{document}",
            beginQuestions = "\\begin{questions}", school = "\\School",
            docClass = "\\documentclass", insertQR = "\\insertQR",
            endQuestions = "\\end{questions}", endDocument = "\\end{document}",
            printRubric = "\\printrubric", BLANK_PAGE_CODE = "{0}";

}
