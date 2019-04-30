HelloWorld {
   // When the message changes, set the reply property.
   // Use server-only bindings like this for database queries or other logic
   // that should not run on the client.
   reply := "Server says: " + message;
}
