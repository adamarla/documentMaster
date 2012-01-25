package gutenberg.workers;

import gutenberg.blocs.AssignQuiz;
import gutenberg.blocs.AssignmentType;
import gutenberg.blocs.BuildQuiz;
import gutenberg.blocs.BuildQuizResponse;
import gutenberg.blocs.DocumentMasterSkeleton;
import gutenberg.blocs.EntryType;
import gutenberg.blocs.PageType;
import gutenberg.blocs.QuizType;
import gutenberg.blocs.StudentType;

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
			AssignmentType assignment = new AssignmentType();			
			QuizType quiz = new QuizType();
			quiz.setId("quiz61");
			quiz.setTeacherId("2008");
			PageType[] pages = new PageType[1];
			pages[0] = new PageType();
			pages[0].setNumber(1);
			EntryType[] questions = new EntryType[1];
			questions[0] = new EntryType();
			questions[0].setId("1");
			pages[0].setQuestion(questions);
			quiz.setPage(pages);
			StudentType[] students = new StudentType[2];
			students[0] = new StudentType();
			students[0].setId("1A");
			students[0].setName("Student Aman");
			students[1] = new StudentType();
			students[1].setId("1B");
			students[1].setName("Etudiant Azan");
			assignment.setQuiz(quiz);
			assignment.setStudents(students);
			request.setBuildQuiz(quiz);
			service = new DocumentMasterSkeleton();
			
			AssignQuiz assignQuizReq = new AssignQuiz();
			assignQuizReq.setAssignQuiz(assignment);
			service.assignQuiz(assignQuizReq);
			
			//response = service.buildQuiz(request);			
		} catch (Exception e) {
			
		}

	}

}
