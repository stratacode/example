/**
 * An Activity which lets you name a new quiz to be added, and creates
 * the quiz in the database.
 */
public class NameQuiz extends Activity implements QuizConstants {
   private static final String[] PROJECTION = new String[] { Quizes._ID };

   // The Quiz to be created
   object quiz extends Quiz {
   }

   // The main layout
   object mainView extends LinearLayout {
      orientation = VERTICAL;   
      { setPadding(0,10,0,0); }

      object quizNameInput extends LinearLayout {
	 object quizNameLabel extends CTextView {
	    text = "Name your quiz:";
	    { setPadding(5,0,5,0); }
	 }
	 object quizNameField extends CEditText {
            singleLine = true;
	    textString =: quiz.name;
            layoutParams = new LinearLayout.LayoutParams(FILL_PARENT, WRAP_CONTENT);
	 }
      }

      object makeQuizButton extends CButton {
	 text = "Make Quiz";
	 enabled := !StringUtil.isBlank(quiz.name);
	 clickCount =: gotoMakeQuiz();
	 layoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
      }
   }

   /**
    * This method is called when the Make Quiz button is pressed.  It
    * makes sure a quiz with the chosen name doesn't already exist,
    * then creates a new quiz in the database, and, finally, starts a
    * new activity to fill in the quiz data.
    */
   void gotoMakeQuiz() {
      Cursor cursor = null;
      try {
	 // Make sure a quiz with this name doesn't already exist
	 cursor = getContentResolver().query
	    (Quizes.CONTENT_URI, PROJECTION, Quizes.NAME + "='" + quiz.name + "'", null, null);
	 if ((cursor != null) && (cursor.getCount() > 0)) {
	    DialogUtil.showErrorDialog
	       (this, "Invalid Name", 
		"Quiz " + quiz.name + " already exists!  Please use another name.");
	    return;
	 }
      }
      finally {
	 // Close the cursor after we're done with it
	 if (cursor != null) 
	    cursor.close();
      }
      
      // Insert a row for this quiz into the database
      ContentValues values = ContentUtil.quizToContentValues(quiz);
      Uri quizUri = getContentResolver().insert(Quizes.CONTENT_URI, values);

      // Launch activity to edit this quiz
      Intent intent = new Intent(null, quizUri, this, QuestionList.class);
      intent.putExtra(KEY_QUIZ_NAME, quiz.name);
      startActivity(intent);

      // Finish this activity, since we typically won't go back to it,
      // and we want it off the stack
      finish();
   }
}
