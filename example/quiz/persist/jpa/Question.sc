import sc.bind.Bind;
import sc.bind.IListener;

@Entity
@Table(name="question")
Question {
   @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
   long id;

   @Version int version;

   override @Column(length=500) question;
   override @Column(length=500) answerDetail;

   /**
    * Returns the answer choice at the given index.
    */
   @BindSettings(reverseMethod="updateAnswerChoice")
   public String retAnswerChoice(int index) {
      return answerChoices[index];
   }

   override @Lob answerChoices;

   /**
    * Sets the answer choice at the given index to the given value.
    */
   public int updateAnswerChoice(String value, int index) {
      if (!StringUtil.equalStrings(value, answerChoices[index])) {
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

   /*
   @PostLoad
   void fieldStateChanged() {
      Bind.sendAllEvents(IListener.VALUE_CHANGED, this);
   }
   */
}
