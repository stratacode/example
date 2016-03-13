TakeQuizMain {
   takeQuiz {
      // JJV: set this to true cause I can't see where those linebreaks are being inserted
      insertLinebreaksInText = true;
   }

   static object dataManager extends QuizDataManager {
   }  

   /**
    * Returns a List of all the available quiz names.  The quiz names
    * are obtained from the database via QuizDataManager.
    */
   static List<String> getAllQuizNames() {
      return dataManager.getAllQuizNames();
   }

   /**
    * Retrieves the Quiz with the given name.  The Quiz is looked up
    * by name in the database via QuizDataManager.
    */
   static Quiz getQuizByName(String quizName) {
      return dataManager.findQuiz(quizName);
   }

   /**
    * Terminates the application.  Shuts down the data manager
    * services before exiting.
    */
   static void shutdown() {
      dataManager.shutdown();
      super.shutdown();
   }
}
