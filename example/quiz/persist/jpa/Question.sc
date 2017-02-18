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

   override @Lob answerChoices;


   /*
   @PostLoad
   void fieldStateChanged() {
      Bind.sendAllEvents(IListener.VALUE_CHANGED, this);
   }
   */
}
