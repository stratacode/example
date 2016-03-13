/**
 * A WebPage which lets you name a new quiz to be added, and creates
 * the quiz in the database.
 */
public class NameQuiz extends DataManagedWebPage {
   // The Quiz to be created
   Quiz quiz = new Quiz();

   // Navigation links and labels
   object quizListLink extends Link {
      public void onClick() {
	 gotoPage(QuizList.class);
      }
   }

   // Form for specifying the quiz name
   object nameQuizForm extends CForm<NameQuiz> {
      object quizNameField extends CTextField<String> {
	 required = true;
	 fieldValue =: quiz.name;
      }
      public void onSubmit() {
	 if (!hasError())
	    gotoMakeQuiz();
      }
   }

   // The error message panel
   object feedback extends FeedbackPanel {
   }

   /**
    * This method is called when the Make Quiz button is pressed.  It
    * makes sure a quiz with the chosen name doesn't already exist,
    * then creates a new quiz in the database, and, finally, creates a
    * new QuestionList page instance to fill in the quiz data.
    */
   void gotoMakeQuiz() {
      // Just try to create the quiz; if one already exists with this
      // name, we'll get an exception.
      try {
	 dataManager.persistQuiz(quiz);
      }
      catch (EntityExistsException e) {
	 error("Quiz " + quiz.name + " already exists!  Please use another name.");
	 return;
      }
      gotoPage(new QuestionList(quiz.name));
   }
}
