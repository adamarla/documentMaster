package gutenberg.workers;

import gutenberg.blocs.AssignmentType;
import gutenberg.blocs.ExamType;
import gutenberg.blocs.MkFlagsType;
import gutenberg.blocs.QFlagsType;
import gutenberg.blocs.TexFlagsType;
import gutenberg.blocs.WFlagsType;
import gutenberg.blocs.EntryType;
import gutenberg.blocs.ManifestType;
import gutenberg.blocs.QuizType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.io.File;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;

public class Mint implements ITagLib {

    public Mint(Config config) throws Exception {
        commonPath = config.getPath(Resource.common);
        sharedPath = config.getPath(Resource.shared);
        mintPath = config.getPath(Resource.mint);
        vaultPath = config.getPath(Resource.vault);
        latexRoot = config.getPath(Resource.latexRoot);
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
    public int make(Path dir, boolean skel) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("/usr/local/bin/make");

        String ltx = latexRoot.toString();
        pb.directory(dir.toFile());

        Path pthLog = dir.resolve("logfile");
        File logf = new File(pthLog.toString());
        Process p;

        pb.redirectErrorStream(true);
        if (skel) {
            // pb.redirectOutput(logf);
            pb.command().add("skel");
        } else if (ltx != null) {
            // pb.redirectOutput(Redirect.appendTo(logf));
        } else {
            throw new Exception("Mint.java:61 LATEX_ROOT not inferred");
        }
        p = pb.start();
        BufferedReader messages =
                new BufferedReader(new InputStreamReader(
                        p.getInputStream()));

        String line = null;
        while ((line = messages.readLine()) != null) {
            System.out.println(line);
        }

        int ret = p.waitFor();
        return ret ;
    }

    public int compileTex(MkFlagsType f) throws Exception {
        Path path = mintPath.resolve(f.getPath());
        System.out.println(" ---> Starting make") ;
        int ret = this.make(path, false);
        System.out.println(" ---> Ending make --> " + ret) ;
        return ret;
    }

    public String errorOut(MkFlagsType f) throws Exception {
        String target = f.getPath();
        String lnk = target.replaceAll("/", "_");
        Path to = mintPath.resolve(target);
        Path errFolder = mintPath.resolve("errors");

        if (!Files.exists(errFolder))
            Files.createDirectory(errFolder);

        Path softLnk = errFolder.resolve(lnk);
        if (!Files.exists(softLnk))
            Files.createSymbolicLink(softLnk, to);        
        return lnk;
    }

    public int createTex(TexFlagsType f) throws Exception {
        Path target = mintPath.resolve(f.getTarget());
        String mode = f.getMode();
        String[] imports = f.getImports();
        QFlagsType qflags = f.isQFlagsSpecified() ? f.getQFlags() : null;
        WFlagsType wflags = f.isWFlagsSpecified() ? f.getWFlags() : null;

        // Prepare sandbox
        Files.createDirectories(target);

        if (!Files.exists(target.resolve("Makefile")))
            Files.createSymbolicLink(target.resolve("Makefile"),
                    commonPath.resolve("makefiles/compile.mk"));
        if (!Files.exists(target.resolve("shell-script")))
            Files.createSymbolicLink(target.resolve("shell-script"),
                    commonPath.resolve("scripts/compile.sh"));

        // Write the blueprint file
        PrintWriter out;
        String blueprint = target.resolve("blueprint").toString();
        out = new PrintWriter(new FileWriter(blueprint));

        out.println("mode: " + mode);
        for (int i = 0; i < imports.length; i++) {
            out.println("import: " + imports[i]);
        }

        if (f.getAuthor() != null)
            out.println("author: " + f.getAuthor());

        if (qflags != null) {
            out.println("title: " + qflags.getTitle());
            out.println("pageBreaks: " + qflags.getPageBreaks());
            out.println("versionTriggers: " + qflags.getVersionTriggers());
        } else if (wflags != null) {
            out.println("responses: " + wflags.getResponses());
            out.println("versions: " + wflags.getVersions());
        }
        out.close();
        this.make(target, true);
        return 0;
    }
    
    public ManifestType destroyExam(ExamType exam) throws Exception {
        String examUid = exam.getUid();
        Path examDir = mintPath.resolve(examUid);
        String[] worksheetUid = exam.getWorksheetUid();
        for (String wsUid : worksheetUid) {
            Path wsDir = examDir.resolve(wsUid).getParent();
            System.out.println("Deleting: " + wsDir.getFileName());
            Files.walkFileTree(wsDir, new SimpleFileVisitor<Path>() {           
                @Override
                public FileVisitResult visitFile(Path file,
                        BasicFileAttributes attrs) throws IOException {
                    System.out.println("Deleting file: " + file);
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }           
                @Override
                public FileVisitResult postVisitDirectory(Path dir,
                        IOException exc) throws IOException {           
                    System.out.println("Deleting dir: " + dir);
                    if (exc == null) {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    } else {
                        throw exc;
                    }
                }
            });
        }        
        ManifestType manifest = new ManifestType();
        manifest.setRoot(examUid);
        return manifest;
    }

    public ManifestType generate(QuizType quiz) throws Exception {

        String quizId = quiz.getQuiz().getId();
        String quizKey = quiz.getQuiz().getValue();

        Path quizDir = mintPath.resolve(quizDirName).resolve(quizKey);
        Path staging = quizDir.resolve(stagingDirName);
        Path preview = quizDir.resolve(previewDirName);
        Path blueprintTex = staging.resolve(blueprintFile);

        if (!Files.exists(quizDir)) {
            Files.createDirectory(quizDir);
            Files.createDirectory(staging);
            Files.createDirectory(preview);
        } else {
            Files.deleteIfExists(blueprintTex);
        }

        DocumentWriter blueprintDoc = new DocumentWriter(blueprintTex);
        EntryType[] questions = quiz.getQuestions();
        for (EntryType question : questions) {
            copyQuestion(staging, blueprintDoc, question.getId());
        }
        blueprintDoc.close();

        Path answerkeyTex =
                staging.resolve(String.format(nameFormat, "answer", "key",
                        quizId, "tex"));
        DocumentWriter answerkeyDoc = new DocumentWriter(answerkeyTex);
        answerkeyDoc.writePreamble(quiz.getQuiz().getName());
        answerkeyDoc.beginDocument(quiz.getBreaks(), quiz.getVersionTriggers());
        answerkeyDoc.beginQuiz(quiz.getTeacher().getName(),
                new int[questions.length], null);
        answerkeyDoc.printAnswers();
        answerkeyDoc.writeTemplate(blueprintTex);
        answerkeyDoc.endQuiz();
        answerkeyDoc.endDocument();
        answerkeyDoc.close();

        Path toMakefile =
                staging.relativize(sharedPath).resolve(makefilesDir)
                        .resolve(quizMakefile);
        if (!Files.exists(staging.resolve(makefile)))
            Files.createSymbolicLink(staging.resolve(makefile), toMakefile);

        if (make(staging, quizDir, preview) != 0) {
            throw new Exception("Problemo! Non-zero return code from make");
        }

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
        boolean publish = assignment.getPublish();

        Path quizDir = mintPath.resolve(quizDirName).resolve(quizKey);
        Path assignmentDir =
                mintPath.resolve(worksheetDirName).resolve(assignmentKey);
        Path staging = assignmentDir.resolve(stagingDirName);
        Path studentsDir = assignmentDir.resolve(studentDirName);

        if (!Files.exists(assignmentDir)) {
            Files.createDirectory(assignmentDir);
            Files.createDirectory(staging);
        }

        Path quizStaging = quizDir.resolve(stagingDirName);
        DirectoryStream<Path> plotfiles =
                Files.newDirectoryStream(quizStaging, "*.gnuplot");
        for (Path plotfile : plotfiles) {
            Files.copy(plotfile, staging.resolve(plotfile.getFileName()),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        plotfiles.close();

        Path blueprintTex = quizStaging.resolve(blueprintFile);
        Path quizTex =
                quizStaging.resolve(String.format(nameFormat, "answer", "key",
                        quizId, "tex"));
        String[] lines =
                Files.readAllLines(quizTex, StandardCharsets.UTF_8).toArray(
                        new String[0]);
        int[] breaks = getList(lines, setPageBreaks);
        int[] triggers = getList(lines, setVersionTriggers);
        Path compositeTex =
                staging.resolve(String.format(nameFormat, "assignment", quizId,
                        testpaperId, "tex"));
        DocumentWriter compositeDoc = new DocumentWriter(compositeTex);
        compositeDoc.writePreamble(assignment.getQuiz().getName());
        compositeDoc.beginDocument(breaks, triggers);

        Path studentDir, studentStaging;
        DocumentWriter individualDoc = null;
        EntryType[] students = assignment.getStudents();
        for (int i = 0; i < students.length; i++) {

            String studentId = students[i].getId();
            String studentKey = students[i].getValue();
            String studentName = students[i].getName();
            int[] signature = students[i].getSignature();

            // QRKey = [TestPaperId(6)][studentIdx(3)][pageNum(1)]
            String QRKey = String.format("%s%s", assignmentKey, studentKey);

            if (publish) {
                studentDir = studentsDir.resolve(studentKey);
                studentStaging = studentDir.resolve(stagingDirName);
                Files.createDirectories(studentStaging);

                plotfiles = Files.newDirectoryStream(quizStaging, "*.gnuplot");
                for (Path plotfile : plotfiles) {
                    Files.copy(plotfile,
                            studentStaging.resolve(plotfile.getFileName()),
                            StandardCopyOption.REPLACE_EXISTING);
                }
                plotfiles.close();

                Path individualTex =
                        studentStaging.resolve(String.format(nameFormat,
                                studentId, quizId, testpaperId, "tex"));
                individualDoc = new DocumentWriter(individualTex);
                individualDoc.writePreamble(assignment.getQuiz().getName());
                individualDoc.beginDocument(breaks, triggers);
                individualDoc.beginQuiz(studentName, signature, QRKey);
                individualDoc.writeTemplate(blueprintTex);
                individualDoc.endQuiz();
                individualDoc.endDocument();
                individualDoc.close();

                if (i == 0) {
                    compositeDoc.beginQuiz("sample copy",
                            new int[signature.length], null);
                    compositeDoc.writeTemplate(blueprintTex);
                    compositeDoc.endQuiz();
                }
            } else {
                compositeDoc.beginQuiz(studentName, signature, QRKey);
                compositeDoc.writeTemplate(blueprintTex);
                compositeDoc.endQuiz();
            }
        }
        compositeDoc.endDocument();
        compositeDoc.close();

        // 3. Link Makefiles and make the PDFs
        Path toMakefile =
                staging.relativize(sharedPath).resolve(makefilesDir)
                        .resolve(quizMakefile);
        if (!Files.exists(staging.resolve(makefile)))
            Files.createSymbolicLink(staging.resolve(makefile), toMakefile);

        if (make(staging, assignmentDir, null) != 0)
            throw new Exception("Problem! Non-zero return code from make");

        return prepareManifest(assignmentDir, assignmentDir, null);
    }

    /**
     * Publishes an assignment so that individual students can download it from
     * their own *home* directories
     * 
     * @param assignmentId
     *            (quiz, assignment)
     * @return manifest
     * @throws Exception
     */
    public ManifestType prepTest(AssignmentType assignment) throws Exception {

        String quizId = assignment.getQuiz().getId();
        String assignmentId = assignment.getInstance().getId();
        String assignmentValue = assignment.getInstance().getValue();

        Path studentsDir =
                mintPath.resolve(worksheetDirName).resolve(assignmentValue)
                        .resolve(studentDirName);
        ManifestType manifest = prepareManifest(studentsDir, null, null);

        Path studentDir, stagingDir, toMakefile;
        for (EntryType student : assignment.getStudents()) {

            String studentId = student.getId();
            String studentKey = student.getValue();

            studentDir = studentsDir.resolve(studentKey);
            stagingDir = studentDir.resolve(stagingDirName);
            toMakefile =
                    stagingDir.relativize(sharedPath).resolve(makefilesDir)
                            .resolve(quizMakefile);

            if (!Files.exists(stagingDir.resolve(makefile)))
                Files.createSymbolicLink(stagingDir.resolve(makefile),
                        toMakefile);

            if (make(stagingDir, studentDir, null) != 0) {
                throw new Exception(
                        "Oppa! Non-zero return code making worksheet");
            }

            EntryType PDF = new EntryType();
            PDF.setId(String.format(nameFormat, studentId, quizId,
                    assignmentId, "pdf"));
            manifest.addDocument(PDF);
        }
        return manifest;
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
        BufferedReader messages =
                new BufferedReader(new InputStreamReader(
                        process.getInputStream()));

        String line = null;
        while ((line = messages.readLine()) != null) {
            System.out.println(line);
        }

        int retCode = 0;
        if ((retCode = process.waitFor()) == 0) {

            Path target = null;
            DirectoryStream<Path> stream = null;

            if (downloadsDir != null) {
                stream = Files.newDirectoryStream(workingDir, "*.pdf");
                for (Path entry : stream) {
                    target = downloadsDir.resolve(entry.getFileName());
                    Files.move(entry, target,
                            StandardCopyOption.REPLACE_EXISTING);
                }
                stream.close();
            } else {
                stream = Files.newDirectoryStream(workingDir, "*.pdf");
                for (Path entry : stream) {
                    Files.delete(entry);
                }
                stream.close();
            }

            if (previewsDir != null) {
                stream = Files.newDirectoryStream(previewsDir, "*.jpeg");
                for (Path entry : stream)
                    Files.delete(entry);
                stream.close();

                stream = Files.newDirectoryStream(workingDir, "*.jpeg");
                for (Path entry : stream) {
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
            String mangled = questionId.replace('/', '-');
            target = staging.resolve(String.format("%s.gnuplot", mangled));
            Files.copy(plotfile, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private int[] getList(String[] lines, String tag) throws Exception {
        String csv = null;
        for (String line : lines) {
            if (line.startsWith(tag)) {
                csv = line.replaceFirst("(^.*\\{)(.*)(\\}$)", "$2");
                break;
            }
        }
        String[] values =
                csv.trim().length() == 0 ? new String[0] : csv.split(",");
        int[] breaks = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            breaks[i] = Integer.parseInt(values[i].trim());
        }
        return breaks;
    }

    private ManifestType prepareManifest(Path root, Path downloads, Path preview)
            throws Exception {
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
                    Files.newDirectoryStream(preview, "*.jpeg");
            for (Path jpeg : jpegs) {
                EntryType img = new EntryType();
                img.setId(root.relativize(jpeg).toString());
                manifest.addImage(img);
            }
            jpegs.close();
            if (manifest.getImage().length == 0)
                throw new Exception("Problema! No images for preview");
        }

        return manifest;
    }

    private Path commonPath, sharedPath, mintPath, vaultPath, latexRoot;

    private final String stagingDirName = "staging",
            previewDirName = "preview", quizDirName = "quiz",
            worksheetDirName = "ws", studentDirName = "student",
            makefilesDir = "makefiles", makefile = "Makefile",
            quizMakefile = "quiz.mk", blueprintFile = "blueprintTex",
            nameFormat = "%s-%s-%s.%s";

}
