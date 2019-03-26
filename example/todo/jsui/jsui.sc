public example.todo.jsui extends html.schtml, model {
   codeType = sc.layer.CodeType.UI;
   void init() {
      // Not strictly necessary, but these two lines will keep the jsui layer out of the Desktop runtime by tieing it to
      // any JS process or the Server process for java.
      includeProcess("Server");
      includeRuntime("js");
   }
}
