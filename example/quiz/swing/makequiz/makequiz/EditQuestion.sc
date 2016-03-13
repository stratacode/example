/**
 * A panel which allows you to fill in the data for a particular
 * question.  This may be a new question which has just been created,
 * or an existing question which is being modified.
 */
public class EditQuestion extends JPanel {
   // Used by text areas and their scroll panes
   static int textAreaHeight = 80;
   static Border textAreaBorder = BorderFactory.createCompoundBorder
      (BorderFactory.createLineBorder(Color.GRAY), BorderFactory.createEmptyBorder(5, 5, 5, 5));

   int xpad = 10, ypad = 10, gap = 10;
   int displayWidth := (int) size.width - 2*xpad;

   // State shared by all the panels
   QuizDataManager dataManager;
   MakeQuizState state;

   // Object containing all the accumulated question data
   Question question := findQuestion(state.questionId, state.newQuestionEdit);

   // Originally no question is selected.  Use the panel to display
   // some instructions to the user.
   object instructionsText extends JTextArea {
      text = "Select a quiz from the top list, and a question to edit\n" + 
	     "from the bottom list.\n\n" + 
	     "You can add a new quiz or question, or delete an existing\n" + 
	     "quiz or question, by selecting the appropriate option from\n" + 
	     "each list's context menu.";
      editable = false;
      focusable = false;
      opaque = false;
      location := SwingUtil.point(xpad, 30);
      size := preferredSize;	       
      visible := (question == null);
   }

   object questionLabel extends JLabel {
      text := "Question " + (state.questionIndex + 1);
      location := SwingUtil.point(xpad, ypad);
      size := preferredSize;   
      visible := (question != null);
   }

   // A scrollable text area for the question text
   object questionPane extends JScrollPane {
      viewportView = questionText;
      border = textAreaBorder;
      location := SwingUtil.point(xpad, questionLabel.location.y + questionLabel.size.height + ypad);
      size := SwingUtil.dimension(displayWidth, textAreaHeight);
      visible := (question != null);
      object questionText extends JTextArea {
	 text :=: question.question;
      }
   }
	 
   // Create a group of radio buttons for the answer choices.  Each
   // radio button is followed by the answer choice text to be entered
   // by the user.
   object answerChoicesLabel extends JLabel {
      text = "Answer choices (select the correct answer):";
      location := SwingUtil.point(xpad, questionPane.location.y + questionPane.size.height + ypad);
      size := preferredSize;
      visible := (question != null);
   }
   
   object answerChoice0 extends JRadioButton {
      location := SwingUtil.point(xpad, answerChoicesLabel.location.y + answerChoicesLabel.size.height + ypad);
      size := preferredSize;
      visible := (question != null);
   }
   object answerChoice0Text extends JTextField {
      // We must call get/setAnswerChoice here to force Openjpa to
      // detect the property change.  
      // text :=: question.answerChoices[0];
      text :=: question.retAnswerChoice(0);
      location := SwingUtil.point(xpad + answerChoice0.size.width + gap, answerChoice0.location.y);
      size := SwingUtil.dimension(displayWidth - answerChoice0.size.width - gap, preferredSize.height);
      visible := (question != null);
   }
   
   object answerChoice1 extends JRadioButton {
      location := SwingUtil.point(xpad, answerChoice0.location.y + answerChoice0.size.height + ypad);
      size := preferredSize;
      visible := (question != null);
   }
   object answerChoice1Text extends JTextField {
      text :=: question.retAnswerChoice(1);
      location := SwingUtil.point(xpad + answerChoice1.size.width + gap, answerChoice1.location.y);
      size := SwingUtil.dimension(displayWidth - answerChoice1.size.width - gap, preferredSize.height);
      visible := (question != null);
   }
   
   object answerChoice2 extends JRadioButton {
      location := SwingUtil.point(xpad, answerChoice1.location.y + answerChoice1.size.height + ypad);
      size := preferredSize;
      visible := (question != null);
   }
   object answerChoice2Text extends JTextField {
      text :=: question.retAnswerChoice(2);
      location := SwingUtil.point(xpad + answerChoice2.size.width + gap, answerChoice2.location.y);
      size := SwingUtil.dimension(displayWidth - answerChoice2.size.width - gap, preferredSize.height);
      visible := (question != null);
   }
   
   object answerChoice3 extends JRadioButton {
      location := SwingUtil.point(xpad, answerChoice2.location.y + answerChoice2.size.height + ypad);
      size := preferredSize;
      visible := (question != null);
   }
   object answerChoice3Text extends JTextField {
      text :=: question.retAnswerChoice(3);
      location := SwingUtil.point(xpad + answerChoice3.size.width + gap, answerChoice3.location.y);
      size := SwingUtil.dimension(displayWidth - answerChoice3.size.width - gap, preferredSize.height);
      visible := (question != null);
   }
   
   // Create a ButtonGroup for the radio buttons
   object answerChoiceButtons extends ButtonGroup {
      buttons = Arrays.asList
	 (new AbstractButton[] { answerChoice0, answerChoice1, answerChoice2, answerChoice3 });
      selectedIndex :=: question.answerIndex;
   }  	  
   
   // Another scrollable text area for the answer detail
   object answerDetailLabel extends JLabel {
      text = "Explain why your answer is correct:";
      location := SwingUtil.point(xpad, answerChoice3.location.y + answerChoice3.size.height + ypad);
      size := preferredSize;
      visible := (question != null);
   }
   object answerDetailPane extends JScrollPane {
      viewportView = answerDetailText;
      border = textAreaBorder;
      location := SwingUtil.point(xpad, answerDetailLabel.location.y + answerDetailLabel.size.height + ypad);
      size := SwingUtil.dimension(displayWidth, textAreaHeight);
      visible := (question != null);
      object answerDetailText extends JTextArea {
	 text :=: question.answerDetail;
      }
   }
   
   // The Save and Discard buttons
   object saveQuestionButton extends JButton {
      text = "Save Question";
      clickCount =: gotoSaveQuestion();
      location := SwingUtil.point(xpad, answerDetailPane.location.y + answerDetailPane.size.height + ypad);
      size := preferredSize;
      visible := (question != null);
   }
   object discardChangesButton extends JButton {
      text = "Discard Changes";
      clickCount =: gotoDiscardChanges();
      location := SwingUtil.point(saveQuestionButton.location.x + saveQuestionButton.size.width + gap, 
				  saveQuestionButton.location.y);
      size := preferredSize;
      visible := (question != null);
   }

   /**
    * Returns the Question object corresponding to the given question
    * id.  If this is a new question being added, creates and returns
    * a new Question object.  Otherwise, looks up the question in the
    * database.
    */
   Question findQuestion(long questionId, boolean newQuestionEdit) {
      // Make sure we're initialized
      if (dataManager == null) 
	 return null;

      // Whenever we switch to a different question, first make sure
      // to discard any changes made to the current question.  If we
      // don't do this, then the data manager will commit the changes
      // we've made even without us explicitly saving the current
      // question.
      if ((question != null) && (question.id > 0)) {
	 try {
	    dataManager.discardQuestionChanges(question);
	 }
	 catch (EntityNotFoundException e) {
	    // Do nothing if this question no longer exists
	 }
      }

      if ((questionId == 0) && !newQuestionEdit)
	 return null;

      if (newQuestionEdit)
	 return new Question();

      Question quest = dataManager.findQuestion(questionId);

      // If this question no longer exists, pop up a dialog with an
      // error message, then tell the question list to refresh itself
      if (quest == null) {
	 JOptionPane.showMessageDialog
	    (SwingUtilities.windowForComponent(this), 
	     "Question " + (state.questionIndex + 1) + " in quiz " + state.quizName + " does not exist.", 
	     "Invalid question", JOptionPane.WARNING_MESSAGE);

	 state.toggleQuizListRefresh();
	 return null;
      }

      return quest;
   }

   /**
    * This method is called when the Save Question button is pressed.
    * It saves the question data in the database.  If this is a new
    * question, it gets added to the quiz; if it's an existing
    * question, its data gets updated.
    */
   void gotoSaveQuestion() {
      if (StringUtil.isBlank(question.question) ||
	  StringUtil.isBlank(question.answerChoices[0]) ||
	  StringUtil.isBlank(question.answerChoices[1]) ||
	  StringUtil.isBlank(question.answerChoices[2]) ||
	  StringUtil.isBlank(question.answerChoices[3]) ||
	  StringUtil.isBlank(question.answerDetail)) {
	 JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), 
				       "Please fill in all the question and answer information.",
				       "Missing Data", JOptionPane.ERROR_MESSAGE);
	 return;
      }
      if (question.answerIndex == Question.NO_ANSWER) {
	 JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), 
				       "Please select the correct answer.", 
				       "Missing Data", JOptionPane.ERROR_MESSAGE);
	 return;
      }

      if (state.newQuestionEdit) {
	 try {
	    dataManager.addQuestion(state.quizName, question);
	 }
	 catch (EntityNotFoundException e) {	 
	    JOptionPane.showMessageDialog
	       (SwingUtilities.windowForComponent(this), 
		"Quiz " + state.quizName + " does not exist.", 
		"Invalid quiz", JOptionPane.WARNING_MESSAGE);
	    state.toggleQuizListRefresh();	    
	 }
	 state.clearNewQuestionEdit();
      }
      else {
	 try {
	    dataManager.updateQuestion(question);
	 }
	 catch (OptimisticLockException e) {
	    JOptionPane.showMessageDialog
	       (SwingUtilities.windowForComponent(this),
		"Question " + (state.questionIndex + 1) + " has been modified " + 
		"by another user.  Please discard your changes and try again.",
		"Stale Question", JOptionPane.WARNING_MESSAGE);
	    return;
	 }
      }

      // No question is now selected
      state.questionIndex = -1;
      state.questionId = 0;
   }

   /**
    * This method is called when the Discard Changes button is
    * pressed.  It discards any changes made to the selected question.
    * If the question was newly created, it is abandoned and never
    * added to the quiz.
    */
   void gotoDiscardChanges() {
      // Confirm discard
      int option = JOptionPane.showConfirmDialog
	 (SwingUtilities.windowForComponent(this),
	  state.newQuestionEdit ? "This question will not be saved." : 
	  "All changes to this question will be discarded.",
	  "Confirm Discard Changes", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

      if (option == JOptionPane.CANCEL_OPTION)
	 return;

      // If this was a new question, simply abandon the Question
      // object which we created earlier.  Otherwise, refresh it with
      // the data found in the database, abandoning our changes.
      if (state.newQuestionEdit) 
	 state.clearNewQuestionEdit();
      else {
	 try {
	    dataManager.discardQuestionChanges(question);
	 }
	 catch (EntityNotFoundException e) {
	    // Do nothing if this question no longer exists
	 }
      }

      // No question is now selected
      state.questionIndex = -1;
      state.questionId = 0;
   }
}
