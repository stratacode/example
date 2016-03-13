/**
 * Utility methods for reading and writing quiz content to and from
 * the QuizProvider.
 */
public class ContentUtil {
   /**
    * Returns a ContentValues object containing values for the given
    * Quiz.  This method operates only on the basic Quiz data itself,
    * and not on its list of Questions.
    */
   public static ContentValues quizToContentValues(Quiz quiz) {
      ContentValues values = new ContentValues();
      values.put(Quizes.NAME, quiz.name);
      return values;
   }

   /**
    * Returns a ContentValues object containing values for the given
    * Question.
    * 
    * @throw ContentException if there was a problem serializing
    * Question data
    */
   public static ContentValues questionToContentValues(Question question) {
      try {
	 ContentValues values = new ContentValues();
	 values.put(Questions.QUESTION, question.question);
	 values.put(Questions.ANSWER_CHOICES, StringUtil.serializeToBytes(question.answerChoices));
	 values.put(Questions.ANSWER_INDEX, question.answerIndex);
	 values.put(Questions.ANSWER_DETAIL, question.answerDetail);
	 return values;
      }
      catch (IOException e) {
	 throw new ContentException("Error serializing question's answer choices " + 
				    Arrays.toString(question.answerChoices), e);
      }
   }

   /**
    * Returns a Quiz object with the data found at the given cursor's
    * current position.  This method does not retrieve the associated
    * Question data.  It returns a Quiz object with an empty list of
    * Questions.
    */
   public static Quiz cursorRowToQuiz(Cursor cursor) {
      Quiz quiz = new Quiz();
      quiz.name = cursor.getString(cursor.getColumnIndex(Quizes.NAME));
      return quiz;
   }

   /**
    * Returns a Question object with the data found at the given
    * cursor's current position.  
    *
    * @throw ContentException if there was a problem deserializing
    * Question data
    */
   public static Question cursorRowToQuestion(Cursor cursor) {
      try {
	 Question question = new Question();
	 question.question = cursor.getString(cursor.getColumnIndex(Questions.QUESTION));
	 question.answerChoices = StringUtil.deserializeToStringArray
	    (cursor.getBlob(cursor.getColumnIndex(Questions.ANSWER_CHOICES)));
	 question.answerIndex = cursor.getInt(cursor.getColumnIndex(Questions.ANSWER_INDEX));
	 question.answerDetail = cursor.getString(cursor.getColumnIndex(Questions.ANSWER_DETAIL));
	 return question;
      }
      catch (IOException e) {
	 throw new ContentException("Error deserializing answer choices for question " + 
				    cursor.getInt(cursor.getColumnIndex(Questions._ID)), e);
      }
      catch (ClassNotFoundException e) {
	 throw new ContentException("Error deserializing answer choices for question " + 
				    cursor.getInt(cursor.getColumnIndex(Questions._ID)), e);
      }
   }

   /**
    * Returns a Quiz object with the data found at the given quiz URI.
    * Unlike cursorRowToQuiz, this method retrieves not just the Quiz
    * data, but the associated Question data as well.  Returns a Quiz
    * object with all of its Questions, or null if no data was found
    * at the given URI.
    */
   public static Quiz quizUriToQuiz(Uri quizUri, ContentResolver contentResolver) {
      // First get the quiz
      Cursor quizCursor = null;
      Quiz quiz;
      try {
	 quizCursor = contentResolver.query(quizUri, null, null, null, null);
	 if ((quizCursor == null) || !quizCursor.moveToFirst())
	    return null;
	 quiz = cursorRowToQuiz(quizCursor);
      }
      finally {
	 // Close the cursor after we're done with it
	 if (quizCursor != null) 
	    quizCursor.close();
      }

      // Then get all of its questions
      Uri questionsUri = Uri.withAppendedPath(quizUri, Questions.URI_PATH_SEGMENT);
      Cursor questionsCursor = null;
      try {
	 questionsCursor = contentResolver.query(questionsUri, null, null, null, null);
	 if ((questionsCursor != null) && questionsCursor.moveToFirst()) {
	    do {
	       Question question = cursorRowToQuestion(questionsCursor);
	       quiz.questions.add(question);
	    } while (questionsCursor.moveToNext());
	 }
      }
      finally {
	 // Close the cursor after we're done with it
	 if (questionsCursor != null)
	    questionsCursor.close();
      }

      return quiz;
   }

   /**
    * Returns a Question object with the data found at the given
    * question URI, or null if no data was found.
    */
   public static Question questionUriToQuestion(Uri questionUri, ContentResolver contentResolver) {
      Cursor questionCursor = null;
      try {
	 questionCursor = contentResolver.query(questionUri, null, null, null, null);
	 if ((questionCursor == null) || !questionCursor.moveToFirst()) {
	    return null;
	 } 
	 return cursorRowToQuestion(questionCursor);
      }
      finally {
	 // Close the cursor after we're done with it
	 if (questionCursor != null) 
	    questionCursor.close();
      }
   }
}
