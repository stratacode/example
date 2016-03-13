/**
 * A WebPage which allows you to fill in the data for a particular
 * question.  This may be a new question which has just been created,
 * or an existing question which is being modified.
 */
public class EditQuestion extends DataManagedWebPage {
   String quizName;
   int questionIndex;

   // Is this a new question being created?
   boolean newQuestion;

   // Object containing all the accumulated question data
   Question question;

   // Set the title of the page to reflect the quiz name
   object pageTitle extends CLabel {
      textValue := quizName + ": Question " + (questionIndex + 1);
   }

   // Navigation links and labels
   object quizListLink extends Link {
      public void onClick() {
	 gotoPage(QuizList.class);
      }
   }
   object questionListLink extends Link {
      object quizNameLabel extends CLabel {
	 textValue := quizName;
      }
      public void onClick() {
	 gotoPage(new QuestionList(quizName));
      }
   }
   object questionLabel extends CLabel {
      textValue := "Question " + (questionIndex + 1);
   }

   // The main form
   object editQuestionForm extends Form<EditQuestion> {
      // Don't display the form if we couldn't retrieve the question
      // to modify.
      visible := (question != null);

      // A text area for the question text
      object questionText extends CTextArea<String> {
	 required = true;
         fieldValue :=: question.question;
      }

      // Create a group of radio buttons for the answer choices.  Each
      // radio button is followed by the answer choice text to be
      // entered by the user.
      object answerChoiceButtons extends CRadioGroup<Integer> {
	 required = true;
	 currentChoiceIndex :=: question.answerIndex;

	 object answerChoices extends CListView<String> {
	    // TODO: should quietly return null without the check?
	    list := (question == null) ? null : Arrays.asList(question.answerChoices);

	    scope<ListItem> object answerChoice implements Serializable {
	       object answerChoiceRadio extends CRadio<Integer> {
		  choiceValue = listItemIndex;
	       }
	       object answerChoiceText extends CTextField<String> {
		  required = true;
		  // We must call get/setAnswerChoice here to force
		  // Openjpa to detect the property change.
		  // fieldValue :=: question.answerChoices[listItemIndex];
		  fieldValue :=: question.retAnswerChoice(listItemIndex);
	       }
	    }
	 }
      }

      // Another text area for the answer detail
      object answerDetailText extends CTextArea<String> {
	 required = true;
         fieldValue :=: question.answerDetail;
      }

      // The Save and Discard buttons
      object saveQuestionButton extends Button {
	 public void onSubmit() {
	    if (!hasError()) {
	       gotoSaveQuestion();
	    }	 
	 }
      }
      object discardChangesButton extends Button {
	 // Confirm before discarding changes
	 object confirmDiscardChanges extends OnclickConfirmation {
	    message = "All changes to this question will be discarded.";
	 }
	 // Don't perform validation when this button is pressed
	 defaultFormProcessing = false;

	 public void onSubmit() {
	    gotoDiscardChanges();
	 }	 
      }
   }

   // The error message panel
   object feedback extends FeedbackPanel {
   }

   /**
    * Creates a new EditQuestion page for a new question to be added.
    * quizName specifies which Quiz to add the question to, and
    * questionIndex indicates the new question's index.  The question
    * index is used only for display purposes; the actual index may
    * end up being different in the case where another user adds or
    * removes questions at the same time.
    */
   public EditQuestion(String quizName, int questionIndex) {
      this.quizName = quizName;
      this.questionIndex = questionIndex;
      newQuestion = true;
      question = new Question();
   }

   /**
    * Creates a new EditQuestion page for an existing question to be
    * modified.  quizName specifies which Quiz the question belongs
    * to, questionIndex indicates the question's index, and questionId
    * identifies the question in the database.  The question index is
    * used only for display purposes.
    */
   public EditQuestion(String quizName, int questionIndex, long questionId) {
      this.quizName = quizName;
      this.questionIndex = questionIndex;
      newQuestion = false;
      question = dataManager.findQuestion(questionId);
      if (question == null)
	 error("Question " + (questionIndex + 1) + " in quiz " + quizName + " does not exist.");
   }

   /**
    * This method is called when the Save Question button is pressed.
    * It saves the question data in the database.  If this is a new
    * question, it gets added to the quiz; if it's an existing
    * question, its data gets updated.
    */
   void gotoSaveQuestion() {
      if (newQuestion) {
	 try {
	    dataManager.addQuestion(quizName, question);
	 }
	 catch (EntityNotFoundException e) {
	    error("Quiz " + quizName + " does not exist.");
	    return;
	 }
      }
      else {
	 try {
	    dataManager.updateQuestion(question);
	 }
	 catch (OptimisticLockException e) {
	    error("Question " + (questionIndex + 1) + " has been modified by another user.  " + 
		  "Please discard your changes and try again.");
	    return;
	 }
      }
      gotoPage(new QuestionList(quizName));
   }

   /**
    * This method is called when the Discard Changes button is
    * pressed.  It simply creates a new instance of the QuestionList
    * page, abandoning any changes to the question data.
    */
   void gotoDiscardChanges() {
      gotoPage(new QuestionList(quizName));
   }
}
