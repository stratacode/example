Question {
   static int displayLength = 18;

   /**
    * Returns the String representation of this question which can be
    * used by the UI.
    */
   public String toString() {
      if (question == null)
	 return "New question";
      return StringUtil.ellipsis(question, displayLength, true);
   }
}