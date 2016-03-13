TakeQuiz extends DataManagedWebPage {
   /**
    * Retrieves the Quiz with the given name.  The Quiz is looked up
    * by name in the database via QuizDataManager.
    */
   Quiz getQuizByName(String quizName) {
      return dataManager.findQuiz(quizName);
   }
}