package gutenberg.workers;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import gutenberg.blocs.AssignmentType;
import gutenberg.blocs.EntryType;
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
      quizDir.setExecutable(true, false);
      staging.mkdir();
      staging.setExecutable(true, false);
    }
    manifest.setRoot(quizDir.getPath());
    
    copyPlotFilesFor(quiz) ;
    String[] pages = readPages(quiz) ;
    
    // First, generate the answer-key.tex & blueprint.tex file
    
    PrintWriter[] targets = {new PrintWriter(staging + "/answer-key.tex"),
    		                 new PrintWriter(staging + "/blueprint.tex")} ;
    
    for (int i = 0 ; i < targets.length ; i++) {
    	targets[i].println(preamble);
    	if (i == 0) targets[i].println("\\printanswers");
    	targets[i].println(docBegin) ;
    	
    	for (int j = 0 ; j < pages.length ; j++) {
    		targets[i].println(pages[j]);
    		targets[i].println("\\newpage") ;
    	}
    	targets[i].println(docEnd) ;
    	targets[i].close() ;
    }

    // Then, generate the preview .tex files for each page
    // Remember, each preview is a self-contained .tex file in itself 
    for (int i = 0 ; i < pages.length ; i++) {
      PrintWriter preview = new PrintWriter(staging + "/page-" + i + ".tex") ;
      
      preview.println(preamble) ;
      preview.println("\\printanswers") ;
      preview.println(docBegin);
      preview.println(pages[i]) ;
      preview.println(docEnd) ;
      preview.close() ;
    }
    /*
    PageType[] pages = quiz.getPage();
    PrintWriter[] preview = new PrintWriter[pages.length];
    String page = null;
    EntryType[] images = new EntryType[pages.length*2];
    for (int i = 0; i < pages.length; i++) {
      preview[i] = new PrintWriter(staging + "/page" + i + ".tex");
      preview[i].println(docBegin);
      page = buildPage(pages[i], staging);
      preview[i].println(page);
      preview[i].print(docEnd);
      preview[i].close();      
      answerKey.println(page);
      images[2*i] = new EntryType();
      images[2*i].setId(i+"page.jpg");
      images[2*i+1] = new EntryType();
      images[2*i+1].setId(i+"thumb.jpg");
      if (i == pages.length-1) {
        answerKey.println("\\newpage");
      }
    }
    if (answerKey.checkError())
      throw new Exception("Check returned with error ") ;
    answerKey.close();
    EntryType[] documents = new EntryType[1];
    documents[0] = new EntryType();
    documents[0].setId("answerKey.pdf");
    */
    
    int ret = make(quiz) ;
    /*
    if (ret != 0) {
      throw new Exception("Make returned with: " + ret) ;
    } else {
      manifest.setImage(images);
      manifest.setDocument(documents);      
    }
    */
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
  
  private boolean copyPlotFilesFor(QuizType quiz) throws Exception {
    PageType[] pages = quiz.getPage() ;
    String here = this.MINT + "/" + quiz.getId() + "/staging" ;
    Filer f = new Filer() ;
    
    for (int i = 0 ; i < pages.length ; i++) {
      PageType page = pages[i] ; 
      EntryType[] questions = page.getQuestion() ;
      String vault = this.vault.getPath() ;
      
      for (int j = 0 ; j < questions.length ; j++) {
        String qid = questions[j].getId() ;
        String plot = vault + "/" + qid + "/figure.gnuplot" ;
        String target = here + "/" + qid + ".gnuplot" ;

        f.copy(plot, target) ;
        System.out.println(" Copied " + plot + " ---> " + target) ;
      }
    }
    return true ;
  }
  
  private String[] readPages(QuizType quiz) throws Exception {
    PageType[]  pages = quiz.getPage() ;
    String[]    document = new String[pages.length] ;
    String      vault = this.vault.getPath() ;
    Filer       f = new Filer() ;
    
    for (int i = 0; i < pages.length ; i++) {
      EntryType[]   questions = pages[i].getQuestion() ;
      StringBuilder content = new StringBuilder() ;
      
      for (int j = 0 ; j < questions.length ; j++) {
        String qid = questions[j].getId() ;
        String path = vault + "/" + qid + "/question.tex" ;
        
        content.append(f.get(new File(path))) ; // check for file existence?    
      }
      document[i] = content.toString() ;
    }
    return document ;
  }

  private int make(QuizType quiz) throws Exception {
    ProcessBuilder processBuilder = new ProcessBuilder("make", "dir="+quiz.getId());
    File mintDir = new File(MINT);
    processBuilder.directory(mintDir);
    processBuilder.redirectErrorStream(true);
    Process process = processBuilder.start();
    BufferedReader reader = new BufferedReader (new InputStreamReader(process.getInputStream()));
    String line = null;
    while ((line = reader.readLine()) != null) {
      System.out.println(line);
    }
    return process.waitFor();        
  }
  
}
