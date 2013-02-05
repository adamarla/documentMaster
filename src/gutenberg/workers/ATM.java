package gutenberg.workers;

import gutenberg.blocs.ManifestType;

import java.io.File;
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
        atmPath = new File(config.getPath(Resource.atm)).toPath();
        random = new Random();
    }

    /**
     * Creates a symlink in ATM pointing to the given directory
     * @param directory to be linked
     */
    public ManifestType deposit(Path directory) throws Exception {
        Path key = atmPath.resolve(generateKey());
        Path rel = atmPath.relativize(directory);
        
        while (true) {
            try {
                Files.createSymbolicLink(key, rel);
                break;
            } catch (FileAlreadyExistsException fae) {}
        }
        
        ManifestType manifest = new ManifestType();
        manifest.setRoot(key.toString());
        return manifest;
    }

    private static ATM atm;
    private Path       atmPath;
    private Random     random;

    private String generateKey() {
        int name = Math.abs(random.nextInt());
        return String
                .format("%6s", Integer.toString(name, Character.MAX_RADIX))
                .replace(' ', '0');
    }

}
