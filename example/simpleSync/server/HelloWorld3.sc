// This runs on the server - it could be a database query or secure logic you do not want
// to expose on the client. 
HelloWorld3 {
   // TODO: needs to be implemented.  
   //override @sc.bind.Bindable(doLater=true)
   reply := "Server says: " + message;
}
