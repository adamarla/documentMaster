package gutenberg.workers;

import gutenberg.blocs.ManifestType;

import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
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
        Path bundle;
        PrintWriter pw = null;
        for (String question : questions) {
            String[] tokens = question.split("\\|");
            bundle = vaultPath.resolve(tokens[1]).resolve("bundle.xml");
            pw = new PrintWriter(bundle.toFile());
            pw.println("<entry>");
            pw.println(String.format("\t<bundleId>%s</bundleId>", bundleId));
            pw.println(String.format("\t<questionId>%s</questionId>", tokens[0]));
            pw.println(String.format("\t<label>%s</label>", tokens[2]));
            pw.println("</entry>");
            pw.close();
        }
        
        ManifestType manifest = new ManifestType();
        manifest.setRoot(bundleId);        
        return manifest;
    }
    
    Path vaultPath, bundlePath;

}
