package gutenberg.workers;

import gutenberg.blocs.ManifestType;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Vault {

	public Vault(String vault) {
		VAULT = vault;
	}

	public Vault() throws Exception {
		Properties properties = new Properties();
		properties
				.loadFromXML(new FileInputStream("/opt/gutenberg/config.xml"));
		VAULT = properties.getProperty("BANK_ROOT") + "/vault";
		SHARED = properties.getProperty("BANK_ROOT") + "/shared";
	}

	/**
	 * @param id
	 *            - question id
	 * @param filter
	 *            - type of content e.g. "tex", "gnuplot" etc.
	 * @return returns file contents for given id
	 * @throws Exception
	 */
	public String[] getContent(String id, String filter) throws Exception {
		System.out.println("[vault] : Looking inside " + VAULT + "/" + id);
		File directory = new File(VAULT + "/" + id);
		File[] files = directory.listFiles(new NameFilter(filter));
		String[] contents = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Files.copy(files[i].toPath(), baos);
			contents[i] = baos.toString();
		}
		return contents;
	}

	/**
	 * @param id
	 *            - question id
	 * @param filter
	 *            - type of content e.g. "tex", "gnuplot" etc.
	 * @return TODO
	 * @throws Exception
	 */
	public File[] getFiles(String id, String filter) throws Exception {
		File directory = new File(VAULT + "/" + id);
		return directory.listFiles(new NameFilter(filter));
	}

	/**
	 * Creates a question in the Vault
	 * 
	 * @return
	 * @throws Exception
	 */
	public ManifestType createQuestion() throws Exception {

		String dirName = Long.toString(System.currentTimeMillis(),
				Character.MAX_RADIX);
		Path questionDir = new File(VAULT).toPath().resolve(dirName);
		Files.createDirectory(questionDir);

		Path shared = new File(SHARED).toPath();
		Files.copy(shared.resolve(texFile), questionDir.resolve(texFile));
		Files.copy(shared.resolve(plotFile), questionDir.resolve(plotFile));

		gitIt(questionDir);

		ManifestType manifest = new ManifestType();
		manifest.setRoot(questionDir.toString());
		return manifest;
	}

	private int gitIt(Path questionDir) throws Exception {
		File bank = new File(VAULT).getParentFile();
		ProcessBuilder pb = new ProcessBuilder("shared/gitQuestion.sh", bank.toPath()
				.relativize(questionDir).toString());
		pb.directory(bank);
		pb.redirectErrorStream(true);

		Process git = pb.start();
		BufferedReader messages = new BufferedReader(new InputStreamReader(
				git.getInputStream()));
		String line = null;
		while ((line = messages.readLine()) != null) {
			System.out.println(line);
		}
		return git.waitFor();
	}

	public String getPath() throws Exception {
		return this.VAULT;
	}

	private String VAULT, SHARED;
	private final String texFile = "question.tex", plotFile = "figure.gnuplot";
}
