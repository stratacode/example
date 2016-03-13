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

   static {
      String quizName = (String) JOptionPane.showInputDialog
	 (null, "Pick a quiz:", "Pick a Quiz", JOptionPane.PLAIN_MESSAGE,
	  null, getAllQuizNames().toArray(), null);
      
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
