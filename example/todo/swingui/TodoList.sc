TodoList extends AppFrame {
   location = new Point(300, 300);
   size = new Dimension(430, 430);

   int spad = 10;

   object mainLabel extends JLabel {
      // The getRemaining method depends on the 'complete' state of the TodoItem so this binding is refreshed
      // when completeCheckBox.selected changes by calling Bind.refreshBindings
      text := "Todo - " + remaining + " of: " + numTodos + " remaining";
      location := SwingUtil.point(xpad, ypad + baseline);
      size := preferredSize;
   }

   object nameButton extends JButton {
      location := SwingUtil.point(mainLabel.location.x + mainLabel.size.width + xpad, ypad);
      size := preferredSize;
      text = "Remove Completed";
      clickCount =: removeComplete();
   }

   double startListY := nameButton.location.y + nameButton.size.height + ypad + spad;

   class TodoComponent extends ComponentGroup {
      TodoItem todo;
      int ix;

      TodoComponent(TodoItem todo, int ix) {
         this.todo = todo;
         this.ix = ix;
      }

      double startTodoY;

      public void setIx(int ix) {
         this.ix = ix;
         startTodoY = ix == 0 ? startListY : todoList.repeatComponents.get(ix-1).startTodoY + todoTextLabel.size.height + ypad;
      }

      object completeCheckBox extends JCheckBox {
         selected :=: todo.complete;

         // To refresh the mainLabel.text property due to the getRemaining function, validate all bindings on that label
         selected =: Bind.refreshBindings(mainLabel);

         location := SwingUtil.point(xpad, startTodoY);
         size := preferredSize;
      }

      object todoTextLabel extends JLabel {
         text :=: todo.text;
         location := SwingUtil.point(completeCheckBox.location.x + completeCheckBox.size.width + xpad, startTodoY + baseline);
         size := preferredSize;
         enabled := !todo.complete;
      }
   }

   object todoList extends RepeatComponent<TodoComponent> {
      repeat := todos;

      double height = ypad;

      parentComponent = TodoList.this;

      // This will be called once for each todoItem when the RepeatComponent refreshes itself
      public TodoComponent createRepeatElement(Object todo, int ix, Object oldComp) {
         return new TodoComponent((TodoItem)todo, ix);
      }

      // Called to find the original repeat value given the component created above
      public Object getRepeatVar(TodoComponent todoComp) {
         return todoComp.todo;
      }

      public void setRepeatIndex(TodoComponent todoComp, int ix) {
         todoComp.ix = ix;
      }

      boolean refreshList() {
         boolean anyChanges = super.refreshList();

         int newHeight = ypad;
         for (int i = 0; i < repeatComponents.size(); i++) {
            java.awt.Rectangle bounds = SwingUtil.getBoundingRectangle(repeatComponents.get(i));
            newHeight += bounds.size.height + ypad;
         }
         height = newHeight;
         Bind.refreshBindings(mainLabel);

         TodoList.this.revalidate();
         TodoList.this.repaint();

         return anyChanges;
      }
   }

   double startAddY := startListY + todoList.height + ypad + spad;

   object newTodoText extends JTextField {
      location := SwingUtil.point(xpad, startAddY);
      size := SwingUtil.dimension(TodoList.this.size.width - 4*xpad - addTodo.size.width, preferredSize.height);
      text :=: todoText;

      userEnteredCount =: addTodoEntry();
   }

   object addTodo extends JButton {
      location := SwingUtil.point(newTodoText.location.x + newTodoText.size.width, startAddY);
      size := preferredSize;
      text = "Add";
      clickCount =: addTodoEntry();
      enabled := newTodoText.text.length() > 0;
   }
}