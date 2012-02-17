package gutenberg.workers;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {

	public Config() throws Exception {
		Properties properties = new Properties();
		properties.loadFromXML(
				new FileInputStream("/opt/gutenberg/config.xml"));
		bankRoot = properties.getProperty("BANK_ROOT");
	}

	public String getPath(Resource id) {
		String path = bankRoot;
		switch (id) {
		case bank:
			break;
		case mint:
			path += "/mint/";
			break;
		case locker:
			path += "/locker/";
			break;
		case atm:
			path+= "/atm/";
			break;
		case vault:
			path += "/vault";
			break;
		case shared:
			path += "/shared/";
			break;
		case staging:
			path += "/staging/";
			break;
		default:
		}
		return path;
	}

	private String bankRoot;
}
