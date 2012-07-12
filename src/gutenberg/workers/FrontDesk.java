package gutenberg.workers;

import gutenberg.blocs.EntryType;
import gutenberg.blocs.ManifestType;
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

		File template = new File(SHARED + "/suggestion.tex");
		File target = new File(FRONTDESK + "/suggestion-forms/suggestion.tex");
		
		String line = null;
		PrintWriter writer = new PrintWriter(new FileWriter(target));
		BufferedReader reader = new BufferedReader(new FileReader(template));
		while ((line = reader.readLine()) != null) {
			if (line.trim().startsWith(school_tag)) {
				line = line.replace("school", school.getName());
			} else if (line.trim().startsWith(author_tag)) {
				line = line.replace("name", teacher.getName());
			} else if (line.trim().startsWith(insertQRC_tag)) {
				line = line.replace("qrc",
						teacher.getId() + "-" + teacher.getName()
								+ "-0-0-0-1-1");
			}
			writer.println(line);
		}
		writer.close();
		
		generate();
		
		Path dir = new File(FRONTDESK + "/suggestion-forms").toPath();
		Path sourcePdf = dir.resolve("suggestion.pdf");
		Path targetPdf = dir.resolve(teacher.getId() + ".pdf");
		Files.move(sourcePdf, targetPdf);
		
		File[] files = dir.toFile().listFiles();
		for (File file: files) {
			if (file.getName().startsWith("suggestion")) {
				file.delete();
			}
		}		

		ManifestType manifest = new ManifestType();
		manifest.setRoot(target.getParent().toString());
		EntryType document = new EntryType();
		document.setId(teacher.getId());
		document.setName(target.getName());
		manifest.addDocument(document);
		return manifest;
	}

	private String FRONTDESK, SHARED;
	private final String school_tag = "\\School", author_tag = "\\DocAuthor",
			insertQRC_tag = "\\insertQR";
	
	private int generate() throws Exception {
		
		ProcessBuilder pb = new ProcessBuilder("make");

		pb.directory(new File(FRONTDESK + "/suggestion-forms"));
		pb.redirectErrorStream(true);

		Process process = pb.start();
		BufferedReader messages = new BufferedReader(new InputStreamReader(
				process.getInputStream()));
		
		String line = null;
		while ((line = messages.readLine()) != null) {
			System.out.println(line);
		}
		
		return process.waitFor();
	}
}
