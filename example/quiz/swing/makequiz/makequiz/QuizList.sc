/**
 * A panel which lists all the existing quiz names.  Each individual
 * quiz can be selected for editing.  New quizes can be added, and
 * existing quizes can be deleted, by means of a context menu.
 */
public class QuizList extends JPanel {
   // A border around the list of quiz names
   static Border quizListBorder = BorderFactory.createCompoundBorder
      (BorderFactory.createEmptyBorder(5, 5, 0, 5), BorderFactory.createTitledBorder("Quizes"));

   border = quizListBorder;
   Insets panelInsets := insets;

   // State shared by all the panels
   QuizDataManager dataManager;
   MakeQuizState state;

   // A custom dialog used to name and create a new quiz
   object nameQuiz extends NameQuiz {
      parent = QuizList.this;
      dataManager := QuizList.this.dataManager;
   }

   // A popup menu with menu items to add a new quiz and delete an
   // existing quiz
   object quizPopupMenu extends JPopupMenu implements ActionListener {
      object addQuizMenuItem extends JMenuItem {
	 text = "Add quiz";
	 { addActionListener(quizPopupMenu); }
      }
      object deleteQuizMenuItem extends JMenuItem {
	 text := "Delete quiz " + state.quizName;
	 visible := (state.quizName != null);
	 { addActionListener(quizPopupMenu); }
      }

      void actionPerformed(ActionEvent event) {
	 JMenuItem menuItem = (JMenuItem) event.source;
	 if (menuItem == addQuizMenuItem)
	    gotoAddQuiz();
	 else if (menuItem == deleteQuizMenuItem) 
	    gotoDeleteQuiz();
      }
   }

   // Listens for mouse events and brings up the popup menu when
   // necessary
   object popupListener extends PopupMenuListener {
      popupMenu = quizPopupMenu;
   }

   // A scroll pane with a list of all quiz names
   object quizListPane extends JScrollPane {
      location := SwingUtil.point(panelInsets.left, panelInsets.top);
      size := SwingUtil.subInsets(QuizList.this.size, panelInsets);
      viewportView = quizNameList;

      object quizNameList extends JList {
	 listItems := getAllQuizNames(state.quizListRefreshToggle);
	 selectionMode = ListSelectionModel.SINGLE_SELECTION;
	 selectedValue :=: (String) state.quizName;
	 { addMouseListener(popupListener); }
      }
   }

   /**
    * Retrieves the names of all the quiz objects stored in the
    * database.  This method gets called every time the refresh flag
    * is toggled.
    */
   List<String> getAllQuizNames(boolean refreshToggle) {
      if (dataManager == null)
	 return null;
      return dataManager.getAllQuizNames();
   }

   /**
    * Creates a new quiz by popping up a dialog which allows the user
    * to name the quiz, and persists it in the database.
    */
   void gotoAddQuiz() {
      String newQuizName = nameQuiz.showInputDialog();

      // If a valid quiz name wasn't specified (e.g., because the user
      // canceled the dialog), then a new quiz was not created
      if (newQuizName == null)
	 return;

      // Refresh the quiz name list to include the one we just created
      state.toggleQuizListRefresh();

      // The new quiz becomes automatically selected
      state.quizName = newQuizName;
   }

   /**
    * Deletes the currently selected quiz from the database.
    */
   void gotoDeleteQuiz() {
      // Confirm delete
      int option = JOptionPane.showConfirmDialog
	 (SwingUtilities.windowForComponent(this), 
	  "Are you sure you want to delete quiz " + state.quizName + "?", 
	  "Confirm Delete", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

      if (option == JOptionPane.CANCEL_OPTION)
         return;

      try {
         dataManager.deleteQuiz(state.quizName);
      }
      catch (EntityNotFoundException e) {
	 // Somebody must have deleted this quiz before us;
	 // do nothing.
      }
      catch (OptimisticLockException e) {
	 // Somebody must have deleted this question before
	 // us; do nothing.
      }

      // Refresh the quiz name list to exclude the one we just deleted
      state.toggleQuizListRefresh();
   }
}

