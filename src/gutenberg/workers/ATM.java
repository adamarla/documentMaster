package gutenberg.workers;

import gutenberg.blocs.ManifestType;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class ATM {
	
	public ATM(Config config) throws Exception {
		ATMDir = config.getPath(Resource.atm);
	}
	
	public ManifestType deposit(File[] files) throws Exception {
		key = new File(ATMDir + "/" + generateKey());
		manifest = new ManifestType();
		for (int i = 0; i < files.length; i++) {
			Path link = key.toPath().resolve(files[i].getName());
			Files.createSymbolicLink(link, files[i].toPath());
		}
		return manifest;
	}
 	
	private String ATMDir;
	private File key;
	private ManifestType manifest;
	
	private String generateKey() {
		return UUID.randomUUID().toString();
	}
}
