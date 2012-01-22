package gutenberg.workers;

import java.io.File;
import java.io.FileReader;

public class Filer {
	
	public void load(String filepath) throws Exception {
		load(new File(filepath));
	}
	
	public void load(File file) throws Exception {
		if (!file.exists() || !file.canRead()) {
			throw new Exception(file.getPath() + " does not exist or cannot be read");
		}		
		char[] cbuf = new char[1024];
		StringBuilder builder = new StringBuilder();
		FileReader reader = new FileReader(file);
		int charsRead = 0;
		while ((charsRead = reader.read(cbuf)) != -1) {			
			builder.append(cbuf, 0, charsRead);
		}
		contents = builder.toString();
		
	}
	public String getContents() {
		return contents;
	}
	
	private String contents;

}
