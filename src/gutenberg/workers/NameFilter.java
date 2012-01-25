package gutenberg.workers;

import java.io.File;
import java.io.FilenameFilter;

public class NameFilter implements FilenameFilter {

	public NameFilter(String filter) {
		this.filter = filter;
	}
	
	@Override
	public boolean accept(File dir, String name) {
		return name.contains(filter);
	}
	
	private String filter;
	
}
