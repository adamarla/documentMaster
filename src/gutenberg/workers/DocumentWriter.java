package gutenberg.workers;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;


public class DocumentWriter extends PrintWriter implements ITagLib {
    
    public DocumentWriter(Path writerPath) throws Exception {
        super(Files.newBufferedWriter(writerPath,
            StandardCharsets.UTF_8, StandardOpenOption.CREATE));
    }
    
    public void writeTemplate(Path template) throws Exception{
        List<String> file = Files.readAllLines(template, StandardCharsets.UTF_8);
        for (String line : file) {
            println(line);
        }
    }
    
    public void writeTemplate(Path template,  HashMap<String, String>params) throws Exception{ 
        
        List<String> file = Files.readAllLines(template, StandardCharsets.UTF_8);
        for (String line : file) {
            
            String trimmed = line.trim();
            if (trimmed.startsWith(comment)) { // => a comment
                continue;
            } else if (trimmed.startsWith(ITagLib.printanswers)) {
                continue;
            } else if (trimmed.startsWith(ITagLib.printRubric)) {
                continue;
            } else if (trimmed.startsWith(rollDice)) {
                continue;
            } else if (trimmed.startsWith(insertQR)) {
                String QRCode = String.format("{%s%s}", params.get(insertQR), 
                    ITagLib.pageNumber);
                line = line.replaceFirst("\\{.*\\}", 
                    Matcher.quoteReplacement(QRCode));
            } else if (trimmed.startsWith(school)) {
                line = String.format("%s{%s}", school, params.get(school));
            } else if (trimmed.startsWith(docAuthor)) {
                line = String.format("%s{%s}",docAuthor, params.get(docAuthor));
            }
            println(line);
        }
    }
    
    public void writePreamble(String quiz) {
        println("\\documentclass[12pt,a4paper,justified]{tufte-exam}");
        println(String.format("%s{%s}", school, quiz));
        println("\\usepackage[absolute]{textpos}");
        println("\\usepackage{xstring}");
        println("\\TPGrid{600}{800}");
        println(fancyfooter);        
    }
    
    public void printHooksForVersions() {
        println("\\AfterEndEnvironment{solution}{");
        println("  \\setVersionS{\\versionS}");
        println("}");
        println("");
        println("\\AfterEndEnvironment{parts}{");
        println("  \\setVersionS{\\versionS}");
        println("}");
    }
        
    public void printVersions(String versions) {
        println(String.format("%s{%s}", resetVersions, versions));
    }
    
    public void printAuthor(String author) { 
        println(String.format("%s{%s}", docAuthor, author));        
    }
    
    public void beginQuiz() {
        println(beginDocument);
        println(beginQuestions);
    }

    public void endQuiz() {
        println(endQuestions);
        println(endDocument);
    }
    
    public void newPage() {
        println(newpage);
    }
    
    public void printAnswers() {
        println(printanswers);
    }
    
    public void printRubric() {
        println(printRubric);
    }

    public void resetPageNumbering() {
        println("\\setcounter{page}{1}");
    }

    public void resetQuestionNumbering() {
        println("\\setcounter{question}{0}");
    }
    
    public void rollDice(int i) {
        println(String.format("\\setcounter{rolldice}{%d}", i));
    }

    public void insertBlankPage() {
        print("\\begingroup");
        print("\\centering For rough work only. Will NOT be graded \\\\");
        println("\\endgroup");
        println(insertQR + BLANK_PAGE_CODE);
        println(newpage);
    }
    
    private final String BLANK_PAGE_CODE = "{0}";
        
}
