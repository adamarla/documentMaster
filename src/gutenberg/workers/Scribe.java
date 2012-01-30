package gutenberg.workers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Properties;

import gutenberg.blocs.AssignmentType;
import gutenberg.blocs.EntryType;
import gutenberg.blocs.ManifestType;
import gutenberg.blocs.PageType;
import gutenberg.blocs.QuizType;

public class Scribe {

  public Scribe() throws Exception {
	Properties config = new Properties();

	config.loadFromXML(new FileInputStream("/opt/gutenberg/config.xml"));
    this.bankRoot = config.getProperty("BANK_ROOT") ;
    this.webRoot = config.getProperty("WEB_ROOT") ; 
    this.mint = this.bankRoot + "/mint/" ;
    this.vault = new Vault(this.bankRoot + "/vault/") ;
  } 

  /**
   * creates an answer key pdf for a quiz and preview jpgs
   * 
   * @param quiz
   * @throws Exception
   */
  public void generate(QuizType quiz) throws Exception {

    String quizId = quiz.getQuiz().getId();
    File quizDir = new File(this.mint + quizId);
    File staging = new File(this.mint + quizId + "/answer-key/staging") ;

    boolean failed = (this.make("Prepare",quizId, null) == 0) ? false : true ;

    if (failed) { 
      throw new Exception("[generateQuiz] : Sandbox creation failed ... ( quiz = " + quizId + " )") ;
    } 
    
    //System.out.println("[Scribe] : mint = " + this.mint) ;
    PrintWriter answerkey = new PrintWriter(staging + "/answer-key.tex");

    writePreamble(answerkey, quiz.getSchool().getName(), quiz.getTeacher().getName());
    beginDoc(answerkey);
    answerkey.println(printanswers);

    PageType[] pages = quiz.getPage();
    
    for (int i = 0; i < pages.length; i++) {
      PrintWriter preview = new PrintWriter(staging + "/page-" + i + ".tex") ;
      String page = null;

      writePreamble(preview, quiz.getSchool().getName(), quiz.getTeacher().getName());
      beginDoc(preview);
      preview.println(printanswers);
      
      page = preparePage(pages[i], staging);
      preview.println(page);
      answerkey.println(page);
      endDoc(preview);
      preview.close();
      if (i < pages.length - 1) {
        answerkey.println(newpage);
      }
    }
    
    endDoc(answerkey);
    answerkey.close();

    // make command : make Compile QUIZ=<quizId>
    System.out.println("Return Code: " + make("Compile", quizId,null));
    prepareManifest(quiz);
  }

  /**
   * create an assignment
   * 
   * @param assignment
   * @throws Exception
   */
  public void generate(AssignmentType assignment) throws Exception {

    String quizId = assignment.getQuiz().getId();
    String instanceId = assignment.getInstance().getId();
    File quizDir = new File(this.mint + quizId);
    File instanceDir = new File(quizDir + "/" + instanceId);
    File staging = new File(instanceDir + "/staging");

    if (!quizDir.exists()) {
      throw new Exception("Sorry! Cannot assign non-existent quiz: "
          + quizId);
    }
    // Note: staging directory is different from quizDir/staging
    // this is quizDir/instanceDir/staging
    instanceDir.mkdir();
    staging.mkdir();

    // link the plot files from the original quiz staging folder
    File quiz_staging = new File(quizDir + "/answer-key/staging");
    File[] files = quiz_staging.listFiles(new NameFilter("gnuplot"));
    for (int i = 0; i < files.length; i++) {
      linkResources(files[i], staging, files[i].getName());
    }    
    //TODO - may need to change depending on what the final preview directory is
    int totalPages = quiz_staging.list(new NameFilter("thumbnail")).length - 1;

    PrintWriter composite = new PrintWriter(staging + "/assignment.tex");
    PrintWriter individual = null;

    EntryType[] students = assignment.getStudents();
    for (int i = 0; i < students.length; i++) {

      individual = new PrintWriter(staging + "/" + students[i].getId()
          + "-assignment.tex");
      int pageNumber = 1, questionNumber = 1;

      String line = null, text = null;
      BufferedReader reader = new BufferedReader(new FileReader(quiz_staging
          + "/answer-key.tex"));
      while ((line = reader.readLine()) != null) {

        text = line.trim();
        if (text.startsWith(newpage)) {
          pageNumber++;
        }

        if (text.startsWith(question)) {
          questionNumber++;
        }

        if (text.startsWith(insertQR)) {
          line = line.replace("QRC", String.format(
              "%s.%s.%s.%s.%s.%s", quizId, pageNumber,
              totalPages, questionNumber, students[i].getId(),
              students[i].getName()));
        }

        if (text.startsWith(docAuthor)) {
          line = String.format(docAuthor + "{%s}",
              students[i].getName());
        }

        if (text.startsWith(printanswers)) {
          line = "";
        }

        individual.println(line);
        
        
        // docBegin and docEnd print only once for composite document
        if (text.startsWith(beginDocument) && i != 0) {
          line = "";
        }
        if (text.startsWith(endDocument) && i != students.length - 1) {
          line = "";
        }
        //TODO documentClass needs to be printed only once maybe?
        if (text.startsWith(docClass) && i != 0) {
          line = "";
        }        
        composite.println(line);
      }
      // We are not yet done with the composite document. So, do the
      // following
      resetPageNumbering(composite);
      resetQuestionNumbering(composite);

      endDoc(individual);
      individual.close();
      composite.flush();
    }
    endDoc(composite);
    composite.close();

    // make command : make Compile QUIZ=<quizId> TEST=<instanceId>
    System.out.println("Return Code: " + make("Compile", quizId, instanceId)) ;
    prepareManifest(assignment);
  }

  public ManifestType getManifest() {
    return manifest;
  }
  private String preparePage(PageType page, File staging) throws Exception {
    StringBuilder contents = new StringBuilder();
    EntryType[] questionIds = page.getQuestion();
    String questionId = null, question = null;
    File[] resources = null;
    for (int i = 0; i < questionIds.length; i++) {
      questionId = questionIds[i].getId();
      question = this.vault.getContent(questionId, "question.tex")[0];
      contents.append(insertQR).append('\n');
      contents.append(question);
      resources = this.vault.getFiles(questionId, "figure.gnuplot");      
      linkResources(resources[0], staging, questionId + ".gnuplot");
    }
    return contents.toString();
  }

  private void linkResources(File resource, File targetDir, String targetFile)
      throws Exception {
    File target = new File(targetDir.getPath() + "/" + targetFile);
    if (!target.exists())
      Files.createSymbolicLink(target.toPath(), resource.toPath());
  }
  
  private void writePreamble(PrintWriter writer, String school, String author)
      throws Exception {
    writer.println("\\documentclass[justified]{tufte-exam}");
    writer.println("\\School{" + school + "}");
    writer.println("\\DocAuthor{" + author + "}");
    writer.println("\\fancyfoot[C]{\\copyright\\, Gutenberg}");
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

  private int make(String operation, String quizId, String testpaperId) throws Exception {
  testpaperId = (testpaperId == null || testpaperId.length() == 0) ? "" : testpaperId ;
    ProcessBuilder pb = new ProcessBuilder("make", operation, "QUIZ=" + quizId,
                                           "PRINTING_PRESS=" + this.bankRoot,
                                           "TEST=" + testpaperId) ;
                                           
    System.out.println("[make] : make " + operation + " QUIZ=" + quizId + " TEST=" + testpaperId) ;

    pb.directory(new File(this.mint)) ;
    pb.redirectErrorStream(true) ;

    Process        process = pb.start() ;
    BufferedReader messages = new BufferedReader(new InputStreamReader(process.getInputStream())) ;
    String         line = null ;

    while ((line = messages.readLine()) != null) {
      System.out.println(line) ;
    }
    return process.waitFor() ;
  }

  private void prepareManifest(QuizType quiz) throws Exception {

    manifest = new ManifestType();
    String quizId = quiz.getQuiz().getId();
    manifest.setRoot(webRoot + "/" + quizId);
    String previewDir = this.mint + quizId + "/answer-key/preview";

    int pages = quiz.getPage().length;
    EntryType[] images = new EntryType[pages * 2];
    String filename = null;
    for (int i = 0; i < pages; i++) {

      filename = "page-" + i + ".jpg";
      if (new File(previewDir + "/" + filename).exists()) {
        images[2 * i] = new EntryType();
        images[2 * i].setId(filename);
      } else {
        throw new Exception(filename
            + " missing! All preview files not prepared.");
      }

      filename = "thumb-" + i + ".jpg";
      if (new File(previewDir + "/" + filename).exists()) {
        images[2 * i + 1] = new EntryType();
        images[2 * i + 1].setId(filename);
      } else {
        throw new Exception(filename
            + " missing! All preview thumbnail files not prepared.");
      }

    }
    manifest.setImage(images);

    String downloadsDir = this.mint + quizId + "/answer-key/downloads";
    EntryType[] documents = new EntryType[1];
    if (!new File(downloadsDir + "/answer-key.pdf").exists()) {
      throw new Exception("answer-key.pdf is missing!");
    }
    documents[0] = new EntryType();
    documents[0].setId("answerkey.pdf");
    manifest.setDocument(documents);
  }

  private void prepareManifest(AssignmentType assignment) throws Exception {

    manifest = new ManifestType();
    String quizId = assignment.getQuiz().getId();
    String instanceId = assignment.getInstance().getId();
    manifest.setRoot(webRoot + "/" + quizId + "/" + instanceId);

    EntryType[] students = assignment.getStudents();
    EntryType[] documents = new EntryType[students.length + 1];
    String filename = null;
    String downloadsDir = this.mint + quizId + "/" + instanceId;
    for (int i = 0; i < students.length; i++) {

      filename = students[i].getId() + "-assignment.pdf";
      if (new File(downloadsDir + "/" + filename).exists()) {
        documents[i] = new EntryType();
        documents[i].setId(filename);
      } else {
        throw new Exception(filename
            + " missing! All assignment files not prepared.");
      }

    }

    if (!new File(downloadsDir + "/assignment.pdf").exists()) {
      throw new Exception("assignment.pdf missing!");
    }
    documents[students.length] = new EntryType();
    documents[students.length].setId("assignment.pdf");
    manifest.setDocument(documents);
  }

  private String       bankRoot, mint, webRoot ;
  private Vault        vault;
  private ManifestType manifest;
  private final String printanswers = "\\printanswers",
                       docAuthor = "\\DocAuthor", 
                       newpage = "\\newpage",
                       question = "\\question", 
                       beginDocument = "\\begin{document}",
                       beginQuestions = "\\begin{questions}", 
                       docClass = "\\documentclass",
                       insertQR = "\\insertQR{QRC}", 
                       endQuestions = "\\end{questions}",
                       endDocument = "\\end{document}" ;


}
