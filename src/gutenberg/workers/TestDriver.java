package gutenberg.workers;

import gutenberg.blocs.BuildQuiz;
import gutenberg.blocs.BuildQuizResponse;
import gutenberg.blocs.DocumentMasterSkeleton;
import gutenberg.blocs.EntryType;
import gutenberg.blocs.PageType;
import gutenberg.blocs.QuizType;

public class TestDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BuildQuiz request = null;
		BuildQuizResponse response = null;
		DocumentMasterSkeleton service = null;
		try {
			request = new BuildQuiz();
			QuizType quiz = new QuizType();
			quiz.setId("1004");
			quiz.setTeacherId("2008");
			PageType[] pages = new PageType[1];
			pages[0] = new PageType();
			pages[0].setNumber(1);
			EntryType[] questions = new EntryType[1];
			questions[0] = new EntryType();
			questions[0].setId("1");
			pages[0].setQuestion(questions);
			quiz.setPage(pages);
			request.setBuildQuiz(quiz);
			service = new DocumentMasterSkeleton();
			response = service.buildQuiz(request);
			
		} catch (Exception e) {
			
		}

	}

}
