/**
 * The top level WebPage which lists all the available quiz names,
 * allowing the user to pick one.
 */
public class PickQuiz extends QuizWebPage {
   String quizName;

   // Form for picking a quiz to take
   object pickQuizForm extends CForm<PickQuiz> {

      // A drop down which displays all available quiz names
      object quizChoice extends CDropDownChoice<String> {
	 required = true;
	 choiceValues = getAllQuizNames();
	 currentValue =: quizName;
      }

      // When this form is submitted, go to the TakeQuiz page
      public void onSubmit() {
	 if (!hasError())
	    gotoPage(new TakeQuiz(quizName));
      }
   }

   // The error message panel
   object feedback extends FeedbackPanel {
   }

   /**
    * Returns a List of all the available quiz names.  The quiz names
    * are obtained from the static QuizData object.
    */
   List<String> getAllQuizNames() {
      return QuizData.allQuizNames;
   }
}
