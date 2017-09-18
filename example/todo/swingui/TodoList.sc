TodoList extends AppFrame {
   location = new Point(300, 300);
   size = new Dimension(430, 430);

   int spad = 10;

   object mainLabel extends JLabel {
      text := "Todo - " + getRemaining(todos) + " of: " + getSize(todos) + " remaining";
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

      @sc.bind.NoBindWarn
      double startTodoY := prev == null ? startListY : prev.location.y + prev.size.height + ypad;

      object completeCheckBox extends JCheckBox {
         selected :=: todo.complete;

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

      // Returns the previous component in the todo list
      java.awt.Component getPrev() {
         if (ix == 0)
            return null;
         else {
            TodoComponent comp = todoList.repeatComponents.get(ix - 1);
            return comp.lastComponent;
         }
      }

   }

   object todoList extends RepeatComponent<TodoComponent> {
      repeat := todos;

      double height = ypad;

      parentComponent = TodoList.this;

      public TodoComponent createRepeatElement(Object todo, int ix, Object oldComp) {
         return new TodoComponent((TodoItem)todo, ix);
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