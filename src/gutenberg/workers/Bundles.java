package gutenberg.workers;

import gutenberg.blocs.ManifestType;

import java.security.MessageDigest;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bundles {    
    
    public Bundles(Config config) throws Exception {
        vaultPath = config.getPath(Resource.vault);
        bundlePath = config.getPath(Resource.bundles);
    }
    
    /**
     * Creates a bundle
     * 
     * @param examinerId - id of person creating question
     * @return Manifest
     * @throws Exception
     */
    public ManifestType create(String bundleId) throws Exception {
        Path newBundle = bundlePath.resolve(String.format("%s.zip", bundleId));
        final URI uri = URI.create("jar:file:" + newBundle.toUri().getPath());
       
        final Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        FileSystems.newFileSystem(uri, env);
        
        ManifestType manifest = new ManifestType();
        manifest.setRoot(bundleId);
        
        return manifest;
    }
    
    public ManifestType addTo(String bundleId, String[] questions) throws Exception {
        Path zipfile = bundlePath.resolve(String.format("%s.zip", bundleId));
        if (Files.notExists(zipfile)) create(bundleId);
        
        try (FileSystem fs = FileSystems.newFileSystem(zipfile, null)) {
            
            final Path root = fs.getPath("/");
            String manifestXML = String.format("%s.xml", bundleId);
            Path zipManifest = vaultPath.resolve(manifestXML);
            PrintWriter pw = new PrintWriter(zipManifest.toFile());
            
            // Copy existing manifest entries (if any) OR start new one
            if (Files.exists(root.resolve(manifestXML))) {
                List<String> lines = Files.readAllLines(root.resolve(manifestXML), 
                    StandardCharsets.UTF_8);
                pw.println(lines.get(0));
                boolean skip = false;
                for (int i = 1; i < lines.size()-1; i++) {
                    for (String question : questions) {
                        if (lines.get(i).contains(question.split("\\|")[1])) {
                            skip = true;
                            break;
                        }
                    }
                    if (!skip) pw.println(lines.get(i));
                }
            } else {
                pw.println(String.format("<qbundle tag=\"%s\">", bundleId));
            }
            
            Path srcDir, destDir = null;
            for (String question : questions) {
                String[] tokens = question.split("\\|");
                String id=tokens[0], path = tokens[1], label = tokens[2];
                
                // Create question dir
                destDir = fs.getPath(root.toString(), path.replace('/', '-'));
                if (Files.notExists(destDir)) Files.createDirectory(destDir);
                
                srcDir = vaultPath.resolve(path);
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(srcDir, "*.svg")) {
                    for (Path entry: stream) {
                        Files.copy(entry, destDir.resolve(entry.getFileName().toString()), 
                            StandardCopyOption.REPLACE_EXISTING);
                    }
                    Files.copy(srcDir.resolve(QSN_XML), destDir.resolve(QSN_XML), 
                        StandardCopyOption.REPLACE_EXISTING);
                }
                
                String signature = getSHA1Sum(srcDir.resolve(QSN_XML));
                pw.println(String.format(QSN_TAG, path, label, id, signature));
                System.out.println("Processed " + srcDir.resolve(QSN_XML).toString());
            }
            pw.println("</qbundle>");
            pw.close();
            
            Files.copy(zipManifest, root.resolve(manifestXML), StandardCopyOption.REPLACE_EXISTING);
            Files.delete(zipManifest);
        }
        ManifestType manifest = new ManifestType();
        manifest.setRoot(bundleId);
        
        return manifest;
    }
    
    private String getSHA1Sum(Path path) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        int bytesRead = 0;
        byte[] byteBuf = new byte[1024];
        try (java.io.InputStream is = new FileInputStream(path.toFile())) {
            while ((bytesRead = is.read(byteBuf)) != -1) {
                md.update(byteBuf, 0, bytesRead);
            }
        }
        
        byte[] SHA1digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : SHA1digest){
            sb.append(String.format("%02x", b));
        }
        
        return sb.toString().substring(0, 12);
    }
    
    Path vaultPath, bundlePath;
    final String QSN_XML = "question.xml";
    final String QSN_TAG = "    <question tag=\"%s\" label=\"%s\" id=\"%s\" signature=\"\"/>";

}
