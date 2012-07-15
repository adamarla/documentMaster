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

		copyTemplate(teacher, school);
		generatePdf(teacher);
		// cleanUp(teacher);

		ManifestType manifest = new ManifestType();
		manifest.setRoot(FRONTDESK + "/suggestion-forms/");
		EntryType document = new EntryType();
		document.setId(teacher.getId());
		manifest.addDocument(document);
		return manifest;
	}

	/*
	private void cleanUp(EntryType teacher) throws Exception {

		Path dir = new File(FRONTDESK + "/suggestion-forms").toPath();
		Path sourcePdf = dir.resolve("suggestion.pdf");
		Path targetPdf = dir.resolve(teacher.getId() + ".pdf");
		Files.move(sourcePdf, targetPdf);

		File[] files = dir.toFile().listFiles();
		for (File file : files) {
			if (file.getName().startsWith("suggestion")) {
				file.delete();
			}
		}
	}
	*/

	private void copyTemplate(EntryType teacher, EntryType school)
			throws Exception {

		String  id = teacher.getId() ;
		String  name = teacher.getName() ;
				
		File template = new File(SHARED + "/suggestion.tex");
		File target = new File(FRONTDESK + "/suggestion-forms/suggestion.tex");

		String line = null;
		PrintWriter writer = new PrintWriter(new FileWriter(target));
		BufferedReader reader = new BufferedReader(new FileReader(template));
		while ((line = reader.readLine()) != null) {
			if (line.trim().startsWith(school_tag)) {
				line = line.replace("school", school.getName());
			} else if (line.trim().startsWith(author_tag)) {
				line = line.replace("name", name.replace('-', ' '));
			} else if (line.trim().startsWith(insertQRC_tag)) {
				line = line.replace("qrc", String.format("%s-%s-0-0-0-1-1",id, name));
			}
			writer.println(line);
		}
		writer.close();

	}

	private String FRONTDESK, SHARED;
	private final String school_tag = "\\School", author_tag = "\\DocAuthor",
			insertQRC_tag = "\\insertQR";

	private int generatePdf(EntryType teacher) throws Exception {
		ProcessBuilder pb = new ProcessBuilder("make");
		File   frontDesk = new File(FRONTDESK + "/suggestion-forms") ;

		pb.directory(frontDesk);
		pb.redirectErrorStream(true);

		Process build = pb.start();
		BufferedReader messages = new BufferedReader(new InputStreamReader(
			build.getInputStream()));

		String line = null;
		while ((line = messages.readLine()) != null) {
			System.out.println(line);
		}
		
		if (build.waitFor() == 0) {
			ProcessBuilder pClean = new ProcessBuilder("make", "clean") ;
			String name = teacher.getName().split("-")[0] ;
			String id = teacher.getId() ;
			Path   dir = frontDesk.toPath() ;
			Path   src = dir.resolve("suggestion.pdf") ;
			Path   target = dir.resolve(String.format("%s-%s.pdf", id, name)) ;
			
			Files.move(src, target) ;
			
			pClean.directory(frontDesk) ;
			Process clean = pClean.start() ;
			return clean.waitFor() ;
		}
		
		return 0 ;
	}
}
