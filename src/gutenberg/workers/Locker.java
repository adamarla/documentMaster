package gutenberg.workers;

import gutenberg.blocs.EntryType;
import gutenberg.blocs.ManifestType;
import gutenberg.blocs.PointType;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

public class Locker {

    public Locker(Config config) throws Exception {
        lockerPath = new File(config.getPath(Resource.locker)).toPath();
        sharedPath = new File(config.getPath(Resource.shared)).toPath();
    }

    /**
     * Creates folder for storing scans related with
     * this test paper or for suggestion scans
     * @return Path to the place in the locked where scans will be recvd
     */
    public Path makeRoom(String quizId, String testpaperId) throws Exception {
        Path dirPath = lockerPath.resolve(
                String.format("%s-%s", quizId, testpaperId));
        if (!Files.exists(dirPath)) {
            Files.createDirectory(dirPath);
        }
        return dirPath;
    }
    
    public ManifestType uploadSuggestion(String signature, EntryType teacher,
            String content) throws Exception {
        Path teacherDirPath = lockerPath.resolve(
                String.format("0-%s", teacher.getId()));
        
        if (!Files.exists(teacherDirPath)) {
            Files.createDirectory(teacherDirPath);
        }
        
        byte[] raw = Base64.decodeBase64(content.getBytes());
        OutputStream ostream = Files.newOutputStream(
                teacherDirPath.resolve(signature), 
                StandardOpenOption.CREATE);
        ostream.write(raw);
        ostream.close();
        
        String imageFile = null, basename = null, extension = null;
        String[] tokens = signature.split("\\.");
        basename = tokens[0];
        extension = tokens[1];
        // {*.doc, *.docx, *.txt} => *.pdf
        if (DOCS.contains(extension)) {
            libreOfficeCmd[4] = signature;
            if (execute(teacherDirPath, libreOfficeCmd) != 0)
                throw new Exception(libreOfficeCmd[0] + " had an error");
            imageFile = basename + ".pdf";
        } else {
            imageFile = signature;
        }
        
        // {*.pdf, *.tiff, *.png ....} => *.jpg
        if (IMAGES.contains(imageFile.split("\\.")[1])) {
            convertCmd[1] = imageFile;
            convertCmd[6] += basename;
            if (execute(teacherDirPath, convertCmd) != 0)
                throw new Exception(convertCmd[0] + " had an error");
        } else {
            throw new Exception("Unhandled file format " + signature);            
        }

        ManifestType manifest = new ManifestType();
        manifest.setRoot(teacherDirPath.toString());
        
        DirectoryStream<Path> stream = 
                Files.newDirectoryStream(teacherDirPath, basename + "*");
        for (Path entry: stream) {
            String filename = entry.getFileName().toString();
            if (filename.equals(basename) || filename.contains("-")) {
                EntryType image = new EntryType();
                image.setId(filename);
                manifest.addImage(image);
            } else if (!filename.equals(signature)) {
                Files.delete(entry);
            }            
        }
        stream.close();
        
        EntryType suggestionFile = new EntryType();
        suggestionFile.setId(signature);
        manifest.addDocument(suggestionFile);
        
        return manifest;
    }
    
    
    public ManifestType save(File[] scans) throws Exception {

        ManifestType manifest = new ManifestType();
        manifest.setRoot(lockerPath.toString());

        String scanId = null, base36ScanId = null;
        boolean rotate = false;
        String[] tokens = null;
        for (File scan : scans) {
            
            //scanId(base36)_scanId(base10)_orientation
            tokens = scan.getName().split("_");
            if (tokens.length != 3) {
                scan.delete();
                continue;
            }

            base36ScanId = tokens[0];
            scanId = tokens[1];
            rotate = tokens[2].equals("1") ? true : false;
            
            //scanId(base10)=quizId-testpaperId-studentId-pageNo
            //scanId(suggestion)=0-[signature]-teacherId-1
            String[] subTokens = scanId.split("-");            
            boolean  isSuggestion = subTokens[0].equals("0") ;
            String   subpath = null ;

            if (isSuggestion) {
                subpath = String.format("0-%s/%s", subTokens[2],
                        subTokens[1]);
            } else {
                subpath = String.format("%s-%s/%s", subTokens[0],
                    subTokens[1], base36ScanId);
            }
            
            Path stored = lockerPath.resolve(subpath);            
            if (!Files.exists(stored)) {
                if (convert(scan.toPath(), stored, SCAN_SIZE, rotate) != 0) {
                    Files.copy(scan.toPath(), stored);
                }
                EntryType image = new EntryType() ;
                String value = stored.getFileName().toString(); 

                image.setId(scanId);
                image.setValue(value) ; 
                manifest.addImage(image);
            }
            
            scan.delete();
        }

        // workaround for de-serialization bug in Savon
        // https://github.com/rubiii/savon/issues/11 (supposedly fixed!)
        if (manifest.getImage() != null && manifest.getImage().length == 1) {
            EntryType dummyEntry = new EntryType();
            dummyEntry.setId("SAVON_BUG_SKIP");
            manifest.addImage(dummyEntry);
        }

        return manifest;
    }

    /**
     * Annotate various things, lines curves etc.
     * 
     * @param scanId
     * @throws Exception
     */
    public ManifestType annotate(String scanId, PointType[] points)
            throws Exception {

        ManifestType manifest = new ManifestType();
        manifest.setRoot(scanId);

        Path imagePath = lockerPath.resolve(scanId);
        File imageFile = imagePath.toFile();
        
        //Create a back up in case we want to revert grading for the page
        Path backupPath = imagePath.getParent().
                resolve("." + imagePath.getFileName());
        if (!Files.exists(backupPath))
            Files.copy(imagePath, backupPath);
        
        BufferedImage image = ImageIO.read(imageFile);
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        BasicStroke s = new BasicStroke(STROKE_WIDTH, BasicStroke.CAP_ROUND, 
                BasicStroke.JOIN_ROUND);
        graphics.setStroke(s);
        graphics.setFont(COMMENT_FONT);

        BufferedImage overlay = null;
        ArrayList<PointType> curve = new ArrayList<PointType>();
        ArrayList<PointType> annotations = new ArrayList<PointType>();
        for (PointType point : points) {
            
            curve.add(point);
            if (point.isCodeSpecified()) {
                
                switch(point.getCode()) {
                    case 0:
                        overlay = ImageIO.read(this.getClass().getClassLoader()
                                .getResourceAsStream("META-INF/cross-mark.png"));
                        break;
                    case 1:
                        overlay = ImageIO.read(this.getClass().getClassLoader()
                                .getResourceAsStream("META-INF/check-mark.png"));
                        break;
                    case 2:
                        overlay = ImageIO.read(this.getClass().getClassLoader()
                                .getResourceAsStream("META-INF/question-mark.png"));
                        break;
                    case 3:
                        annotations.add(point);
                        break;
                    case 4:
                        break;
                    default:
                        throw new Exception(
                                String.format("Invalid annotation code (%s)", 
                                        point.getCode()));
                }
                
                int nPoints = curve.size();
                if (nPoints > 1) {
                    graphics.setColor(new Color(OUTLINE));
                    int[] xPoints = new int[nPoints], yPoints = new int[nPoints];
                    for (int i = 0; i < nPoints; i++) {                    
                        xPoints[i] = curve.get(i).getX();
                        yPoints[i] = curve.get(i).getY();                    
                    }
                    graphics.drawPolyline(xPoints, yPoints, nPoints);
                } else if (overlay != null){
                    graphics.drawImage(overlay, point.getX()-10, point.getY(), null);
                    overlay = null;
                } 
                curve.clear();
            }
        }
        
        if (annotations.size() != 0) {
            Path overlayPath = lockerPath.resolve(scanId + ".overlay");
            overlay = this.getOverlay(overlayPath,
                    annotations.toArray(new PointType[annotations.size()]));
            graphics.drawImage(overlay, -50, -10, null);
            Files.delete(overlayPath);
        }

        ImageIO.write(image, FORMAT, imageFile);
        return manifest;
    }
    
    public ManifestType undoAnnotate(String scanId) throws Exception {

        ManifestType manifest = new ManifestType();
        manifest.setRoot(lockerPath.toString());

        Path imagePath = lockerPath.resolve(scanId);
        Path backupPath = imagePath.getParent().
                resolve("." + imagePath.getFileName());
        if (Files.exists(backupPath)) {
            
            Files.copy(backupPath, imagePath, StandardCopyOption.REPLACE_EXISTING);
            
            EntryType image = new EntryType() ;
            String value = scanId;

            image.setId(scanId);
            image.setValue(value) ; 
            manifest.addImage(image);
        } else {
            throw new Exception(String.format("%s not found", scanId));
        }
            
        // workaround for de-serialization bug in Savon
        // https://github.com/rubiii/savon/issues/11 (supposedly fixed!)
        if (manifest.getImage() != null && manifest.getImage().length == 1) {
            EntryType dummyEntry = new EntryType();
            dummyEntry.setId("SAVON_BUG_SKIP");
            manifest.addImage(dummyEntry);
        }

        return manifest;
    }    
    
    public ManifestType rotate(String scanId) throws Exception {

        ManifestType manifest = new ManifestType();
        manifest.setRoot(lockerPath.toString());

        Path imageFile = lockerPath.resolve(scanId);
        if (Files.exists(imageFile)) {
            
            convert(imageFile, imageFile, SCAN_SIZE, true);
                        
            EntryType image = new EntryType() ;
            String value = imageFile.getFileName().toString(); 

            image.setId(scanId);
            image.setValue(value) ; 
            manifest.addImage(image);
        } else {
            throw new Exception(String.format("%s not found", scanId));
        }
            
        // workaround for de-serialization bug in Savon
        // https://github.com/rubiii/savon/issues/11 (supposedly fixed!)
        if (manifest.getImage() != null && manifest.getImage().length == 1) {
            EntryType dummyEntry = new EntryType();
            dummyEntry.setId("SAVON_BUG_SKIP");
            manifest.addImage(dummyEntry);
        }

        return manifest;
    }    
    
    private int convert(Path src, Path target, String size, boolean rotate)
            throws Exception {
        
        return execute(lockerPath, new String[]{"convert", src.toString(), "-resize",
                size, "-type", "TrueColor"});
    }
    
    private BufferedImage getOverlay(Path overlayPath, PointType[] annotations) throws Exception {

        Path workingDirPath = Files.createTempDirectory(lockerPath, "annotation");
        
        Files.createSymbolicLink(workingDirPath.resolve("Makefile"),
                sharedPath.resolve("makefiles/annotation.mk"));

        Path annotationTex = workingDirPath.resolve("annotation.tex");
        Files.copy(sharedPath.resolve("templates/annotation.tex"),
                annotationTex, StandardCopyOption.REPLACE_EXISTING);

        List<String> lines = Files.readAllLines(annotationTex, StandardCharsets.UTF_8);
        lines.add("\\begin{document}");        
        for (PointType annotation: annotations) {
            lines.add(String.format("\\begin{textblock}{600}(%s,%s)", annotation.getX(), annotation.getY()));
            //lines.add(String.format("\\[rgb]{0.25,0.57,0.16}%s", annotation.getText()));
            lines.add(String.format("\\color{blue}%s", annotation.getText()));
            lines.add("\\end{textblock}");
        }
        lines.add("\\end{document}");
        Files.write(annotationTex, lines, StandardCharsets.UTF_8);
        
        BufferedImage overlay = null;
        if (execute(workingDirPath, new String[]{"make"}) == 0) {
            Files.move(workingDirPath.resolve("overlay.png"), overlayPath);
            Files.delete(workingDirPath.resolve("Makefile"));
            Files.delete(workingDirPath);
            overlay = ImageIO.read(overlayPath.toFile());
        }
        return overlay;
    }
    
    private int execute(Path workingDirPath, String[] commands) throws Exception {
        
        System.out.print("[Locker] execute:");
        for (String s : commands) {
            System.out.print(s + " ");
        }
        System.out.println();
        
        ProcessBuilder pb = new ProcessBuilder();
        pb.environment().put("HOME", "/opt/tomcat6-writable/");
        pb.command(commands);

        pb.directory(workingDirPath.toFile());
        pb.redirectErrorStream(true);

        Process build = pb.start();
        BufferedReader messages = new 
                BufferedReader(new InputStreamReader(build.getInputStream()));

        String line = null;
        while ((line = messages.readLine()) != null) {
            System.out.println(line);
        }        
        return build.waitFor();
    }

    private Path lockerPath, sharedPath;
    private String[] libreOfficeCmd = 
        {"libreoffice", "--headless", "--convert-to", "pdf", ""};
    private String[] convertCmd = 
        {"convert", "", "-resize", "600x800", "-scene", "1", "jpg:"};

    private final String SCAN_SIZE = "600x800";
    private final float  STROKE_WIDTH = 3f;
    private final String FORMAT    = "JPG";
    private final int    OUTLINE = 0xf6bd13;
    private final Font COMMENT_FONT = new Font("Ubuntu", 0, 12);
    
    private static final Set<String> 
        IMAGES = new HashSet<String>(Arrays.asList("tif", "tiff", "png", "jpg", "jpeg",
                "gif", "bmp", "pdf")), 
        DOCS = new HashSet<String>(Arrays.asList("doc", "docx", "txt"));
}
