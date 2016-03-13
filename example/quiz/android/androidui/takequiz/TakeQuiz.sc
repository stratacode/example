/**
 * The Activity which actually lets you take a particular quiz.
 */
public class TakeQuiz extends Activity implements QuizConstants {
   // State that needs to be preserved across restarts
   int questionIndex = 0;
   boolean questionAnswered = false;
   int currentScore = 0;

   // State derived from the intent and/or from the above
   Quiz quiz;
   Question currentQuestion := quiz.questions.get(questionIndex);
   int numQuestions := quiz.questions.size();

   // Set the title of the window to reflect the quiz name
   title := "Quiz: " + quiz.name;

   // Put a scroll view at the top to allow vertical scrolling
   object scrollView extends ScrollView {

      // The main layout
      object mainView extends LinearLayout {
         orientation = VERTICAL;   
	 // leave some space on the right for the scroll bar
         { setPadding(0,0,9,0); }

         // Display the current question number and score
         object questionAndScore extends RelativeLayout {
            object questionLabel extends CTextView {
               text := "Question " + (questionIndex + 1);
            }
   	    object scoreLabel extends CTextView {
               text := "Current score: " + currentScore + " of " + numQuestions;
               layoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
	       { ((RelativeLayout.LayoutParams) layoutParams).addRule(ALIGN_PARENT_RIGHT); }
            }
	    { setPadding(0,5,0,5); }
         }

	 // Put a border around the question text
         object questionText extends CTextView {
            text := currentQuestion.question;
	    backgroundResource = R.drawable.border;
         }

         // Create a group of radio buttons for the answer choices
         object answerChoiceButtons extends CRadioGroup {
            orientation = VERTICAL;   
            object answerChoice0 extends CRadioButton {
               text := currentQuestion.answerChoices[0];
               enabled := !questionAnswered;
               textColor := determineChoiceColor
		  (questionAnswered, checked, (currentQuestion.answerIndex == 0));
            }
            object answerChoice1 extends CRadioButton {
               text := currentQuestion.answerChoices[1];
     	       enabled := !questionAnswered;
	       textColor := determineChoiceColor
		  (questionAnswered, checked, (currentQuestion.answerIndex == 1));
   	    }
	    object answerChoice2 extends CRadioButton {
      	       text := currentQuestion.answerChoices[2];
	       enabled := !questionAnswered;
      	       textColor := determineChoiceColor
		  (questionAnswered, checked, (currentQuestion.answerIndex == 2));
   	    }
	    object answerChoice3 extends CRadioButton {
	       text := currentQuestion.answerChoices[3];
      	       enabled := !questionAnswered;
      	       textColor := determineChoiceColor
		  (questionAnswered, checked, (currentQuestion.answerIndex == 3));
            }
         }

         // The Submit Answer button is originally invisible; it is
      	 // displayed only after the user has selected one of the
      	 // answer choices.  Once it is pressed, it goes away again
      	 // since now questionAnswered is set to true.
         object answerButton extends CButton {
            text = "Submit Answer";
            clickCount =: gotoDisplayAnswer(answerChoiceButtons.selectedIndex);
            layoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            visibility := ((answerChoiceButtons.checkedRadioButtonId != -1) &&
	                   !questionAnswered) ? VISIBLE : GONE;
         }

      	 // Display the correct answer and answer detail
      	 object answerLabel extends CTextView {
            text := "The correct answer is: " + currentQuestion.answer;
            visibility := (questionAnswered ? VISIBLE : GONE);
	    { setPadding(0,5,0,5); }
         }

	 // Put a border around the answer detail
         object answerDetailText extends CTextView {
            text := currentQuestion.answerDetail;         
            visibility := (questionAnswered ? VISIBLE : GONE);
	    backgroundResource = R.drawable.border;
         }

	 // Add some padding before displaying the next button
      	 object nextPadding extends View {
            layoutParams = new LinearLayout.LayoutParams(FILL_PARENT, 8);
      	 }

         // The Next Question button is only displayed after the
         // current question has been answered.
         object nextButton extends CButton {
            text = "Next Question";
            clickCount =: gotoNextQuestion();
            layoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
	    visibility := (questionAnswered && (questionIndex < numQuestions-1)) ? VISIBLE : GONE;
         }

	 // When the last question is answered, we're done
         object doneButton extends CButton {
            text = "Done";
            clickCount =: gotoDone();
            layoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            visibility := (questionAnswered && (questionIndex == numQuestions-1)) ? VISIBLE : GONE;
         }
      }
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      quiz = getQuizFromIntent(getIntent());

      // If this activity is being recreated after having been killed
      // by the system, get back to the state it was in at the time.
      if (savedInstanceState != null) {
	 questionIndex = savedInstanceState.getInt(KEY_QUESTION_INDEX);
	 questionAnswered = savedInstanceState.getBoolean(KEY_QUESTION_ANSWERED);
	 currentScore = savedInstanceState.getInt(KEY_CURRENT_SCORE);
      }
   }

   @Override
   protected void onSaveInstanceState(Bundle outState) {
      // Save away the current state, so we still have it if the
      // activity needs to be killed while paused.
      outState.putInt(KEY_QUESTION_INDEX, questionIndex);
      outState.putBoolean(KEY_QUESTION_ANSWERED, questionAnswered);
      outState.putInt(KEY_CURRENT_SCORE, currentScore);
   }

   /**
    * Retrieves the Quiz with the name contained in the Intent.  The
    * quiz is looked up by name in the static QuizData object.
    */
   Quiz getQuizFromIntent(Intent intent) {
      Bundle extras = intent.getExtras();
      String quizName = extras.getString(KEY_QUIZ_NAME);
      return QuizData.getQuizByName(quizName);
   }

   /**
    * The color of each answer choice changes depending on several
    * factors.  If the question hasn't yet been answered, then all the
    * answer choices are displayed in white.  Once the question has
    * been answered, the correct answer choice is displayed in green;
    * the chosen answer, if incorrect, is displayed in red; and the
    * rest of the answers are displayed in gray.
    */
   static int determineChoiceColor(boolean questionAnswered, 
				   boolean choiceSelected, 
				   boolean choiceCorrect)
   {
      if (!questionAnswered)
	 return Color.WHITE;
      else if (choiceCorrect)
	 return Color.GREEN;
      else if (choiceSelected)
	 return Color.RED;
      else 
	 return Color.GRAY;
   }

   /**
    * This method is called when the Submit Answer button is pressed.
    * The selectedIndex argument contains the index of the answer
    * choice selected by the user.
    */
   void gotoDisplayAnswer(int selectedIndex) {
      questionAnswered = true;
      if (selectedIndex == currentQuestion.answerIndex)
	 currentScore++;
   }

   /**
    * This method is called when the Next Question button is pressed.
    * It advances the state so that the next question is displayed.
    */
   void gotoNextQuestion() {
      questionIndex++;
      questionAnswered = false;
      scrollView.mainView.answerChoiceButtons.clearCheck();
   }

   /**
    * This method is called when the Done button is pressed.  It
    * displays a dialog box with the final score, and when the user
    * dismisses it, finishes this Activity.
    */
   void gotoDone() {
      DialogUtil.showInfoDialog
	 (this, "Final Score", 
	  "Your final score is " + currentScore + " out of " + numQuestions + 
	  ".  Thanks for playing!",
	  new DialogInterface.OnClickListener() {
	     public void onClick(DialogInterface dialog, int id) {
		TakeQuiz.this.finish();
	     }
	  });		 
   }
}