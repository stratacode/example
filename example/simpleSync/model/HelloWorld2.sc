object HelloWorld2 {
   String message = "hello";

   // This version initializes reply to a value but overrides the default via the @Sync(initDefault)
   // implementation so that the client is initialized with the server's initial value of 'reply'
   @sc.obj.Sync(initDefault=true)
   String reply = "";

   // When message or reply change print them using 'reverse only' binding
   message =: System.out.println("--- Message: " + message);
   reply =: System.out.println("--- Reply: " + reply);
}
