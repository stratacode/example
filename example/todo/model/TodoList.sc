// The classic TodoList example.  Unlike most examples, there's no 'view model' or coreui layer.
class TodoList {
   @Component
   class TodoItem {
      String text;
      boolean complete;

      complete =: updateRemaining();

      // Use ManualGetSet so we do not send unnecessary change events from the constructor
      @sc.obj.ManualGetSet
      TodoItem(String t, boolean c) {
          text = t;
          complete = c;
      }
   }
   ArrayList<TodoItem> todos; /* = {
      new TodoItem("Run layerCake todo sample", true),
      new TodoItem("Check me and see it stay in sync", false),
      new TodoItem("Add a new entry and press 'remove completed'", false),
   } */

   String todoText = "";

   // No need to synchronize these two because we sync todos from which they are computed
   @sc.obj.Sync(syncMode=sc.obj.SyncMode.Disabled)
   int remaining, numTodos;

   todos =: updateRemaining();

   void addTodoEntry() {
      todos.add(new TodoItem(todoText, false));
      todoText = "";
   }

   void updateRemaining() {
      int count = 0;
      if (todos != null) {
         for (TodoItem todo: todos) {
            if (!todo.complete)
               count++;
         }
      }
      remaining = count;
      numTodos = todos.size();
   }

   int getSize(List<TodoItem> list) {
      return list == null ? 0 : list.size();
   }

   void removeComplete() {
      for (int i = 0; i < todos.size(); i++) {
         TodoItem todo = todos.get(i);
         if (todo.complete) {
            todos.remove(i);

            DynUtil.dispose(todo);
            i--;
         }
      }
   }
}
