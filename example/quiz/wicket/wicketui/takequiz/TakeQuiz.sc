/**
 * The WebPage which actually lets you take a particular quiz.
 */
public class TakeQuiz extends QuizWebPage {
   // State that changes as we advance through the questions
   int questionIndex = 0;
   boolean questionAnswered = false;
   int currentScore = 0;

   // State passed in via the constructor and/or derived from above
   Quiz quiz;
   int numQuestions := quiz.questions.size();
   // TODO: should quietly return null without the check?
   Question currentQuestion := 
      (numQuestions == 0) ? null : quiz.questions.get(questionIndex);

   // Set the title of the page to reflect the quiz name
   object pageTitle extends CLabel {
      textValue := "Quiz: " + quiz.name;
   }

   // The main form
   object takeQuizForm extends CForm<TakeQuiz> {
      // Don't display the form if we don't have the quiz/questions.
      visible := (quiz != null && numQuestions > 0);

      // Display the current question number and score
      object questionLabel extends CLabel {
	 textValue := "Question " + (questionIndex + 1);
      }
      object scoreLabel extends CLabel {
         textValue := "Current score: " + currentScore + " of " + numQuestions;
      }

      // Question text
      object questionText extends CLabel {
         textValue := currentQuestion.question;
      }

      // Create a group of radio buttons for the answer choices
      object answerChoiceButtons extends CRadioGroup<Integer> {
         selectionChangedNotifications = true;

         object answerChoices extends CListView<String> {
	    // TODO: should quietly return null without the check?
            list := (currentQuestion == null) ? null : Arrays.asList(currentQuestion.answerChoices);
            scope<ListItem> object answerChoice implements Serializable {
               object answerChoiceRadio extends CRadio<Integer> {
                  choiceValue = listItemIndex;
                  enabled := !questionAnswered;
               }
               object answerChoiceText extends CLabel {
                  // alternatively textValue := currentQuestion.answerChoices[listItemIndex];
                  textValue := listItemValue;
                  object classModifier extends CAttributeModifier {
                     attribute = "class";
                     attributeValue := determineChoiceCssClass
                        (questionAnswered, answerChoiceRadio.selected, (currentQuestion.answerIndex == listItemIndex));
                  }
               }
            }
         }
      }

      // The Submit Answer button is originally invisible; it is
      // displayed only after the user has selected one of the answer
      // choices.  Once it is pressed, it goes away again since now
      // questionAnswered is set to true.
      object answerButton extends Button {
	 visible := ((answerChoiceButtons.currentValue != null) && !questionAnswered);
	 public void onSubmit() {
	    questionAnswered = true;
	    // We can use either currentValue or currentChoiceIndex here
	    if (answerChoiceButtons.currentChoiceIndex == currentQuestion.answerIndex)
	       currentScore++;
	 }
      }

      // Display the correct answer and answer detail
      object answerLabel extends CLabel {
	 textValue := "The correct answer is: " + currentQuestion.answer;
	 visible := questionAnswered;
      }
      object answerDetailText extends CLabel {
	 textValue := currentQuestion.answerDetail;
	 visible := questionAnswered;
      }

      // The Next Question button is only displayed after the current
      // question has been answered.
      object nextButton extends Button {
	 visible := questionAnswered && (questionIndex < numQuestions-1);
	 public void onSubmit() {
	    questionIndex++;
	    questionAnswered = false;
	    // Clear the radio group selection
	    answerChoiceButtons.clearSelection();
	 }      
      }

      // When the last question is answered, we're done
      object resultsLabel extends CLabel {
	 textValue := "Your final score is " + currentScore + " out of " + 
	    numQuestions + ".  Thanks for playing!";
	 visible := questionAnswered && (questionIndex == numQuestions-1);
      }
      
      object doneButton extends Button {
	 visible := questionAnswered && (questionIndex == numQuestions-1);
	 public void onSubmit() {
	    // This creates a new instance of the PickQuiz page
	    gotoPage(PickQuiz.class);
	 }
      }      
   }

   // The error message panel
   object feedback extends FeedbackPanel {
   }

   /**
    * Creates a new TakeQuiz page for a Quiz with the given name.
    */
   public TakeQuiz(String quizName) {
      quiz = getQuizByName(quizName);
      if (quiz == null)
	 error("Quiz " + quizName + " does not exist.");
      else if (quiz.questions.size() == 0)
	 error("Quiz " + quizName + " does not contain any questions.");
   }

   /**
    * Retrieves the Quiz with the given name.  The Quiz is looked up
    * by name in the static QuizData object.
    */
   Quiz getQuizByName(String quizName) {
      return QuizData.getQuizByName(quizName);
   }

   /**
    * The CSS style of each answer choice changes depending on several
    * factors.  If the question hasn't yet been answered, then all the
    * answer choices are styled as "enabled."  Once the question has
    * been answered, the correct answer choice is styled as "correct";
    * the chosen answer, if incorrect, is styled as "incorrect"; and
    * the rest of the answers are styled as "disabled."
    */
   static String determineChoiceCssClass(boolean questionAnswered, 
					 boolean choiceSelected, 
					 boolean choiceCorrect)
   {
      if (!questionAnswered)
	 return "enabled";
      else if (choiceCorrect) 
	 return "correct";
      else if (choiceSelected) 
	 return "incorrect";
      else 
	 return "disabled";
   }
}
