package gutenberg.tests;

import java.io.File;

import junit.framework.Assert;

import gutenberg.workers.Config;
import gutenberg.workers.Locker;
import gutenberg.workers.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LockerTest {

	@Before
	public void setUp() throws Exception {
		
		config = new Config();
		locker = new Locker(config);
		scan = new File(config.getPath(Resource.locker) + 
				"/123-23-2-32.jpg");
		scan.createNewFile();
		thumb = new File(config.getPath(Resource.locker) + 
				"/thumb-123-23-2-32.jpg");
		thumb.createNewFile();
		
		inScan = new File(config.getPath(Resource.staging) + "/123-33-32-23.jpg");
		inScan.createNewFile();
		
	}

	@After
	public void tearDown() throws Exception {
		
		scan.delete();
		thumb.delete();
		inScan.delete();
	}

	@Test
	public void testFetch() {
		File[] f = null;
		try {
			String[] s = new String[1];
			s[0] = "123-23-2-32";
			f = locker.fetch(s);
			Assert.assertNotNull(f);
			f[0].delete();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testSave() {
		try {
			config = new Config();
			locker = new Locker(config);		
			File[] scans = new File[1];
			scans[0] = inScan;
			locker.save(scans);
			File savedScan = new File(config.getPath(Resource.locker) + 
					"/123-33-32-23.jpg");
			File savedThumb = new File(config.getPath(Resource.locker) + 
					"/thumb-123-33-32-23.jpg");
			Assert.assertTrue(savedScan.exists());
			Assert.assertTrue(savedThumb.exists());
			savedScan.delete();
			savedThumb.delete();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		
	}
	
	Locker locker;
	Config config;	
	File scan, thumb, inScan;
}
