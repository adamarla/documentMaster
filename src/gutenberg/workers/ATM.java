package gutenberg.workers;

import gutenberg.blocs.EntryType;
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
		File key = new File(ATMDir + "/" + generateKey());
		if (!key.mkdir()) {
			throw new Exception("Could not create " + key);
		}
		ManifestType manifest = new ManifestType();
		manifest.setRoot(key.getPath());
		for (int i = 0; i < files.length; i++) {
			Path link = key.toPath().resolve(files[i].getName());
			Files.createSymbolicLink(link, files[i].toPath());
			
			EntryType image = new EntryType();
			image.setId(files[i].getName());
			manifest.addImage(image);
		}
		return manifest;
	}
 	
	private String ATMDir;
	
	private String generateKey() {
		return UUID.randomUUID().toString();
	}
}
