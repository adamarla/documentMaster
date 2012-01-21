package gutenberg.workers;

import java.io.File;

public interface IFiler {
	
	public String load(String filepath);
	
	public String load(File file);
	
	public void transfer(String sourcepath, String targetpath);
	
	public void transfer(File source, File target);	

}
