package gutenberg.workers;

import java.io.File;
import java.io.FilenameFilter;

public class Vault {
	
	public Vault(String vault) {
		VAULT = vault;
		filer = new Filer();
	}
	
	/**
	 * @param id - question id
	 * @param filter - type of content e.g. "tex", "gnuplot" etc.
	 * @return returns file contents for given id
	 * @throws Exception 
	 */
	public String[] get(String id, String filter) throws Exception {
		File directory = new File(VAULT + "/" + id);
		File[] files = directory.listFiles(new VaultFilter(filter));
		String[] contents = new String[files.length];
		for (int i = 0; i < files.length; i++) {			
			filer.load(files[i]);
			contents[i] = filer.getContents();
		}		
		return contents;
	}
	
	private Filer filer;
	private String VAULT;
}

class VaultFilter implements FilenameFilter {

	public VaultFilter(String filter) {
		this.filter = filter;
	}
	
	@Override
	public boolean accept(File dir, String name) {
		return name.endsWith(filter);
	}
	
	private String filter;
	
}