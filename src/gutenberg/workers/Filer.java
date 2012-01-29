package gutenberg.workers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;

public class Filer {
	
	public String get(String filepath) throws Exception {
		return get(new File(filepath));
	}	
	
	public String get(File file) throws Exception {		
		if (!file.exists() || !file.canRead()) {
			throw new Exception(file.getPath() + " does not exist or cannot be read");
		}		
		ByteArrayOutputStream baos = new ByteArrayOutputStream(DECENT_SIZE);
		Files.copy(file.toPath(), baos);		
		return baos.toString();		
	}
	
	public void copy(String sourcepath, String targetpath) throws Exception {
		copy(new File(sourcepath), new File(targetpath));
	}
	
	public void copy(File source, File target) throws Exception {
		if (!source.exists() || !source.canRead()) {
			throw new Exception(source.getPath() + " does not exist or cannot be read");
		}
		
		Files.createSymbolicLink(target.toPath(), source.toPath());
	}		
	
	private int DECENT_SIZE = 1024;

}
