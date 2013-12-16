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
    }
    
    public void beginDocument(int[] pageBreaks, int[] versionTriggers) {
        pageBreaks = pageBreaks == null ? new int[0] : pageBreaks;
        println(String.format("%s%s", setPageBreaks,
                Arrays.toString(pageBreaks).replaceFirst("(^\\[)(.*)(\\]$)", "{$2}")));
        println(String.format("%s%s", setVersionTriggers, 
                Arrays.toString(versionTriggers).replaceFirst("(^\\[)(.*)(\\]$)", "{$2}")));
        println(beginDocument);
    }
    
    public void endDocument() {
        println(endDocument);
    }
    
    public void beginQuiz(String author, int[] versions) {
        println(String.format("%s%s{%s}", docAuthor,
                Arrays.toString(versions), author));
        println(beginQuestions);
    }

    public void endQuiz() {
        println(endQuestions);
    }
    
    public void printAnswers() {
        println(printanswers);
    }
    
}
