object HelloWorld3 {
   String message = "hello";
   // In this version we initialize reply to a value so initDefault is false
   // but we'll set a "doLater" on the reply := binding in the server layer
   // so that setReply is called after addSyncInst - causing us to send the
   // initial change on the first request because it's set after the initial value.
   String reply = "";

   // When message or reply change print them using 'reverse only' binding
   message =: System.out.println("--- Message: " + message);
   reply =: System.out.println("--- Reply: " + reply);
}
