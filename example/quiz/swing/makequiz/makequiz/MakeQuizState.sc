/**
 * Application state shared by all the UI elements.
 */
public class MakeQuizState {
   // Name of the quiz currently being viewed/edited
   String quizName =: clearNewQuestionEdit();
   
   // Id of the question currently being viewed/edited
   long questionId = 0;

   // Index of the question currently being viewed/edited
   int questionIndex = -1;

   // Are we editing a newly created question?
   boolean newQuestionEdit = false;

   // When toggled, quiz list should be refreshed
   boolean quizListRefreshToggle = true;

   /**
    * Clears the newQuestionEdit field.
    */ 
   void clearNewQuestionEdit() {
      newQuestionEdit = false;
   }

   /**
    * Toggles the quizListRefreshToggle field.
    */
   void toggleQuizListRefresh() {
      quizListRefreshToggle = !quizListRefreshToggle;
   }
}

