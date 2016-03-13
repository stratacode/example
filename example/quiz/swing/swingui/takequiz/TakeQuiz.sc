/**
 * The swing AppFrame which leads you through taking a particular
 * quiz.
 */
public class TakeQuiz extends AppFrame {
   size = SwingUtil.dimension(400, 490);
   xpad = 10;
   ypad = 10;

   // Used by text areas and their scroll panes
   static int textAreaMaxHeight = 100;
   static int scrollPaneYpad = 12;
   static Border textAreaBorder = BorderFactory.createCompoundBorder
      (BorderFactory.createLineBorder(Color.GRAY), BorderFactory.createEmptyBorder(5, 5, 5, 5));

   // Should we insert linebreaks into question/answer text?
   boolean insertLinebreaksInText = true;

   // Maximum number of characters in a line of question text
   int lineWidth = 55;

   // State that changes as we advance through the questions
   int questionIndex = 0;
   boolean questionAnswered = false;
   int currentScore = 0;

   // State set by the caller and/or derived from above
   Quiz quiz;
   int numQuestions := quiz.questions.size();
   // TODO: check the case where numQuestions = 0
   Question currentQuestion := quiz.questions.get(questionIndex);

   // Set the title of the window to reflect the quiz name
   title := "Quiz: " + quiz.name;

   // Display the current question number and score
   object questionLabel extends JLabel {
      text := "Question " + (questionIndex + 1);
      location := SwingUtil.point(xpad, ypad);
      size := preferredSize;
   }
   object scoreLabel extends JLabel {
      text := "Current score: " + currentScore + " of " + numQuestions;
      location := SwingUtil.point(windowWidth - xpad - preferredSize.width, ypad);
      size := preferredSize;
   }

   // Put the question text into a non-editable text area.  The height
   // of the text area is its preferred height (capped by
   // textAreaMaxHeight), and that depends on the number of lines of
   // text in the question.  The text area itself is placed inside a
   // scroll pane, in case the text is too long to fit into the space
   // allowed.
   object questionPane extends JScrollPane {
      object questionText extends JTextArea {
         text := textWithLinebreaks(currentQuestion.question, insertLinebreaksInText);
         editable = false;
         focusable = false;
         opaque = false;
      }
      viewportView = questionText;
      border = textAreaBorder;
      location := SwingUtil.point(xpad, questionLabel.location.y + questionLabel.size.height + ypad);
      size := SwingUtil.dimension(windowWidth - 2*xpad, 
         Math.min(questionText.preferredSize.height + scrollPaneYpad, textAreaMaxHeight));
      opaque = false;
      { getViewport().setOpaque(false); }
   }

   // Create a group of radio buttons for the answer choices.  We
   // could've just set each button's foreground property to the
   // desired Color instead of using HTML text, but unfortunately,
   // swing doesn't allow changing the button's text color when it's
   // in the disabled state.
   object answerChoice0 extends JRadioButton {
      String color := determineChoiceColor
	 (questionAnswered, selected, (currentQuestion.answerIndex == 0));
      text := htmlTextWithColor(currentQuestion.answerChoices[0], color);
      location := SwingUtil.point(xpad, questionPane.location.y + questionPane.size.height + ypad);
      size := preferredSize;
      enabled := !questionAnswered;
   }
   object answerChoice1 extends JRadioButton {
      String color := determineChoiceColor
	 (questionAnswered, selected, (currentQuestion.answerIndex == 1));
      text := htmlTextWithColor(currentQuestion.answerChoices[1], color);
      location := SwingUtil.point(xpad, answerChoice0.location.y + answerChoice0.size.height);
      size := preferredSize;
      enabled := !questionAnswered;
   }
   object answerChoice2 extends JRadioButton {
      String color := determineChoiceColor
	 (questionAnswered, selected, (currentQuestion.answerIndex == 2));
      text := htmlTextWithColor(currentQuestion.answerChoices[2], color);
      location := SwingUtil.point(xpad, answerChoice1.location.y + answerChoice1.size.height);
      size := preferredSize;
      enabled := !questionAnswered;
   }
   object answerChoice3 extends JRadioButton {
      String color := determineChoiceColor
	 (questionAnswered, selected, (currentQuestion.answerIndex == 3));
      text := htmlTextWithColor(currentQuestion.answerChoices[3], color);
      location := SwingUtil.point(xpad, answerChoice2.location.y + answerChoice2.size.height);
      size := preferredSize;
      enabled := !questionAnswered;
   }

   // The elements property of ButtonGroup is set to the list of radio
   // buttons.  The ButtonGroup will keep track of which radio button
   // is selected, and when the selection changes.
   object answerChoiceButtons extends ButtonGroup {
      buttons = Arrays.asList
         (new AbstractButton[] { answerChoice0, answerChoice1, answerChoice2, answerChoice3 });
   }

   // The Submit Answer button is originally invisible; it is 
   // displayed only after the user has selected one of the answer 
   // choices.  Once it is pressed, it goes away again since now
   // questionAnswered is set to true.
   object answerButton extends JButton {
      text = "Submit Answer";
      clickCount =: gotoDisplayAnswer(answerChoiceButtons.selectedIndex);
      visible := ((answerChoiceButtons.selection != null) && !questionAnswered);
      location := SwingUtil.point(xpad, answerChoice3.location.y + answerChoice3.size.height + ypad);
      size := preferredSize;
   }

   // Display the correct answer and answer detail
   object answerLabel extends JLabel {
      text := "The correct answer is: " + currentQuestion.answer;
      visible := questionAnswered;
      size := preferredSize;
      location := SwingUtil.point(xpad, answerChoice3.location.y + answerChoice3.size.height + ypad);
   }

   // The answer detail is again placed inside a scrollable
   // non-editable text area, just like the question.
   object answerDetailPane extends JScrollPane {
      object answerDetailText extends JTextArea {
         text := textWithLinebreaks(currentQuestion.answerDetail, insertLinebreaksInText);
         editable = false;
         focusable = false;
         opaque = false;
      }
      viewportView = answerDetailText;
      visible := questionAnswered;
      border = textAreaBorder;
      location := SwingUtil.point(xpad, answerLabel.location.y + answerLabel.size.height + ypad);
      size := SwingUtil.dimension(windowWidth - 2*xpad, 
         Math.min(answerDetailText.preferredSize.height + scrollPaneYpad, textAreaMaxHeight));
      opaque = false;
      { getViewport().setOpaque(false); }
   }

   // The Next Question button is only displayed after the current
   // question has been answered.
   object nextButton extends JButton {
      text = "Next Question";
      clickCount =: gotoNextQuestion();
      visible := questionAnswered && (questionIndex < numQuestions-1);
      location := SwingUtil.point(xpad, answerDetailPane.location.y + answerDetailPane.size.height + ypad);
      size := preferredSize;
   }

   // When the last question is answered, we're done
   object resultsLabel extends JLabel {
      text := "Your final score is " + currentScore + " out of " + numQuestions + ".  Thanks for playing!";
      visible := questionAnswered && (questionIndex == numQuestions-1);
      location := SwingUtil.point(xpad, answerDetailPane.location.y + answerDetailPane.size.height + ypad*2);
      size := preferredSize;
  }

   object doneButton extends JButton {
      text = "Done";
      clickCount =: gotoDone();
      visible := questionAnswered && (questionIndex == numQuestions-1);
      location := SwingUtil.point(xpad, resultsLabel.location.y + resultsLabel.size.height + ypad);
      size := preferredSize;
   }

   /**
    * If the insertLinebreaksInText flag is set to true, this method
    * inserts linebreaks into the given text string.  The maximum
    * number of characters allowed in a line of text is determined by
    * the lineWidth property.
    */
   String textWithLinebreaks(String text, boolean insertLB) {
      return (insertLB ? StringUtil.insertLinebreaks(text, lineWidth) : text);
   }

   /**
    * The color of each answer choice changes depending on several
    * factors.  If the question hasn't yet been answered, then all the
    * answer choices are displayed in black.  Once the question has
    * been answered, the correct answer choice is displayed in blue;
    * the chosen answer, if incorrect, is displayed in red; and the
    * rest of the answers are displayed in gray.
    */
   static String determineChoiceColor(boolean questionAnswered, 
				      boolean choiceSelected, 
				      boolean choiceCorrect)
   {
      if (!questionAnswered)
         return "black";
      else if (choiceCorrect)
	 return "blue";
      else if (choiceSelected) 
	 return "red";
      else 
         return "gray";
   }

   /**
    * Formats the given text string as HTML, with the purpose of
    * giving it the desired color.
    */
   static String htmlTextWithColor(String text, String color) {
      return "<html><font color=" + color + ">" + text + "</font></html>";
   }

   /**
    * This method is called when the Submit Answer button is pressed.
    * The selectedIndex argument contains the index of the answer
    * choice selected by the user.
    */
   void gotoDisplayAnswer(int selectedIndex) {
      questionAnswered = true;
      if (selectedIndex == currentQuestion.answerIndex)
         currentScore++;
   }

   /**
    * This method is called when the Next Question button is pressed.
    * It advances the state so that the next question is displayed.
    */
   void gotoNextQuestion() {
      questionIndex++;
      questionAnswered = false;
      answerChoiceButtons.clearSelection();
      // Always leave the focus on the first answer choice
      answerChoice0.requestFocusInWindow(); 
   }

   /**
    * This method is called when the Done button is pressed.  It
    * shuts down the application.
    */
   void gotoDone() {
      TakeQuizMain.shutdown();
   }
}
