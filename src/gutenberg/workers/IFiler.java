package gutenberg.workers;

import java.io.File;

public interface IFiler {
		
	public void load(String filepath);
	public void load(File file);
	public String getContents();
	public void transfer(String targetpath);
	public void transfer(File target);	

}
