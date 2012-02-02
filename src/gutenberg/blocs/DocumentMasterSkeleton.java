/**
 * DocumentMasterSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:22:40 CEST)
 */
package gutenberg.blocs;

import java.io.FileInputStream;
import java.util.Properties;

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
	 * @param createQuestion4
	 * @return createQuestionResponse5
	 */

	public gutenberg.blocs.CreateQuestionResponse createQuestion(
			gutenberg.blocs.CreateQuestion createQuestion) {
		ResponseType response = new ResponseType();
		try {
			Vault vault = new Vault();
			response.setManifest(vault.createQuestion());
		} catch (Exception e) {
			response.setError(e.getMessage());
		}
		CreateQuestionResponse createQuestionResponse = new CreateQuestionResponse();
		createQuestionResponse.setCreateQuestionResponse(response);
		return createQuestionResponse;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param buildQuiz6
	 * @return buildQuizResponse7
	 */

	public gutenberg.blocs.BuildQuizResponse buildQuiz(
			gutenberg.blocs.BuildQuiz buildQuiz) {
		ResponseType response = new ResponseType();
		try {
			Scribe scribe = new Scribe();
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
	 * @param assignQuiz8
	 * @return assignQuizResponse9
	 */

	public gutenberg.blocs.AssignQuizResponse assignQuiz(
			gutenberg.blocs.AssignQuiz assignQuiz) {
		ResponseType response = new ResponseType();
		try {
			Scribe scribe = new Scribe();
			scribe.generate(assignQuiz.getAssignQuiz());
			response.setManifest(scribe.getManifest());
		} catch (Exception e) {
			response.setError(e.getMessage());
		}
		AssignQuizResponse assignQuizResponse = new AssignQuizResponse();
		assignQuizResponse.setAssignQuizResponse(response);
		return assignQuizResponse;
	}

}
