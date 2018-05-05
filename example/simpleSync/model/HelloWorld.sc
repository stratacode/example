object HelloWorld {
   String message = "hello";
   String reply = "";

   // When message or reply change print them using 'reverse only' binding
   message =: System.out.println("--- Message: " + message);
   reply =: System.out.println("--- Reply: " + reply);
}
