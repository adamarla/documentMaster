package gutenberg.workers;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
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
            } else if (trimmed.startsWith(insertQR)) {
                String QRCode = String.format("{%s%s}", params.get(insertQR), 
                    ITagLib.pageNumber);
                line = line.replaceFirst("\\{.*\\}", 
                    Matcher.quoteReplacement(QRCode));
            }
            println(line);
        }
    }
    
    public void writePreamble(String quiz) {
        println("\\documentclass[12pt,a4paper,justified]{tufte-exam}");
        println(String.format("%s{%s}", school, quiz));
        println(fancyfooter);        
    }
    
    public void printAuthor(String author, int[] versions) {
        println(String.format("%s{%s}{%s}", docAuthor, author,
                Arrays.toString(versions).replace('[', '{').replace(']', '}')));
    }
    
    public void beginQuiz(int[] breaks) {
        println(String.format("%s{%s}", setPageBreaks, 
                Arrays.toString(breaks).replace('[', '{').replace(']', '}')));
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

    public void insertBlankPage() {
        print("\\begingroup");
        print("\\centering For rough work only. Will NOT be graded \\\\");
        println("\\endgroup");
        println(insertQR + BLANK_PAGE_CODE);
        println(newpage);
    }
    
    private final String BLANK_PAGE_CODE = "{0}";
        
}
