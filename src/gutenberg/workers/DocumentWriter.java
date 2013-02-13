package gutenberg.workers;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class DocumentWriter {
    
    PrintWriter writer;
    Path templates;
    
    public DocumentWriter(PrintWriter writer, Path templates) {
        this.templates = templates;
        this.writer = writer;
    }
    
    public void writeTemplate(String template, Map<String, String> params)
            throws Exception {
        List<String> file = Files.readAllLines(templates.resolve(template), 
                StandardCharsets.UTF_8);
        String[] lines = new String[file.size()];
        lines = file.toArray(lines);

        for (int j = 0; j < lines.length; j++) {
            String line = lines[j];
            String trimmed = line.trim();

            if (trimmed.startsWith(this.school)) {
                writer.println(this.school + "{" + params.get(this.school) + "}");
            } else if (trimmed.startsWith(docAuthor)) {
                if (params.get(this.docAuthor) == null)
                    params.put(this.docAuthor, "Gutenberg");
                writer.println(docAuthor + "{" + params.get(this.docAuthor) + "}");
            } else { // write whatever else is in preamble.tex
                writer.println(line);
            }
        }
    }
    
    public void printAnswers() {
        writer.println(printanswers);
    }

    public void beginDoc() {
        writer.println(beginDocument);
        writer.println(beginQuestions);
    }

    public void endDoc() {
        writer.println(endQuestions);
        writer.println(endDocument);
    }

    public void resetPageNumbering() {
        writer.println("\\setcounter{page}{1}");
    }

    public void resetQuestionNumbering() {
        writer.println("\\setcounter{question}{0}");
    }
    
    public void setCounter(int i) {
        writer.println(String.format("\\setcounter{rolldice}{i}", i));
    }

    public void insertBlankPage() {
        writer.print("\\begingroup");
        writer.print("\\centering For rough work only. Will NOT be graded \\\\");
        writer.println("\\endgroup");
        writer.println(insertQR + BLANK_PAGE_CODE);
        writer.println(newpage);
    }
        
    private final String school = "\\School", docAuthor = "\\DocAuthor", 
            beginDocument = "\\begin{document}", endDocument = "\\end{document}",
            beginQuestions = "\\begin{questions}", endQuestions = "\\end{questions}", 
            insertQR = "\\insertQR", newpage = "nextpg", printanswers = "\\printanswers", 
            BLANK_PAGE_CODE = "\\DocAuthor{0}";    

}
