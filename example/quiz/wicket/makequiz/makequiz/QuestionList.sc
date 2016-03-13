/**
 * A WebPage which lists all the existing questions for a given quiz.
 * Each question can be either edited or deleted, and new questions
 * can be created.
 */
public class QuestionList extends DataManagedWebPage {
   // Truncate question text in list display
   static int maxQuestionLength = 75;

   Quiz quiz;

   // Set the title of the page to reflect the quiz name
   object pageTitle extends CLabel {
      textValue := "Make a Quiz: " + quiz.name;
   }

   // Navigation links and labels
   object quizListLink extends Link {
      public void onClick() {
	 gotoPage(QuizList.class);
      }
   }
   object quizNameLabel extends CLabel {
      textValue := quiz.name;
   }
   object addQuestionLink extends Link {
      public void onClick() {
	 gotoPage(new EditQuestion(quiz.name, quiz.questions.size()));
      }
   }
   
   // List of all existing questions
   object questions extends CListView<Question> {
      list := quiz.questions;

      scope<ListItem> object questionRow implements Serializable {
	 object editQuestionLink extends Link {
	    object questionLabel extends CLabel {
	       textValue := StringUtil.ellipsis(listItemValue.question, maxQuestionLength, true);
	    }
	    public void onClick() {
	       gotoPage(new EditQuestion(quiz.name, listItemIndex, listItemValue.id));
	    }
	 }
	 object deleteQuestionLink extends Link {
	    // Confirm before deleting
	    object confirmDelete extends OnclickConfirmation {
	       message = "Are you sure you want to delete question " + (listItemIndex + 1) + "?";
	    }
	    public void onClick() {
	       try {
		  dataManager.deleteQuestion(quiz.name, listItemValue.id);
	       }
	       catch (EntityNotFoundException e) {
		  // Somebody must have deleted this question before
		  // us; do nothing.
	       }
	       catch (OptimisticLockException e) {
		  // Somebody must have deleted this question before
		  // us; do nothing.
	       }
	    }
	 }
      }
   }

   // The error message panel
   object feedback extends FeedbackPanel {
   }

   /**
    * Creates a new QuestionList page for a Quiz with the given name.
    */
   public QuestionList(String quizName) {
      quiz = dataManager.findQuiz(quizName);
      if (quiz == null)
	 error("Quiz " + quizName + " does not exist.");
   }
}
