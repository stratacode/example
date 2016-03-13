PickQuiz extends DataManagedWebPage {
   /**
    * Returns a List of all the available quiz names.  The quiz names
    * are obtained from the database via QuizDataManager.
    */
   List<String> getAllQuizNames() {
      return dataManager.getAllQuizNames();
   }
}
