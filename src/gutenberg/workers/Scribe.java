package gutenberg.workers;

import java.io.PrintWriter;

import gutenberg.blocs.AssignmentType;
import gutenberg.blocs.ManifestType;
import gutenberg.blocs.QuizType;

public class Scribe {
	
	public Scribe(String mint) {
		MINT = mint;
	}
	
	public void setVault(Vault vault) {
		this.vault = vault;
	}
	
	public ManifestType generate(QuizType quiz) {
		ManifestType manifest = null;
		
		
		return new ManifestType();
	}
	
	public ManifestType generate(AssignmentType assignment) {
		
		return new ManifestType();
	}
	
	private String MINT;
	private Vault vault;
	private PrintWriter writer;
}
