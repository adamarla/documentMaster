package gutenberg.workers;

import java.io.File;
import java.io.PrintWriter;
import gutenberg.blocs.AssignmentType;
import gutenberg.blocs.ManifestType;
import gutenberg.blocs.PageType;
import gutenberg.blocs.QuizType;

public class Scribe {
	
	public Scribe(String mint, String shared) throws Exception {
		MINT = mint;
		loadShared(shared);
		manifest = new ManifestType();
	}
	
	public void setVault(Vault vault) {
		this.vault = vault;
	}
	
	/**
	 * creates an answer key pdf for a quiz and preview jpgs
	 * @param quiz
	 * @throws Exception
	 */
	public void generate(QuizType quiz) throws Exception {
		File quizDir = new File(MINT + "/" + quiz.getId());
		File staging = new File(MINT + "/" + quiz.getId() + "/" + "staging");
		if (!quizDir.exists()) {
			quizDir.mkdir();
			staging.mkdir();
		}
		manifest.setRoot(quizDir.getPath());
		
		PrintWriter answerkey = new PrintWriter(staging + "/answerkey.tex");
		//TODO substitute teacherId 
		answerkey.println(preamble);
		answerkey.println("\\printanswers");
		answerkey.println(docBegin);
		
		PageType[] pages = quiz.getPages().getPage();
		PrintWriter[] preview = new PrintWriter[pages.length];
		String page = null;
		String[] images = new String[pages.length*2];
		for (int i = 0; i < pages.length; i++) {
			preview[i] = new PrintWriter(staging + "/page" + i + ".tex");
			preview[i].println(docBegin);
			page = buildPage(pages[i], staging, null);
			preview[i].println(page);
			preview[i].print(docEnd);
			preview[i].close();
			answerkey.println(page);
			images[2*i] = i+"page.jpg";
			images[2*i+1] = i+"thumb.jpg";
			if (i == pages.length-1) {
				answerkey.println("\\newpage");
			}			
			answerkey.flush();
		}
		answerkey.close();
		String[] documents = new String[1];
		documents[0] = "answerkey.pdf";
		manifest.setImages(images);
		manifest.setDocuments(documents);
	}
	
	/**
	 * create an assignment
	 * @param assignment
	 */
	public void generate(AssignmentType assignment) {

	}
	
	public ManifestType getManifest() {
		return manifest;
	}
	
	private String MINT;
	private String preamble, docBegin, docEnd;
	private Vault vault;
	private ManifestType manifest;
	
	private void loadShared(String shared) throws Exception {
		Filer filer = new Filer();
		preamble = filer.get(shared + "/preamble.tex");
		docBegin = filer.get(shared+ "/doc_begin.tex");
		docEnd = filer.get(shared + "/doc_end.tex");		

	}
		
	private String buildPage(PageType page, File staging, String qrcode) throws Exception {
		StringBuilder contents = new StringBuilder();
		String[] questionIds = page.getQuestions().getId();
		Filer filer = new Filer();
		for(int i = 0; i < questionIds.length; i++) {
			if (qrcode != null) {
				qrcode += questionIds[i];
				contents.append(qrcode).append("\n");
			}
			contents.append(vault.getContent(questionIds[i], "tex")[0]);
			File[] files = vault.getFiles(questionIds[i], "gnuplot");
			for (int j = 0; j < files.length; j++) {			
				filer.copy(files[i], new File(staging.getPath() + "/" + files[i].getName()));
			}
		}
		return contents.toString();
	}
	
	
}
