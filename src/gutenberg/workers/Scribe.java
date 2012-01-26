package gutenberg.workers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import gutenberg.blocs.AssignmentType;
import gutenberg.blocs.EntryType;
import gutenberg.blocs.ManifestType;
import gutenberg.blocs.PageType;
import gutenberg.blocs.QuizType;
import gutenberg.blocs.StudentType;

public class Scribe {
  
  public Scribe(String mint, String shared) throws Exception {
    MINT = mint + "/" ;
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
                         new PrintWriter(staging + "/blueprint.tex.skip")} ;
    
    for (int i = 0 ; i < targets.length ; i++) {
      
      if (i == 0) {
        targets[i].println(preamble);
        targets[i].println("\\printanswers");
        targets[i].println(docBegin) ;
      }
      for (int j = 0 ; j < pages.length ; j++) {
        targets[i].println(pages[j]);
        targets[i].println("\\newpage") ;
      }
      if ( i == 0) targets[i].println(docEnd) ;
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
    
    int ret = make(quiz.getId()) ;
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
  public void generate(AssignmentType assignment) throws Exception {
    String        quizId = assignment.getQuiz().getId() ;
    String        blueprint = MINT + quizId + "/staging/blueprint.tex.skip" ;
    StudentType[] students = assignment.getStudents() ;
    String        composite = MINT + quizId + "/staging/composite.tex" ;
    BufferedWriter  quizTex =   new BufferedWriter(new FileWriter(composite)) ;
    
    this.manifest.setRoot(MINT + quizId) ; 
    writePreamble(quizTex, "Hogwarts School", "Prof. Dumbledore");
    beginDoc(quizTex) ;
    
    for(int i = 0 ; i < students.length ; i++) {
      StudentType student = students[i] ; 
      String      name = student.getName() ;
      String      id = student.getId() ;
      // The human-readable 'name' passed here would be an invalid 
      // target in the Makefile because of the space b/w the first and 
      // last names. Hence, we need to generate a file name that would
      // be a valid target
      String      baseQR = quizId + "-" + id + "-" + name.split(" ")[0] ;
      String      atomic = MINT + quizId + "/staging/" + baseQR + ".tex" ;
      String      line = null ; 
      int         currPage = 1, currQues = 1 ;

      BufferedReader reader = new BufferedReader(new FileReader(blueprint)) ;
      BufferedWriter perStudent = new BufferedWriter(new FileWriter(atomic)) ;
      
      writePreamble(perStudent, "Hogwarts School", name) ;
      beginDoc(perStudent) ;

      BufferedWriter[] targets = {quizTex, perStudent} ;
      
      while ((line = reader.readLine()) != null) {
        if (line.contains("\\insertQR")) {
          String qrCode = baseQR + "-" + currPage + "-" + currQues ;
          for (int j = 0 ; j < targets.length ; j++) {
            targets[j].write("\\insertQR{" + qrCode + "}") ;
            targets[j].newLine() ;
          }
          continue ; // effectively, replace the \insertQR place-holder
        } else if (line.contains("\\newpage")) {
          currPage += 1 ;
        } else if (line.contains("\\question")) {
          currQues += 1 ;
        } 
        for (int j = 0 ; j < targets.length ; j++) {
          targets[j].write(line) ;
          targets[j].newLine() ;
        }
      }
      
      resetPageNumbering(quizTex) ;
      endDoc(perStudent) ;
      reader.close() ;
      perStudent.close() ;
    }
    endDoc(quizTex) ;
    quizTex.close() ;

    int ret = make(quizId) ;
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
  
  private boolean writePreamble(BufferedWriter stream, String school, String author) throws Exception {
    stream.write("\\documentclass[justified]{tufte-exam}") ;
    stream.newLine() ;
    stream.write("\\School{" + school + "}") ;
    stream.newLine() ;
    stream.write("\\DocAuthor{" + author + "}") ;
    stream.newLine() ;
    stream.write("\\fancyfoot[C]{\\copyright\\, Gutenberg}") ;
    stream.newLine() ;
    return true ;
  }
  
  private boolean beginDoc(BufferedWriter stream) throws Exception {
    stream.write("\\begin{document}") ;
    stream.newLine() ;
    stream.write("\\begin{questions}") ;
    stream.newLine() ;
    return true ;
  };
  
  private boolean endDoc(BufferedWriter stream) throws Exception {
    stream.write("\\end{questions}") ;
    stream.newLine() ;
    stream.write("\\end{document}") ;
    return true ;
  }
  
  private void resetPageNumbering(BufferedWriter stream) throws Exception {
	  stream.write("\\setcounter{page}{1}") ;
	  stream.newLine() ;
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
        
        content.append("\\insertQR{QRC}\n") ;
        content.append(f.get(new File(path))) ; // check for file existence?    
      }
      document[i] = content.toString() ;
    }
    return document ;
  }

  private int make(String quizId) throws Exception {
    ProcessBuilder processBuilder = new ProcessBuilder("make", "dir=" + quizId);
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
