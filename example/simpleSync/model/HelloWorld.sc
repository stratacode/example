object HelloWorld {
   String message = "hello";
   // Note: if you assign a value here, even empty string, by default the sync framework will not send the 'initial value'
   // to the client javascript code (it will still be in the initial HTML).  There are several workarounds -
   // 1) set @Sync(initDefault) on this property (see HelloWorld2).  2) If the reply is done in a "doLater" that
   // should also work - if the setReply method is called after addSyncInst in the code it (TBD) record the
   // initial change.
   String reply;

   // When message or reply change print them using 'reverse only' binding
   message =: System.out.println("--- Message: " + message);
   reply =: System.out.println("--- Reply: " + reply);
}
