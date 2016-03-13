/**
 * Implements a dialog which lets you name a new quiz to be added, and
 * creates the quiz in the database.
 */
public class NameQuiz extends WindowAdapter implements PropertyChangeListener {
   java.awt.Component parent;
   QuizDataManager dataManager;
   String quizName;
   
   object quizNameField extends JTextField {
      text :=: quizName;
   }

   object optionPane extends JOptionPane {
      message = new Object[] { "Name your quiz:", quizNameField };
      messageType = PLAIN_MESSAGE;
      optionType = OK_CANCEL_OPTION;
      { addPropertyChangeListener(NameQuiz.this); }
   }

   object quizNameDialog extends JDialog {
      title = "Name Your Quiz";
      modal = true;
      resizable = false;
      contentPane = optionPane;
      defaultCloseOperation = DO_NOTHING_ON_CLOSE;
      { addWindowListener(NameQuiz.this); }
   }

   /**
    * Shows a modal dialog which asks the user for a quiz name, and
    * makes sure that a quiz with that name does not already exist in
    * the database.  Returns a valid quiz name, or null if the user
    * closed the dialog without specifying a valid name.
    */
   public String showInputDialog() {
      quizNameDialog.pack();
      quizNameDialog.setLocationRelativeTo(SwingUtilities.windowForComponent(parent));
      quizNameField.setText(null);
      quizNameField.requestFocusInWindow();
      quizNameDialog.setVisible(true);

      // Because quizName is bound to quizNameField.text, it may
      // evaluate to an empty string when we'd like to see null
      return (StringUtil.isEmpty(quizName) ? null : quizName);
   }

   /**
    * We need to catch the case when the user closes the dialog
    * instead of pressing OK or Cancel.  Instead of directly closing
    * the window, we change the option pane's value so that a
    * PropertyChangeEvent gets sent.  This allows us to make sure
    * quizName is set to null before the dialog goes away.
    */
   public void windowClosing(WindowEvent event) {
      optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
   }

   /**
    * Validates the quiz name supplied by the user when the dialog is
    * submitted.  If the user presses Cancel or simply closes the
    * dialog, sets quizName to null.
    */
   public void propertyChange(PropertyChangeEvent event) {
      if (quizNameDialog.isVisible() && 
	  (event.getSource() == optionPane) && 
	  (event.getPropertyName().equals(JOptionPane.VALUE_PROPERTY))) {
	 Object value = optionPane.getValue();
	 
	 if (value == JOptionPane.UNINITIALIZED_VALUE) {
	    // Ignore reset
	    return;
	 }

	 // Reset the JOptionPane's value.  If we don't do this, then
	 // if the user presses the same button next time, no property
	 // change event will be fired.
	 optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

	 // OK button was pressed
	 if (value.equals(JOptionPane.OK_OPTION)) {
	    quizName = quizName.trim();

	    // Don't allow empty strings
	    if (StringUtil.isBlank(quizName)) {
	       quizNameField.selectAll();
	       JOptionPane.showMessageDialog
		  (quizNameDialog, "Please specify a quiz name.", "Try Again", JOptionPane.ERROR_MESSAGE);
	       quizNameField.requestFocusInWindow();
	    }

	    // Just try to create the quiz; if one already exists with
	    // this name, we'll get an exception.
	    else {
	       try {
		  Quiz quiz = new Quiz();
		  quiz.name = quizName;
		  dataManager.persistQuiz(quiz);
		  quizNameDialog.setVisible(false);
	       }
	       catch (EntityExistsException e) {
		  quizNameField.selectAll();
		  JOptionPane.showMessageDialog
		     (quizNameDialog, "Quiz \"" + quizName + "\" " + "already exists!\n" + 
		      "Please use another name.", "Try Again", JOptionPane.ERROR_MESSAGE);
		  quizNameField.requestFocusInWindow();
	       }
	    }
	 }

	 // Cancel button was pressed, or the dialog was closed - set
	 // quizName to null
	 else {
	    quizName = null;
	    quizNameDialog.setVisible(false);
	 }
      }
   }
}
