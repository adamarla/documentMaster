package gutenberg.workers;

import java.nio.file.Files;
import java.io.File; 
import java.io.FileFilter; 
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {

    public Config() throws Exception {
        Properties properties = new Properties();
        properties.loadFromXML(Files.newInputStream(Paths.get("/opt/gutenberg/config.xml")));
        bankRoot = properties.getProperty("BANK_ROOT");
        
        File f = new File("/usr/local/texlive") ;
        FileFilter filter = new FileFilter() {
        	public boolean accept(File j) {
        		return (j.isDirectory() && j.getName().startsWith("20")) ;
        	}
        } ;
        File j = f.listFiles(filter)[0] ;
        latexRoot = ( j == null ) ? null : new String(j.getAbsolutePath()) ;
    }

    public Path getPath(Resource id) {
        Path path = Paths.get(bankRoot);
        switch (id) {
        case bank:
            break;
        case mint:
            path = path.resolve("mint");
            break;
        case locker:
            path = path.resolve("locker");
            break;
        case atm:
            path = path.resolve("atm");
            break;
        case vault:
            path = path.resolve("vault");
            break;
        case shared:
            path = path.resolve("shared");
            break;
        case scantray:
            path = path.resolve("scantray");
            break;
        case frontdesk:
            path = path.resolve("front-desk");
            break;
        case common:
            path = path.resolve("common");
            break ;
        case latexRoot: 
        	path = Paths.get(latexRoot) ;
        default:
        }
        return path;
    }

    private String bankRoot, latexRoot ;
}
