class TodoList {
   @Component
   class TodoItem {
      String text;
      boolean complete;

      TodoItem(String t, boolean c) {
          text = t;
          complete = c;
      }
   }
   ArrayList<TodoItem> todos; /* = {
      new TodoItem("Run layerCake todo sample", true),
      new TodoItem("Check me and see it stay in sync", false),
      new TodoItem("Add a new entry and press 'remove completed'", false),
   } */;

   String todoText = "";

   void addTodoEntry() {
      todos.add(new TodoItem(todoText, false));
      todoText = "";
   }

   int getRemaining(List<TodoItem> todoList) {
      int count = 0;
      if (todoList == null) {
         System.out.println("*** no list");
         return 0;
      }
      for (TodoItem todo: todoList) {
         if (!todo.complete)
            count++;
      }
      return count;
   }

   int getSize(List<TodoItem> list) {
      return list == null ? 0 : list.size();
   }

   void removeComplete() {
      ArrayList<TodoItem> newTodos = new ArrayList<TodoItem>();
      for (TodoItem todo: todos) {
         if (!todo.complete) 
            newTodos.add(todo);
      }
      todos = newTodos;
   }
}
