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
			gutenberg.blocs.ViewScans viewScans) {
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
			gutenberg.blocs.ViewQuestions viewQuestions) {
		// TODO : fill this with the necessary business logic
		throw new java.lang.UnsupportedOperationException("Please implement "
				+ this.getClass().getName() + "#viewQuestions");
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param buildQuiz4
	 * @return buildQuizResponse5
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
			scribe = new Scribe(config.getPath(Resource.mint), config
					.getPath(Resource.shared));
			scribe.setVault(vault);
			scribe.generate(buildQuiz.getBuildQuiz());
			response.setManifest(scribe.getManifest());
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
	 * @param assignQuiz6
	 * @return assignQuizResponse7
	 */

	public gutenberg.blocs.AssignQuizResponse assignQuiz(
			gutenberg.blocs.AssignQuiz assignQuiz) {
		// TODO : fill this with the necessary business logic
		throw new java.lang.UnsupportedOperationException("Please implement "
				+ this.getClass().getName() + "#assignQuiz");
	}

}
