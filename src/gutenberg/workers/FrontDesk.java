package gutenberg.workers;

import gutenberg.blocs.AssignmentIdType;
import gutenberg.blocs.EntryType;
import gutenberg.blocs.ManifestType;
import gutenberg.blocs.StudentGroupType;
import gutenberg.blocs.TeacherType;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FrontDesk {

    public FrontDesk(Config config) throws Exception {
        frontdeskPath = new File(config.getPath(Resource.frontdesk)).toPath();
        sharedPath = new File(config.getPath(Resource.shared)).toPath();
        mintPath = new File(config.getPath(Resource.mint)).toPath();
        locker = new Locker(config);
    }

    public ManifestType generateSuggestionForm(TeacherType teacherInfo)
            throws Exception {

        EntryType teacher = teacherInfo.getTeacher();
        EntryType school = teacherInfo.getSchool();

        Path outputDirPath =
                frontdeskPath.resolve(String.format(
                        "teachers/%s-%s/petty-cash", school.getId(),
                        teacher.getId()));
        if (!Files.exists(outputDirPath))
            Files.createDirectories(outputDirPath);

        Path keyFile = outputDirPath.resolve("keyFile");
        PrintWriter keyFileWriter =
                new PrintWriter(Files.newBufferedWriter(keyFile,
                        StandardCharsets.UTF_8, StandardOpenOption.CREATE));
        String QRKey = String.format("%s-%s", school.getId(), teacher.getId());
        String QRKeyVal =
                String.format("%s:%s-%s-0-0-0-1-1", QRKey, teacher.getId(),
                        teacher.getName());
        keyFileWriter.println(QRKeyVal);
        keyFileWriter.close();

        Path workingDirPath = outputDirPath.resolve("working");
        Files.createDirectory(workingDirPath);
        String templateName = "suggestion.tex";
        Path texPath = workingDirPath.resolve(templateName);
        PrintWriter writer =
                new PrintWriter(Files.newBufferedWriter(texPath,
                        StandardCharsets.UTF_8, StandardOpenOption.CREATE));

        List<String> template =
                Files.readAllLines(
                        sharedPath.resolve("templates").resolve(templateName),
                        StandardCharsets.UTF_8);
        String trimmed = null;
        for (String line : template) {

            trimmed = line.trim();
            if (trimmed.startsWith(school_tag)) {
                line = line.replace("school", school.getName());
            } else if (trimmed.startsWith(author_tag)) {
                line =
                        line.replace("name", teacher.getName()
                                .replace('-', ' '));
            } else if (trimmed.startsWith(insertQRC_tag)) {
                line = line.replace("QRC", QRKey);
            }
            writer.println(line);
        }
        writer.close();

        Files.createSymbolicLink(workingDirPath.resolve("Makefile"),
                sharedPath.resolve("makefiles/front-desk.mk"));

        String outputFile =
                String.format("%s-%s.pdf", teacher.getId(), teacher.getName()
                        .split("-")[0]);
        generatePdf(workingDirPath, outputDirPath.resolve(outputFile));

        ManifestType manifest = new ManifestType();
        manifest.setRoot(outputDirPath.toString());
        EntryType document = new EntryType();
        document.setId(outputFile);
        manifest.addDocument(document);

        // Make room in the locker for receiving scans
        locker.makeRoom("0", teacher.getId());

        return manifest;
    }

    public ManifestType generateStudentRoster(StudentGroupType studentGroup)
            throws Exception {

        EntryType group = studentGroup.getGroup();
        EntryType school = studentGroup.getSchool();
        EntryType[] members = studentGroup.getMembers();

        if (members == null)
            members = new EntryType[0];
        if (members.length % 2 != 0) {
            throw new Exception(
                    "Doh! Come on, send an even number of elements I say!");
        }

        Path outputDirPath =
                frontdeskPath.resolve(String.format("schools/%s/rosters",
                        school.getId()));
        if (!Files.exists(outputDirPath))
            Files.createDirectories(outputDirPath);

        Path workingDirPath = outputDirPath.resolve(group.getId());
        if (!Files.exists(workingDirPath))
            Files.createDirectory(workingDirPath);

        String templateName = "roster.tex";
        Path texPath = workingDirPath.resolve(templateName);
        PrintWriter writer =
                new PrintWriter(Files.newBufferedWriter(texPath,
                        StandardCharsets.UTF_8, StandardOpenOption.CREATE));
        List<String> template =
                Files.readAllLines(
                        sharedPath.resolve("templates").resolve(templateName),
                        StandardCharsets.UTF_8);

        String trim = null;
        for (String line : template) {
            trim = line.trim();
            if (trim.contains("{school}")) {
                line = line.replace("school", school.getName());
            } else if (trim.contains("{sektion}")) {
                line = line.replace("sektion", group.getName());
            } else if (trim.startsWith(table_end)) {
                // number of lines = members.length / 2
                String row = "%s & %s & & %s & %s \\\\";
                for (int i = 0; i < members.length; i += 2) {
                    writer.println(String.format(row, members[i].getName(),
                            members[i].getId(), members[i + 1].getName(),
                            members[i + 1].getId()));
                }
            }
            writer.println(line);
        }
        writer.close();

        Files.createSymbolicLink(workingDirPath.resolve("Makefile"),
                sharedPath.resolve("makefiles/front-desk.mk"));

        String outputFile =
                String.format("%s-%s.pdf", group.getId(), group.getName()
                        .replace(' ', '_'));
        generatePdf(workingDirPath, outputDirPath.resolve(outputFile));

        ManifestType manifest = new ManifestType();
        manifest.setRoot(outputDirPath.toString());
        EntryType document = new EntryType();
        document.setId(outputFile);
        manifest.addDocument(document);
        return manifest;
    }

    public ManifestType generateQuizReport(StudentGroupType studentGroup)
            throws Exception {

        EntryType testpaper = studentGroup.getGroup();
        EntryType school = studentGroup.getSchool();
        EntryType[] members = studentGroup.getMembers();

        if (members == null)
            members = new EntryType[0];
        if (members.length % 2 != 0) {
            throw new Exception(
                    "Doh! Come on, send an even number of elements I say!");
        }

        Path outputDirPath =
                frontdeskPath.resolve(String.format("schools/%s/reports",
                        school.getId()));
        if (!Files.exists(outputDirPath))
            Files.createDirectories(outputDirPath);

        Path workingDirPath = outputDirPath.resolve(testpaper.getId());
        if (!Files.exists(workingDirPath))
            Files.createDirectory(workingDirPath);

        String outputCSV =
                String.format("%s-%s.csv", testpaper.getId(), testpaper
                        .getName().replace(' ', '_'));
        Path csvPath = outputDirPath.resolve(outputCSV);
        PrintWriter csvWriter =
                new PrintWriter(Files.newBufferedWriter(csvPath,
                        StandardCharsets.UTF_8, StandardOpenOption.CREATE));
        csvWriter.println("name,marks");

        String templateName = "groupreport.tex";
        Path texPath = workingDirPath.resolve(templateName);
        PrintWriter writer =
                new PrintWriter(Files.newBufferedWriter(texPath,
                        StandardCharsets.UTF_8, StandardOpenOption.CREATE));

        List<String> template =
                Files.readAllLines(
                        sharedPath.resolve("templates").resolve(templateName),
                        StandardCharsets.UTF_8);
        String trim = null;
        for (String line : template) {
            trim = line.trim();
            if (trim.contains("{school}")) {
                line = line.replace("school", school.getName());
            } else if (trim.contains("{sektion-testpaper}")) {
                line = line.replace("sektion-testpaper", testpaper.getName());
            } else if (trim.startsWith(table_end)) {
                // number of lines = members.length / 2
                String row = "%s & %s & & %s & %s \\\\";
                for (int i = 0; i < members.length; i += 2) {
                    writer.println(String.format(row, members[i].getName(),
                            members[i].getValue(), members[i + 1].getName(),
                            members[i + 1].getValue()));
                    csvWriter.println(members[i].getName() + ","
                            + members[i].getValue());
                    csvWriter.println(members[i + 1].getName() + ","
                            + members[i + 1].getValue());
                }
            }
            writer.println(line);
        }
        writer.close();
        csvWriter.close();

        Files.createSymbolicLink(workingDirPath.resolve("Makefile"),
                sharedPath.resolve("makefiles/front-desk.mk"));

        String outputFile =
                String.format("%s-%s.pdf", testpaper.getId(), testpaper
                        .getName().replace(' ', '_'));
        generatePdf(workingDirPath, outputDirPath.resolve(outputFile));

        ManifestType manifest = new ManifestType();
        manifest.setRoot(outputDirPath.toString());
        EntryType document = new EntryType();
        document.setId(outputFile);
        manifest.addDocument(document);
        document = new EntryType();
        document.setId(outputCSV);
        manifest.addDocument(document);
        return manifest;
    }

    public ManifestType publishAssignment(AssignmentIdType assignmentId)
            throws Exception {

        EntryType quizId = assignmentId.getQuiz();
        EntryType instanceId = assignmentId.getInstance();

        ManifestType manifest = new ManifestType();
        manifest.setRoot(frontdeskPath.resolve("students").toString());
        Path quizDirPath =
                mintPath.resolve(quizId.getId()).resolve(instanceId.getId());
        if (Files.exists(quizDirPath)) {

            String[] downloads =
                    quizDirPath.resolve("downloads").toFile().list();
            for (String pdf : downloads) {

                if (pdf.startsWith("assignment"))
                    continue;

                String studentId = pdf.split("-")[2].split("\\.")[0];
                Path studentDirPath =
                        frontdeskPath.resolve("students").resolve(studentId);
                if (!studentDirPath.toFile().exists()) {
                    studentDirPath.toFile().mkdir();
                }

                String assignmentDir =
                        String.format("%s-%s", quizId.getId(),
                                instanceId.getId());
                Path assignmentDirPath = studentDirPath.resolve(assignmentDir);
                if (!assignmentDirPath.toFile().exists()) {
                    assignmentDirPath.toFile().mkdir();
                    assignmentDirPath.resolve("downloads").toFile().mkdir();
                    assignmentDirPath.resolve("preview").toFile().mkdir();
                } else
                    continue;

                Path target =
                        Files.copy(
                                quizDirPath.resolve("downloads").resolve(pdf),
                                assignmentDirPath.resolve("downloads").resolve(
                                        pdf));
                EntryType document = new EntryType();

                document.setId(target.relativize(
                        frontdeskPath.resolve("students")).toString());

                this.generateJpegs(target);
                String[] jpegs =
                        target.getParent().toFile()
                                .list(new NameFilter("jpeg"));
                for (String jpeg : jpegs) {
                    Path image = target.getParent().resolve(jpeg);
                    resize(image);
                    Files.move(image, assignmentDirPath.resolve("preview")
                            .resolve(jpeg));
                }
            }
        }

        return manifest;

    }

    private Locker locker;
    private Path   frontdeskPath, sharedPath, mintPath;
    private final String school_tag = "\\School", author_tag = "\\DocAuthor",
            insertQRC_tag = "\\insertQR", table_end = "\\end{tabular}";

    private int generatePdf(Path workingDirPath, Path outputFilePath)
            throws Exception {
        ProcessBuilder pb = new ProcessBuilder("make");

        pb.directory(workingDirPath.toFile());
        pb.redirectErrorStream(true);

        Process build = pb.start();
        BufferedReader messages =
                new BufferedReader(
                        new InputStreamReader(build.getInputStream()));

        String line = null;
        while ((line = messages.readLine()) != null) {
            System.out.println(line);
        }

        if (build.waitFor() == 0) {
            Path src =
                    workingDirPath.toFile().listFiles(new NameFilter(".pdf"))[0]
                            .toPath();
            Files.move(src, outputFilePath, StandardCopyOption.REPLACE_EXISTING);

            ProcessBuilder pClean = new ProcessBuilder("make", "clean");
            pClean.directory(workingDirPath.toFile());
            Process clean = pClean.start();
            if (clean.waitFor() == 0) {
                Files.delete(workingDirPath.resolve("Makefile"));
                Files.delete(workingDirPath);
            }
        }

        return 0;
    }

    private int generateJpegs(Path pdf) throws Exception {

        ProcessBuilder pb = new ProcessBuilder();
        pb.command("gs", "-dNOPAUSE", "-dBATCH", "-sDEVICE=jpeg", "-r700",
                "-sOutputFile=page-%d.jpeg", pdf.getFileName().toString());
        pb.directory(pdf.getParent().toFile());
        pb.redirectErrorStream(true);

        Process process = pb.start();
        BufferedReader messages =
                new BufferedReader(new InputStreamReader(
                        process.getInputStream()));
        String line = null;

        while ((line = messages.readLine()) != null) {
            System.out.println(line);
        }

        return process.waitFor();
    }

    private int resize(Path jpeg) throws Exception {

        ProcessBuilder pb = new ProcessBuilder();
        pb.command("convert", jpeg.getFileName().toString(), "-resize",
                "600x800", jpeg.getFileName().toString());

        pb.directory(jpeg.getParent().toFile());
        pb.redirectErrorStream(true);

        Process process = pb.start();
        BufferedReader messages =
                new BufferedReader(new InputStreamReader(
                        process.getInputStream()));
        String line = null;

        while ((line = messages.readLine()) != null) {
            System.out.println(line);
        }

        return process.waitFor();
    }
}
