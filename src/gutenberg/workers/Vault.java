package gutenberg.workers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;

public class Vault {
	
	public Vault(String vault) {
		VAULT = vault;
	}
	
	/**
	 * @param id - question id
	 * @param filter - type of content e.g. "tex", "gnuplot" etc.
	 * @return returns file contents for given id
	 * @throws Exception 
	 */
	public String[] getContent(String id, String filter) throws Exception {
		File directory = new File(VAULT + "/" + id);
		File[] files = directory.listFiles(new NameFilter(filter));
		String[] contents = new String[files.length];
		for (int i = 0; i < files.length; i++) {			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Files.copy(files[i].toPath(), baos);
			contents[i] = baos.toString();
		}		
		return contents;
	}

	/**
	 * @param id - question id
	 * @param filter - type of content e.g. "tex", "gnuplot" etc.
	 * @return TODO
	 * @throws Exception
	 */ 
	public File[] getFiles(String id, String filter) throws Exception{
		File directory = new File(VAULT + "/" + id);
		return directory.listFiles(new NameFilter(filter));
	}

	public String getPath() throws Exception {
		return this.VAULT ;
	}
	
	private String VAULT;
}

