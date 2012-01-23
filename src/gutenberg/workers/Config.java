package gutenberg.workers;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {
	
	public Config() throws Exception {
		Properties properties = new Properties();
		properties.loadFromXML(new FileInputStream("/opt/gutenberg/config.xml"));
		bankRoot = properties.getProperty("BANK_ROOT");				
	}
	
	public String getPath(Resource id) {
		String path = bankRoot;
		switch(id) {
			case bank:				
			case mint:
				path += "/mint";
			case locker:
				path += "/locker";
			case vault:
				path += "/vault";
			case shared:
				path += "/shared";
			default:				
		}
		return path;		
	}

	private String bankRoot;
}
