/**
 * An Activity which allows you to fill in the data for a particular
 * question.  This may be a new question which has just been created,
 * or an existing question which is being modified.
 */
public class EditQuestion extends Activity implements QuizConstants {
   private static final String TAG = "EditQuestion";

   // Is this a new question being created?
   boolean newQuestion;

   // Object containing all the accumulated question data
   Question question;

   // Put a scroll view at the top to allow vertical scrolling
   object scrollView extends ScrollView {

      // The main layout
      object mainView extends LinearLayout {
	 orientation = VERTICAL;   
	 // leave some space on the right for the scroll bar
         { setPadding(0,5,9,0); }
	 
	 // A text area for the question text
	 object questionText extends CEditText {
	    textString :=: question.question;
	    // Without this, the cursor starts out at the end of the
	    // first line of text; move it to the end of the last line
	    { setSelection(getText().length()); }
	 }
	 
	 // Create a group of radio buttons for the answer choices.
	 // Each radio button is followed by the answer choice text to
	 // be entered by the user.
	 object answerChoicesLabel extends CTextView {
	    text = "Answer choices (select the correct answer):";
	    { setPadding(0,5,0,0); }
	 }

	 // Radio buttons must be direct children of a radio group, so
	 // we have to specify the radio buttons and the associated
	 // text inputs via two separate layouts.
	 object answerChoices extends LinearLayout {
	    orientation = HORIZONTAL;
	    
	    object answerChoiceButtons extends CRadioGroup {
	       orientation = VERTICAL;
	       selectedIndex :=: question.answerIndex;

	       object answerChoice0Radio extends CRadioButton { }
	       object answerChoice1Radio extends CRadioButton { }
	       object answerChoice2Radio extends CRadioButton { }
	       object answerChoice3Radio extends CRadioButton { }
	    }

	    object answerChoiceTexts extends LinearLayout {
	       orientation = VERTICAL;	    
	       layoutParams = new LinearLayout.LayoutParams(FILL_PARENT, WRAP_CONTENT);

	       object answerChoice0Text extends CEditText {
		  textString :=: question.answerChoices[0];
		  layoutParams = new LinearLayout.LayoutParams(FILL_PARENT, WRAP_CONTENT);
		  { setSelection(getText().length()); }
	       }
	       object answerChoice1Text extends CEditText {
		  textString :=: question.answerChoices[1];
		  layoutParams = new LinearLayout.LayoutParams(FILL_PARENT, WRAP_CONTENT);
		  { setSelection(getText().length()); }
	       }
	       object answerChoice2Text extends CEditText {
		  textString :=: question.answerChoices[2];
		  layoutParams = new LinearLayout.LayoutParams(FILL_PARENT, WRAP_CONTENT);
		  { setSelection(getText().length()); }
	       }
	       object answerChoice3Text extends CEditText {
		  textString :=: question.answerChoices[3];
		  layoutParams = new LinearLayout.LayoutParams(FILL_PARENT, WRAP_CONTENT);
		  { setSelection(getText().length()); }
	       }
	    }
	 }

	 // Another text area for the answer detail
	 object answerDetailLabel extends CTextView {
	    text = "Explain why your answer is correct:";
	    { setPadding(0,5,0,0); }
	 }
	 object answerDetailText extends CEditText {
	    textString :=: question.answerDetail;
	    { setSelection(getText().length()); }
	 }

	 // Add some padding before displaying the next button
	 object nextPadding extends View {
	    layoutParams = new LinearLayout.LayoutParams(FILL_PARENT, 5);
	 }

	 // The Save and Discard buttons
	 object saveAndDiscardButtons extends LinearLayout {
	    object saveQuestionButton extends CButton {
	       text = "Save Question";
	       clickCount =: gotoSaveQuestion();
	       layoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
	    }

	    object discardChangesButton extends CButton {
	       text = "Discard Changes";
	       clickCount =: gotoDiscardChanges();
	       layoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
	    }
	 }
      }
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // Set the title of the window to reflect the question index
      Bundle extras = intent.getExtras();
      int questionIndex = extras.getInt(KEY_QUESTION_INDEX);
      setTitle("Question " + (questionIndex + 1));      

      // Determine whether this is a new question being created, or an
      // existing question being edited
      String action = getIntent().getAction();
      if (action.equals(Intent.ACTION_INSERT))
	 newQuestion = true;
      else if (action.equals(Intent.ACTION_EDIT))
	 newQuestion = false;
      else {
	 Log.e(TAG, "Invalid action " + action);
	 finish();
	 return;
      }

      // If this activity is being recreated after having been killed
      // by the system, we get the Question object from our saved
      // state.
      if (savedInstanceState != null)
	 question = (Question) savedInstanceState.getSerializable(KEY_QUESTION);

      // If we're just starting out, and we're inserting a new
      // question, simply create a new Question object.
      else if (newQuestion)
	 question = new Question();

      // Otherwise, get the question data from the database, and use
      // that to generate the Question object.
      else {
	 Uri questionUri = getIntent().getData();
	 question = ContentUtil.questionUriToQuestion(questionUri, getContentResolver());
	 if (question == null) {
	    Log.e(TAG, "No question found at " + questionUri);
	    finish();
	 } 
      }
   }

   @Override
   protected void onSaveInstanceState(Bundle outState) {
      // Save away the Question object, so we still have it if the
      // activity needs to be killed while paused.
      outState.putSerializable(KEY_QUESTION, question);
   }

   /**
    * This method is called when the Save Question button is pressed.
    * It performs validation to make sure all the necessary data has
    * been supplied, then saves the question data in the database.
    */
   void gotoSaveQuestion() {
      if (StringUtil.isBlank(question.question) ||
	  StringUtil.isBlank(question.answerChoices[0]) ||
	  StringUtil.isBlank(question.answerChoices[1]) ||
	  StringUtil.isBlank(question.answerChoices[2]) ||
	  StringUtil.isBlank(question.answerChoices[3]) ||
	  StringUtil.isBlank(question.answerDetail)) {
	 DialogUtil.showErrorDialog(this, "Missing Data", 
				    "Please fill in all the question and answer information.");
	 return;
      }
      if (question.answerIndex == Question.NO_ANSWER) {
	 DialogUtil.showErrorDialog(this, "Missing Data", 
				    "Please select the correct answer.");
	 return;
      }
      
      // Either insert or update the question data, depending on
      // whether this is a new or an existing question.
      ContentValues values = ContentUtil.questionToContentValues(question);
      if (newQuestion) {
	 Uri questionsUri = getIntent().getData();
	 if (getContentResolver().insert(questionsUri, values) == null) 
	    Log.e(TAG, "Failed to insert new question into " + questionsUri);
      } else {
	 Uri questionUri = getIntent().getData();
	 if (getContentResolver().update(questionUri, values, null, null) <= 0)
	    Log.e(TAG, "Failed to update question " + questionUri);
      }

      finish();
   }

   /**
    * This method is called when the Discard Changes button is
    * pressed.  It simply terminates this activity, abandoning any
    * changes to the question data.
    */
   void gotoDiscardChanges() {
      DialogUtil.showConfirmDialog
	 (this, "Discard Changes", "All changes to this question will be discarded.", 
	  new DialogInterface.OnClickListener() {
	     public void onClick(DialogInterface dialog, int id) {
		finish();
	     }
	  }, 
	  null);
   }
}
