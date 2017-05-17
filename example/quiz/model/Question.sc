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

   /**
    * Returns the answer choice at the given index.
    */
   @sc.bind.BindSettings(reverseMethod="updateAnswerChoice")
   public String retAnswerChoice(int index) {
      if (index >= answerChoices.length)
         throw new IllegalArgumentException("Answer choice not in valid range: " + index + " > numChoices " + answerChoices.length);
      return answerChoices[index];
   }

   /**
    * Sets the answer choice at the given index to the given value.
    */
   public int updateAnswerChoice(String value, int index) {
      if (!sc.util.StringUtil.equalStrings(value, answerChoices[index])) {
	 answerChoices[index] = value;
	 // Openjpa doesn't detect that this property has changed if
	 // you simply set one of the array elements.  We must reset
	 // the property itself.
	 // TODO: this might not be necessary if answerChoices is a List
	 String[] ac = new String[] 
	    { answerChoices[0], answerChoices[1], answerChoices[2], answerChoices[3] };
	 answerChoices = ac;
      }
      return index;
   }


}
