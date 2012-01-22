/**
 * DocumentMasterSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:22:40 CEST)
 */
package gutenberg.blocs;

import gutenberg.workers.Config;
import gutenberg.workers.Resource;
import gutenberg.workers.Scribe;
import gutenberg.workers.Vault;

/**
 * DocumentMasterSkeleton java skeleton for the axisService
 */
public class DocumentMasterSkeleton implements DocumentMasterSkeletonInterface {

	/**
	 * Auto generated method signature
	 * 
	 * @param viewScans0
	 * @return viewScansResponse1
	 */

	public gutenberg.blocs.ViewScansResponse viewScans(
			gutenberg.blocs.ViewScans viewScans0) {
		// TODO : fill this with the necessary business logic
		throw new java.lang.UnsupportedOperationException("Please implement "
				+ this.getClass().getName() + "#viewScans");
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param viewQuestions2
	 * @return viewQuestionsResponse3
	 */

	public gutenberg.blocs.ViewQuestionsResponse viewQuestions(
			gutenberg.blocs.ViewQuestions viewQuestions2) {
		// TODO : fill this with the necessary business logic
		throw new java.lang.UnsupportedOperationException("Please implement "
				+ this.getClass().getName() + "#viewQuestions");
	}

	/**
	 * Auto generated method signature.
	 * 
	 * @param buildQuiz
	 * @return buildQuizResponse
	 */

	public gutenberg.blocs.BuildQuizResponse buildQuiz(
			gutenberg.blocs.BuildQuiz buildQuiz) {	
		Config config = null;
		Vault vault = null;
		Scribe scribe = null;
		ResponseType response = new ResponseType();		
		try {
			config = new Config();
			vault = new Vault(config.getPath(Resource.vault));
			scribe = new Scribe(config.getPath(Resource.mint));
			scribe.setVault(vault);			
			ManifestType manifest = scribe.generate(buildQuiz.getBuildQuiz());
			response.setManifest(manifest);			
		} catch (Exception e) {
			response.setError(e.getMessage());
		}		
		BuildQuizResponse buildQuizResponse = new BuildQuizResponse();
		buildQuizResponse.setBuildQuizResponse(response);
		return buildQuizResponse;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param assignQuiz
	 * @return assignQuizResponse
	 */

	public gutenberg.blocs.AssignQuizResponse assignQuiz(
			gutenberg.blocs.AssignQuiz assignQuiz) {
		// TODO : fill this with the necessary business logic
		throw new java.lang.UnsupportedOperationException("Please implement "
				+ this.getClass().getName() + "#assignQuiz");
	}

}
