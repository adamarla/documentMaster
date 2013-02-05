package gutenberg.workers;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class DocumentUtils {
    
    public DocumentUtils(String school, String author) {
        
    }
    
    /*
    public void writePreamble(PrintWriter writer, String school, String author)
            throws Exception {
        List<String> file = Files.readAllLines(
                sharedPath.resolve("templates/preamble.tex"), StandardCharsets.UTF_8);
        String[] lines = new String[file.size()];
        lines = file.toArray(lines);

        for (int j = 0; j < lines.length; j++) {
            String line = lines[j];
            String trimmed = line.trim();

            if (trimmed.startsWith(this.school)) {
                writer.println(this.school + "{" + school + "}");
            } else if (trimmed.startsWith(docAuthor)) {
                if (author == null)
                    author = "Gutenberg";
                writer.println(docAuthor + "{" + author + "}");
            } else { // write whatever else is in preamble.tex
                writer.println(line);
            }
        }
    }

    public void beginDoc(PrintWriter writer) throws Exception {
        writer.println(beginDocument);
        writer.println(beginQuestions);
    }

    public void endDoc(PrintWriter writer) throws Exception {
        writer.println(endQuestions);
        writer.println(endDocument);
    }

    public void resetPageNumbering(PrintWriter writer) throws Exception {
        writer.println("\\setcounter{page}{1}");
    }

    public void resetQuestionNumbering(PrintWriter writer) throws Exception {
        writer.println("\\setcounter{question}{0}");
    }

    public void insertBlankPage(PrintWriter writer) {
        writer.print("\\begingroup");
        writer.print("\\centering For rough work only. Will NOT be graded \\\\ ");
        writer.println("\\endgroup");
        writer.println(insertQR + BLANK_PAGE_CODE);
        writer.println(newpage);
    }
    */

}
