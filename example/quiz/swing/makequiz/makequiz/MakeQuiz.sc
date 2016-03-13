/**
 * The main application frame, which contains the QuizList,
 * QuestionList, and EditQuestion panels.  
 */
public class MakeQuiz extends AppFrame {
   size = SwingUtil.dimension(550, 480);

   // Display size does not include the title bar, border, etc.
   Dimension displaySize := SwingUtil.subInsets(size, insets);

   // Used to access and persist the quiz data
   object dataManager extends QuizDataManager {
   }  

   // Object used to maintain the current state
   object currentState extends MakeQuizState {
   }

   // Set the title of the window to reflect the quiz name
   title := (currentState.quizName == null) ? "Make Quiz" : ("Make Quiz: " + currentState.quizName);

   // A split pane with quiz/question lists on the left side, and the
   // panel for editing the question on the right
   object outerSplitPane extends JSplitPane {
      leftComponent = innerSplitPane;
      rightComponent = editQuestionPanel;
      orientation = HORIZONTAL_SPLIT;
      dividerPosition = 150;
      size := displaySize;

      // Compute the outer split pane's display size (without the
      // border), and the sizes of its left and right components
      Dimension outerDisplaySize := SwingUtil.subInsets(size, insets);
      int leftWidth := outerSplitPane.dividerPosition - outerSplitPane.insets.left;
      int rightWidth := (int) outerDisplaySize.width - leftWidth - outerSplitPane.dividerSize;

      // A split pane with the quiz list on the left side, and the
      // question list on the right
      object innerSplitPane extends JSplitPane {
	 topComponent = quizListPanel;
	 bottomComponent = questionListPanel;
	 orientation = VERTICAL_SPLIT;
	 dividerPosition = 200;
	 size := SwingUtil.dimension(leftWidth, outerDisplaySize.height);

	 // Compute the inner split pane's display size (without the
	 // border), and the sizes of its top and bottom components
	 Dimension innerDisplaySize := SwingUtil.subInsets(size, insets);
	 int topHeight := innerSplitPane.dividerPosition - innerSplitPane.insets.top;
	 int bottomHeight := (int) innerDisplaySize.height - topHeight - innerSplitPane.dividerSize;

	 // The QuizList panel lets the user select the quiz
	 object quizListPanel extends QuizList {
	    size := SwingUtil.dimension(innerDisplaySize.width, topHeight);
	    dataManager = MakeQuiz.this.dataManager;
	    state = currentState;
	 }

	 // The QuestionList panel lets the user select the question
	 object questionListPanel extends QuestionList {
	    size := SwingUtil.dimension(innerDisplaySize.width, bottomHeight);
	    dataManager = MakeQuiz.this.dataManager;
	    state = currentState;
	 }
      }

      // The EditQuestion panel lets the user edit the question data
      object editQuestionPanel extends EditQuestion {
	 size := SwingUtil.dimension(rightWidth, outerDisplaySize.height);
	 dataManager = MakeQuiz.this.dataManager;
	 state = currentState;
      }
   }
}
