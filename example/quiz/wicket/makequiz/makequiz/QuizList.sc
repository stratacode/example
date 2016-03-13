/**
 * The top level WebPage which lists all the existing quiz names.
 * Each quiz can be either edited or deleted, and new quizes can be
 * created.
 */
public class QuizList extends DataManagedWebPage {
   // Navigation links and labels
   object addQuizLink extends Link {
      public void onClick() {
	 gotoPage(NameQuiz.class);
      }
   }
   
   // List of all existing quiz names
   object quizNames extends CListView<String> {
      list = dataManager.getAllQuizNames();

      scope<ListItem> object quizNameRow implements Serializable {
	 object editQuizLink extends Link {
	    object quizNameLabel extends CLabel {
	       textValue = listItemValue;
	    }
	    public void onClick() {
	       gotoPage(new QuestionList(listItemValue));
	    }
	 }
	 object deleteQuizLink extends Link {
	    // Confirm before deleting
	    object confirmDelete extends OnclickConfirmation {
	       message = "Are you sure you want to delete quiz " + listItemValue + "?";
	    }
	    public void onClick() {
	       try {
		  dataManager.deleteQuiz(listItemValue);
	       }
	       catch (EntityNotFoundException e) {
		  // Somebody must have deleted this quiz before us;
		  // do nothing.
	       }
	       catch (OptimisticLockException e) {
		  // Somebody must have deleted this quiz before us;
		  // do nothing.
	       }

	       // Create a new instance of this page, so that the
	       // deleted quiz is removed from the list.
	       gotoPage(QuizList.class);
	    }
	 }
      }
   }
}
