package gutenberg.workers;

import gutenberg.blocs.EntryType;
import gutenberg.blocs.ManifestType;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class Locker {

	public Locker(Config config) throws Exception {
		LOCKER = config.getPath(Resource.locker);
	}

	public File[] fetch(String[] scanIds) {
		Path scan = null, thumb = null;
		File[] scans = new File[scanIds.length * 2];
		Path locker = new File(LOCKER).toPath();
		for (int i = 0; i < scanIds.length; i++) {
			scan = locker.resolve(scanIds[i]);
			thumb = locker.resolve("thumb-" + scanIds[i]);
			scans[i] = scan.toFile();
			scans[i + 1] = thumb.toFile();
		}
		return scans;
	}

	public ManifestType save(File[] scans) throws Exception {
		ManifestType manifest = new ManifestType();
		manifest.setRoot(LOCKER);
		Path locker = new File(LOCKER).toPath();
		for (File scan:scans) {
			if (locker.resolve(scan.getName()).toFile().exists()) {
				if (!scan.delete()) {
					throw new Exception("Received scan " 
							+ scan.getName() + " could not be deleted");
				}
				continue;
			}
						
			if (convert(scan.getPath(),
					locker.resolve(scan.getName()).toString(), SCAN_SIZE) == 0 &&
				convert(scan.getPath(), 
					locker.resolve("thumb-" + scan.getName()).toString(), THUMB_SIZE) == 0) {
				if (!scan.delete()) {
					throw new Exception("Received scan " 
							+ scan.getName() + " could not be deleted");
				}
				
			} else {
				throw new Exception("Error resizing scan "
						+ scan.getName());
			}
			
			EntryType image = new EntryType();
			image.setId(scan.getName());
			manifest.addImage(image);
		}
		return manifest;
	}

	private String LOCKER;
	
	private int convert(String src, String target, String size)
			throws Exception {
		ProcessBuilder pb = 
				new ProcessBuilder("convert", src, "-resize", size, target);
		System.out.println("[receiveScan]: convert " + src + " -resize " + size +
				" " + target);

		pb.directory(new File(LOCKER));
		pb.redirectErrorStream(true);

		Process process = pb.start();
		BufferedReader messages = new BufferedReader(new InputStreamReader(
				process.getInputStream()));
		String line = null;

		while ((line = messages.readLine()) != null) {
			System.out.println(line);
		}

		return process.waitFor();
	}

	private final String SCAN_SIZE = "600x800";
	private final String THUMB_SIZE = "120x120";
}
