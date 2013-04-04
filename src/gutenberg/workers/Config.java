package gutenberg.workers;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.Properties;

public class Config {

    public Config() throws Exception {
        Properties properties = new Properties();
        properties
                .loadFromXML(new FileInputStream("/opt/gutenberg/config.xml"));
        bankRoot = properties.getProperty("BANK_ROOT");
    }

    public Path getPath(Resource id) {
        Path path = new File(bankRoot).toPath();
        switch (id) {
        case bank:
            break;
        case mint:
            path.resolve("mint");
            break;
        case locker:
            path.resolve("locker");
            break;
        case atm:
            path.resolve("atm");
            break;
        case vault:
            path.resolve("vault");
            break;
        case shared:
            path.resolve("shared");
            break;
        case staging:
            path.resolve("staging");
            break;
        case frontdesk:
            path.resolve("front-desk");
            break;
        default:
        }
        return path;
    }

    private String bankRoot;
}
