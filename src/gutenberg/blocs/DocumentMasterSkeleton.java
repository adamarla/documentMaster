/**
 * DocumentMasterSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:22:40 CEST)
 */
package gutenberg.blocs;

import gutenberg.workers.ATM;
import gutenberg.workers.Config;
import gutenberg.workers.Locker;
import gutenberg.workers.Resource;
import gutenberg.workers.Scribe;
import gutenberg.workers.Vault;

import java.io.File;

/**
 * DocumentMasterSkeleton java skeleton for the axisService
 */
public class DocumentMasterSkeleton implements DocumentMasterSkeletonInterface {

	/**
	 * Auto generated method signature
	 * 
	 * @param annotateScan6
	 * @return annotateScanResponse7
	 */

	public gutenberg.blocs.AnnotateScanResponse annotateScan(
			gutenberg.blocs.AnnotateScan annotateScan) {
		Locker locker = null;
		Config config = null;
		ResponseType response = new ResponseType();
		try {
			config = new Config();
			locker = new Locker(config);
			String scanId = annotateScan.getAnnotateScan().getScanId();
			PointType[] points = annotateScan.getAnnotateScan().getCoordinates();
			response.setManifest(locker.annotate(scanId, points));
		} catch (Exception e) {
			e.printStackTrace();
			response.setError(e.getMessage());
		}
		AnnotateScanResponse annotateScanResponse = new AnnotateScanResponse();
		annotateScanResponse.setAnnotateScanResponse(response);
		return annotateScanResponse;

	}

	/**
	 * Auto generated method signature
	 * 
	 * @param viewScans0
	 * @return viewScansResponse1
	 */

	public gutenberg.blocs.ViewScansResponse viewScans(
			gutenberg.blocs.ViewScans viewScans) {
		ATM atm = null;
		Locker locker = null;
		Config config = null;
		ResponseType response = new ResponseType();
		try {
			EntryType[] scans = viewScans.getViewScans().getEntry();
			String[] scanIds = new String[scans.length];
			for (int i = 0; i < scans.length; i++) {
				scanIds[i] = scans[i].getId();
			}

			config = new Config();
			atm = new ATM(config);
			locker = new Locker(config);
			File[] scanFiles = locker.fetch(scanIds);
			response.setManifest(atm.deposit(scanFiles));

		} catch (Exception e) {
			e.printStackTrace();
			response.setError(e.getMessage());
		}
		ViewScansResponse viewScansResponse = new ViewScansResponse();
		viewScansResponse.setViewScansResponse(response);
		return viewScansResponse;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param viewQuestions
	 * @return viewQuestionsResponse
	 */
	public gutenberg.blocs.ViewQuestionsResponse viewQuestions(
			gutenberg.blocs.ViewQuestions viewQuestions) {
		ATM atm = null;
		Vault vault = null;
		Config config = null;
		ResponseType response = new ResponseType();
		try {
			EntryType[] questions = viewQuestions.getViewQuestions().getEntry();
			String[] questionIds = new String[questions.length];
			for (int i = 0; i < questionIds.length; i++) {
				questionIds[i] = questions[i].getId();
			}

			config = new Config();
			atm = new ATM(config);
			vault = new Vault(config);
			File[] questionFiles = vault.getFiles(questionIds, ".jpg");
			response.setManifest(atm.deposit(questionFiles));

		} catch (Exception e) {
			e.printStackTrace();
			response.setError(e.getMessage());
		}
		ViewQuestionsResponse viewQuestionsResponse = new ViewQuestionsResponse();
		viewQuestionsResponse.setViewQuestionsResponse(response);
		return viewQuestionsResponse;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param createQuestion
	 * @return createQuestionResponse
	 */
	public gutenberg.blocs.CreateQuestionResponse createQuestion(
			gutenberg.blocs.CreateQuestion createQuestion) {
		Config config = null;
		ResponseType response = new ResponseType();
		try {
			String quizMasterId = createQuestion.getCreateQuestion();
			config = new Config();
			Vault vault = new Vault(config);
			response.setManifest(vault.createQuestion(quizMasterId));
		} catch (Exception e) {
			e.printStackTrace();
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
		Config config = null;
		Scribe scribe = null;
		ResponseType response = new ResponseType();
		try {
			config = new Config();
			scribe = new Scribe(config);
			QuizType quiz = buildQuiz.getBuildQuiz();
			response.setManifest(scribe.generate(quiz));
		} catch (Exception e) {
			e.printStackTrace();
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
		Config config = null;
		ResponseType response = new ResponseType();
		try {
			config = new Config();
			Scribe scribe = new Scribe(config);
			AssignmentType assignment = assignQuiz.getAssignQuiz();
			response.setManifest(scribe.generate(assignment));
		} catch (Exception e) {
			e.printStackTrace();
			response.setError(e.getMessage());
		}
		AssignQuizResponse assignQuizResponse = new AssignQuizResponse();
		assignQuizResponse.setAssignQuizResponse(response);
		return assignQuizResponse;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param receiveScans8
	 * @return receiveScansResponse9
	 */

	public gutenberg.blocs.ReceiveScansResponse receiveScans(
			gutenberg.blocs.ReceiveScans receiveScans) {
		Locker locker = null;
		Config config = null;
		ResponseType response = new ResponseType();
		try {
			config = new Config();
			locker = new Locker(config);
			File staging = new File(config.getPath(Resource.staging));
			File[] listFiles = staging.listFiles();
			response.setManifest(locker.save(listFiles));
		} catch (Exception e) {
			e.printStackTrace();
			response.setError(e.getMessage());
		}
		ReceiveScansResponse receiveScansResponse = new ReceiveScansResponse();
		receiveScansResponse.setReceiveScansResponse(response);
		return receiveScansResponse;
	}
}
