package gutenberg.workers;

import gutenberg.blocs.EntryType;
import gutenberg.blocs.ManifestType;
import gutenberg.blocs.StudentGroupType;
import gutenberg.blocs.TeacherType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.nio.file.Files;
import java.nio.file.Path;

public class FrontDesk {

	public FrontDesk(Config config) {
		FRONTDESK = config.getPath(Resource.frontdesk);
		SHARED = config.getPath(Resource.shared);
	}

	public ManifestType generateSuggestionForm(TeacherType teacherInfo)
			throws Exception {

		EntryType teacher = teacherInfo.getTeacher();
		EntryType school = teacherInfo.getSchool();

		File templateDir = new File(SHARED);
		File templateFile = templateDir.toPath().resolve("suggestion.tex").toFile();
		File targetDir = new File(String.format("%s/teachers/%s-%s/petty-cash", 
				FRONTDESK, school.getId(), teacher.getId()));
		if (!targetDir.exists()) targetDir.mkdirs();
		File targetFile = targetDir.toPath().resolve("teacher.tex").toFile();

		String line = null;
		PrintWriter writer = new PrintWriter(new FileWriter(targetFile));
		BufferedReader reader = new BufferedReader(new FileReader(templateFile));
		while ((line = reader.readLine()) != null) {
			if (line.trim().startsWith(school_tag)) {
				line = line.replace("school", school.getName());
			} else if (line.trim().startsWith(author_tag)) {
				line = line
						.replace("name", teacher.getName().replace('-', ' '));
			} else if (line.trim().startsWith(insertQRC_tag)) {
				line = line.replace("qrc", String.format("%s-%s-0-0-0-1-1",
						teacher.getId(), teacher.getName()));
			}
			writer.println(line);
		}
		writer.close();

		Files.createSymbolicLink(targetDir.toPath().resolve("Makefile"),
				templateDir.toPath().resolve("teacher.mk"));
		
		String outputFile = String.format("%s-%s.pdf", teacher.getId(),
				teacher.getName().split("-")[0]);
		generatePdf(targetDir, targetDir.toPath().resolve(outputFile).toFile());

		ManifestType manifest = new ManifestType();
		manifest.setRoot(targetDir.getPath());
		EntryType document = new EntryType();
		document.setId(outputFile);
		manifest.addDocument(document);
		return manifest;
	}

	public ManifestType generateStudentRoster(StudentGroupType studentGroup)
			throws Exception {

		EntryType group = studentGroup.getGroup();
		EntryType school = studentGroup.getSchool();
		EntryType teacher = studentGroup.getTeacher();
		String defaultPasswd = studentGroup.getDefaultPasswd();
		EntryType[] members = studentGroup.getMembers();

		File templateDir = new File(SHARED);
		File templateFile = templateDir.toPath().resolve("roster.tex").toFile();
		File targetDir = new File(String.format("%s/teachers/%s-%s/petty-cash", 
				FRONTDESK, school.getId(), teacher.getId()));
		File targetFile = targetDir.toPath().resolve("teacher.tex").toFile();

		String line = null;
		PrintWriter writer = new PrintWriter(new FileWriter(targetFile));
		BufferedReader reader = new BufferedReader(new FileReader(templateFile));
		while ((line = reader.readLine()) != null) {
			if (line.trim().startsWith(school_tag)) {
				line = line.replace("school", school.getName());
			} else if (line.trim().startsWith(author_tag)) {
				line = line.replace("group", group.getName());
			} else if (line.contains("passwd")) {
				line = line.replace("passwd", defaultPasswd);
			} else if (line.trim().startsWith(table_end)) {
				if (members.length % 2 != 0) {
					throw new Exception(
							"Doh! Come on, send an even number of elements I say!");
				}
				String row = "    %s & %s & %s & %s\\\\";
				for (int i = 0; i < members.length; i += 2) {
					writer.println(String.format(row, members[i].getName(),
							members[i].getId(), members[i + 1].getName(),
							members[i + 1].getId()));
				}
			}
			writer.println(line);
		}
		writer.close();

		String outputFile = String.format("%s-%s.pdf", group.getId(),
				group.getName().replace(' ', '_'));
		generatePdf(targetDir, targetDir.toPath().resolve(outputFile).toFile());

		ManifestType manifest = new ManifestType();
		manifest.setRoot(targetDir.getPath());
		EntryType document = new EntryType();
		document.setId(outputFile);
		manifest.addDocument(document);
		return manifest;
	}

	private String FRONTDESK, SHARED;
	private final String school_tag = "\\School", author_tag = "\\DocAuthor",
			insertQRC_tag = "\\insertQR", table_end = "\\end{tabular}";
	

	private int generatePdf(File outputDir, File outputFile) throws Exception {
		ProcessBuilder pb = new ProcessBuilder("make");

		pb.directory(outputDir);
		pb.redirectErrorStream(true);

		Process build = pb.start();
		BufferedReader messages = new BufferedReader(new InputStreamReader(
				build.getInputStream()));

		String line = null;
		while ((line = messages.readLine()) != null) {
			System.out.println(line);
		}

		if (build.waitFor() == 0) {
			Path src = outputDir.listFiles(new NameFilter("teacher.pdf"))[0].toPath();
			Files.move(src, outputFile.toPath());

			ProcessBuilder pClean = new ProcessBuilder("make", "clean");
			pClean.directory(outputDir);
			Process clean = pClean.start();
			return clean.waitFor();
		}

		return 0;
	}
}
