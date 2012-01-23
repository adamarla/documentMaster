package gutenberg.workers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

public class Filer {
	
	public String get(String filepath) throws Exception {
		return get(new File(filepath));
	}	
	
	public String get(File file) throws Exception {
		if (!file.exists() || !file.canRead()) {
			throw new Exception(file.getPath() + " does not exist or cannot be read");
		}		
		char[] cbuf = new char[DECENT_SIZE];
		StringBuilder builder = new StringBuilder();
		FileReader reader = new FileReader(file);
		int charsRead = 0;
		while ((charsRead = reader.read(cbuf)) != -1) {			
			builder.append(cbuf, 0, charsRead);
		}
		return builder.toString();
	}
	
	public void copy(String sourcepath, String targetpath) throws Exception {
		copy(new File(sourcepath), new File(targetpath));
	}
	
	public void copy(File source, File target) throws Exception {
		byte[] bbuf = new byte[DECENT_SIZE];
		FileInputStream filein = new FileInputStream(source);
		FileOutputStream fileout = new FileOutputStream(target);
		int bytesread = 0;
		while ((bytesread = filein.read(bbuf)) != -1) {
			fileout.write(bbuf, 0, bytesread);
		}
		filein.close();
		fileout.close();
	}
	
	private int DECENT_SIZE = 1024;

}
