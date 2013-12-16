package gutenberg.workers;

public interface ITagLib {
    
    String comment ="%",
            printanswers = "\\printanswers",
            pageNumber = "\\alphnum{\\thepage}",
            docAuthor = "\\setAuthor",
            usepackage = "\\usepackage",
            beginDocument = "\\begin{document}",
            beginQuestions = "\\begin{questions}", 
            school = "\\setDocumentTitle",
            docClass = "\\documentclass", 
            insertQR = "\\insertQR",
            endQuestions = "\\end{questions}", 
            endDocument = "\\end{document}",
            question = "\\question",
            solution = "\\begin{solution}", 
            part = "\\part",            
            setPageBreaks = "\\setPageBreaks",
            setVersionTriggers = "\\setVersionTriggers";    
}
