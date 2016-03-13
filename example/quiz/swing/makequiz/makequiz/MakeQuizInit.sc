/**
 * A MakeQuiz instance which gets created on initialization.
 */
@MainInit 
object MakeQuizInit extends MakeQuiz {
outerSplitPane {
      innerSplitPane {
         quizListPanel {
            quizListPane {
               quizNameList {}
            }
         }
         questionListPanel {
            questionListPane {
               questionTextList {}
            }
         }
      }
   }
}
