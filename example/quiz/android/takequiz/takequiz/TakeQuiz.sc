TakeQuiz {
   private static final String TAG = "TakeQuiz";

   /**
    * Retrieves the Quiz corresponding to the URI contained in the
    * Intent.  The quiz data is obtained from the quiz content
    * provider, and a Quiz object is constructed using that data.
    */
   Quiz getQuizFromIntent(Intent intent) {
      Uri quizUri = intent.getData();
      Quiz quiz = ContentUtil.quizUriToQuiz(quizUri, getContentResolver());
      if (quiz == null)
	 Log.e(TAG, "No quiz found at " + quizUri);
      return quiz;
   }
}

