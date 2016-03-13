/**
 * Convenience definitions for QuizProvider.
 */
public interface QuizContent {
   public static final String AUTHORITY = "sc.example.quiz.content.quizprovider";

   /**
    * Quiz-related definitions.
    */
   public interface Quizes extends BaseColumns {
      /**
       * The content:// style URI for this table.
       */
      public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/quizes");

      /**
       * MIME type of a directory of quizes.
       */
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.sc.quiz";
      
      /**
       * MIME type of a sub-directory of a single quiz.
       */
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.sc.quiz";
      
      /**
       * Quiz name.
       * <p>Type: TEXT</p>
       */
      public static final String NAME = "name";
   }
   
   /**
    * Question-related definitions.
    */
   public interface Questions extends BaseColumns {
      /**
       * URI path segment which is appended to a quiz URI in order to
       * obtain the URI for that quiz's questions.
       */
      public static final String URI_PATH_SEGMENT = "questions";

      /**
       * MIME type of a directory of questions.
       */
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.sc.question";
      
      /**
       * MIME type of a sub-directory of a single question.
       */
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.sc.question";
      
      /**
       * Id of the quiz the question belongs to.
       * <p>Type: INTEGER</p>
       */
      public static final String QUIZ_ID = "quiz_id";
      
      /**
       * Question text.
       * <p>Type: TEXT</p>
       */
      public static final String QUESTION = "question";

      /**
       * Answer choices.
       * <p>Type: BLOB (a serialized <code>String</code> array)</p>
       */
      public static final String ANSWER_CHOICES = "answer_choices";

      /**
       * Index of the correct answer choice.
       * <p>Type: INTEGER</p>
       */
      public static final String ANSWER_INDEX = "answer_index";
      
      /**
       * Answer detail text.
       * <p>Type: TEXT</p>
       */
      public static final String ANSWER_DETAIL = "answer_detail";
   }
}
