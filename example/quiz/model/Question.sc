/**
 * A Question consists of the question text, four answer choices, and
 * the answer detail.
 */
public class Question {
   static int NO_ANSWER = -1;

   String question;
   String[] answerChoices = new String[4];
   int answerIndex = NO_ANSWER;
   String answerDetail;

   String getAnswer() {
      return answerChoices[answerIndex];
   }
}
