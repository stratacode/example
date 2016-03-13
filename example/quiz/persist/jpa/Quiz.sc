@Entity
@Table(name="quiz")
Quiz {
   // Primary key is the quiz name
   override @Id name;

   // Define a one to many relationship between a quiz and its
   // questions.  The questions will automatically be persisted when the
   // quiz is persisted.
   override @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER) questions;
}
