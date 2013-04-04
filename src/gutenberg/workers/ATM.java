package gutenberg.workers;

import gutenberg.blocs.ManifestType;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

/**
 * This class has been designed as a Singleton because the seeding algorithm of
 * the Random number generator is a much poorer way to randomize when compared
 * with the call to get the next random number.
 * 
 * @author adamarla
 * 
 */
public class ATM {

    public static ATM instance(Config config) throws Exception {
        if (ATM.atm == null) {
            ATM.atm = new ATM(config);
        }
        return ATM.atm;
    }

    private ATM(Config config) throws Exception {
        atmPath = config.getPath(Resource.atm);
        random = new Random();
    }

    public ManifestType deposit(Path directory, String key) throws Exception { 
        Path atmKey = atmPath.resolve(key);
        Path rel = atmPath.relativize(directory);
        Files.createSymbolicLink(atmKey, rel);
        
        ManifestType manifest = new ManifestType();
        manifest.setRoot(key.toString());
        return manifest;
    }    

    /**
     * Creates a symbolic link in ATM pointing to the given 
     * directory
     * @param directory to be linked
     */
    public ManifestType deposit(Path directory) throws Exception { 
        Path key = atmPath.resolve(generateKey());
        Path rel = atmPath.relativize(directory);
        
        while (true) {
            try {
                Files.createSymbolicLink(key, rel);
                break;
            } catch (FileAlreadyExistsException fae) {
                key = atmPath.resolve(generateKey());
            }
        }
        
        ManifestType manifest = new ManifestType();
        manifest.setRoot(key.toString());
        return manifest;
    }
    
    private static ATM atm;
    private Path atmPath;
    private Random random;

    private String generateKey() {
        while (true)
        {
        int number = Math.abs(random.nextInt());
        String name = String
                .format("%6s", Integer.toString(number, Character.MAX_RADIX))
                .replace(' ', '0');
        if (!name.matches("^[0-9]*$")) return name;            
        }
    }

}
