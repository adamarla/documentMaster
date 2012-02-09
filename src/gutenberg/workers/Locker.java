package gutenberg.workers;

import gutenberg.blocs.EntryType;
import gutenberg.blocs.ManifestType;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
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
			scan = locker.resolve(scanIds[i] + ".jpg");
			thumb = locker.resolve("thumb-" + scanIds[i] + ".jpg");
			scans[i] = scan.toFile();
			scans[i + 1] = thumb.toFile();
		}
		return scans;
	}

	public ManifestType save(File[] scans) throws Exception {
		ManifestType manifest = new ManifestType();
		manifest.setRoot(LOCKER);
		Path locker = new File(LOCKER).toPath();
		Path target = null, thumbnail = null;
		int retScan = 0, retThumb = 0;
		for (int i = 0; i < scans.length; i++) {
			target = locker.resolve(scans[i].getName());
			thumbnail = locker.resolve("thumb-" + scans[i].getName());
			if (target.toFile().exists()) {
				continue;
			}			
			retScan = convert(scans[i].getPath(), target.toString(), SCAN_SIZE);
			retThumb = convert(scans[i].getPath(), thumbnail.toString(), THUMB_SIZE);
			if (retScan == 0 && retThumb == 0) {
				Files.delete(scans[i].toPath());
			} else {
				throw new Exception("Image conversion failed, see cataline.out");
			}
			
			EntryType image = new EntryType();
			image.setId(scans[i].getName() + ".jpg");
			manifest.addImage(image);
			EntryType thumb = new EntryType();
			thumb.setId("thumb-" + scans[i].getName() + ".jpg");
			manifest.addImage(thumb);			
		}
		return manifest;
	}

	private String LOCKER;
	
	private int convert(String src, String target, String size)
			throws Exception {
		ProcessBuilder pb = 
				new ProcessBuilder("convert", src, "-resize", size, target);
		System.out.println("[recieveScan]: convert " + src + " -resize " + size +
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

	private final String SCAN_SIZE = "800x600";
	private final String THUMB_SIZE = "200x200";
	
}
