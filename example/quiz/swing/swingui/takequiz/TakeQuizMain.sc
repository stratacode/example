/**
 * The top level object which gets executed as part of the application
 * initialization.  Pops up a dialog with all the available quiz
 * names, allowing the user to pick one; then proceeds to display the
 * main AppFrame.
 */
@MainInit
public object TakeQuizMain {
   static Quiz quiz;

   @MainInit
   static object takeQuiz extends TakeQuiz {
      quiz := TakeQuizMain.quiz;
      insertLinebreaksInText = true;
   }

   final static String dialogName = "Pick a Quiz";

   static {
      // First priority is the main arg - when we are created from the main, there's no
      // guarantee we can get the dialog answer added from the command interface before we 
      // hit this point in the code so using the programArgs. 
      String quizName = (String) sc.util.DialogManager.getDialogAnswer(dialogName);
      if (quizName == null)
         quizName = Main.programArgs.length > 0 ? Main.programArgs[0] : null;
      
      if (quizName == null) {
         quizName = (String) JOptionPane.showInputDialog
            (null, "Pick a quiz:", dialogName, JOptionPane.PLAIN_MESSAGE,
             null, getAllQuizNames().toArray(), null);
      }
      
      // If a quiz name wasn't picked (e.g., because the user canceled
      // the dialog), exit
      if (quizName == null) {
	 shutdown();
      }

      quiz = getQuizByName(quizName);
      if (quiz == null) {
         System.out.println("[quiz] Quiz " + quizName + " does not exist.");
         shutdown();
      }
   }

   /**
    * Returns a List of all the available quiz names.  The quiz names
    * are obtained from the static QuizData object.
    */
   static List<String> getAllQuizNames() {
      return QuizData.allQuizNames;
   }

   /**
    * Retrieves the Quiz with the given name.  The Quiz is looked up
    * by name in the static QuizData object.
    */
   static Quiz getQuizByName(String quizName) {
      return QuizData.getQuizByName(quizName);
   }

   /**
    * Terminates the application.
    */
   static void shutdown() {
      System.exit(0);
   }
}
