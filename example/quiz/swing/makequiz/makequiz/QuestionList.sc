/**
 * A panel which lists all the existing questions for a given quiz.
 * Each individual question can be selected for editing.  New
 * questions can be added, and existing questions can be deleted, by
 * means of a context menu.
 */
public class QuestionList extends JPanel {
   // A border around the list of questions
   static Border questionListBorder = BorderFactory.createCompoundBorder
      (BorderFactory.createEmptyBorder(0, 5, 5, 5), BorderFactory.createTitledBorder("Questions"));

   border = questionListBorder;
   Insets panelInsets := insets;

   // State shared by all the panels
   QuizDataManager dataManager;
   MakeQuizState state;

   // Get the quiz object for the given quiz name
   Quiz quiz := findQuiz(state.quizName);

   // A popup menu with menu items to add a new question and delete an
   // existing question
   object questionPopupMenu extends JPopupMenu implements ActionListener {
      object addQuestionMenuItem extends JMenuItem {
	 text = "Add question";
	 visible := (state.quizName != null);
	 { addActionListener(questionPopupMenu); }
      }
      object deleteQuestionMenuItem extends JMenuItem {
	 text := "Delete question " + (state.questionIndex + 1);
	 visible := ((state.questionIndex != -1) && !state.newQuestionEdit);
	 { addActionListener(questionPopupMenu); }
      }
      
      void actionPerformed(ActionEvent event) {
	 JMenuItem menuItem = (JMenuItem) event.source;
	 if (menuItem == addQuestionMenuItem)
	    gotoAddQuestion();
	 else if (menuItem == deleteQuestionMenuItem) 
	    gotoDeleteQuestion();
      }
   }

   // Listens for mouse events and brings up the popup menu when
   // necessary
   object popupListener extends PopupMenuListener {
      popupMenu = questionPopupMenu;
   }

   // A scroll pane with a list of all questions for this quiz
   object questionListPane extends JScrollPane {
      location := SwingUtil.point(panelInsets.left, panelInsets.top);
      size := SwingUtil.subInsets(QuestionList.this.size, panelInsets);
      viewportView = questionTextList;

      object questionTextList extends JList {
	 listItems := getAllQuestions(quiz.questions, state.newQuestionEdit);
	 selectionMode = ListSelectionModel.SINGLE_SELECTION;
	 selectedIndex :=: state.questionIndex;
	 selectedValue =: updateQuestionId((Question) selectedValue);
	 { addMouseListener(popupListener); }
      }
   }

   /**
    * Returns the Quiz object corresponding to the given quiz name.
    */
   Quiz findQuiz(String quizName) {
      // Make sure we're initialized
      if ((dataManager == null) || (quizName == null))
	 return null;

      // Whenever we switch to a different quiz, first make sure to
      // discard any changes made to the current quiz.  If we don't do
      // this, then the data manager will commit the changes we've
      // made even without us explicitly saving the current question.
      if (quiz != null) {
	 try {
	    dataManager.discardQuizChanges(quiz);
	 }
	 catch (EntityNotFoundException e) {
	    // Do nothing if this quiz no longer exists
	 }
      }

      Quiz qz = dataManager.findQuiz(quizName);

      // If this quiz no longer exists, pop up a dialog with an error
      // message, then tell the quiz list to refresh itself
      if (qz == null) {
	 JOptionPane.showMessageDialog
	    (SwingUtilities.windowForComponent(this), "Quiz " + quizName + " does not exist.", 
	     "Invalid quiz", JOptionPane.WARNING_MESSAGE);

	 state.toggleQuizListRefresh();
	 return null;
      }

      return qz;
   }

   /**
    * Returns the list of questions to display in the list.  If we're
    * currently editing a new question, we tack it on to the end of
    * the list.
    */
   List<Question> getAllQuestions(List<Question> questions, boolean newQuestionEdit) {
      if (!newQuestionEdit)
	 return questions;

      List allQuestions = new ArrayList<Question>(questions);
      allQuestions.add(new Question());
      return allQuestions;
   }

   int savedQuestionIndex = -1;

   /**
    * Updates the current question id based on the selected value.
    */
   void updateQuestionId(Question selectedValue) {
      if (selectedValue != null) {
	 state.questionId = selectedValue.id;

	 // In case we are navigating away from a newly created
	 // question, make sure to set newQuestionEdit to false
	 if ((state.questionId != 0) && state.newQuestionEdit) {

	    // Since clearing newQuestionEdit resets the list's items,
	    // we lose our list selection in the process.  So save the
	    // current question index, and reset it later.
	    savedQuestionIndex = state.questionIndex;
	    state.clearNewQuestionEdit();
	 }
      }

      // Recover the question index saved above
      else if (savedQuestionIndex != -1) {
	 state.questionIndex = savedQuestionIndex;
	 savedQuestionIndex = -1;
      }
      
      // Otherwise nothing is, or should be, selected
      else {
	 state.questionId = 0;
      }
   }

   /**
    * Adds a new question to the current quiz, by updating the state
    * to indicate that a new question should be created.  The actual
    * work of creating the question, filling it in with data, and
    * saving it in the database is performed in the EditQuestion
    * class.
    **/
   void gotoAddQuestion() {
      state.newQuestionEdit = true;
      state.questionIndex = quiz != null && quiz.questions != null ? quiz.questions.size() : 0;
   }

   /**
    * Deletes the currently selected question from the database.
    **/
   void gotoDeleteQuestion() {
      // Confirm delete
      int option = JOptionPane.showConfirmDialog
	 (SwingUtilities.windowForComponent(this), 
	  "Are you sure you want to delete question " + (state.questionIndex + 1) + "?", 
	  "Confirm Delete", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

      if (option == JOptionPane.CANCEL_OPTION)
	 return;

      try {
	 dataManager.deleteQuestion(state.quizName, state.questionId);
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
