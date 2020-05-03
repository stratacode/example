object RemoteMethod {
  // Anything here runs on the client and is (by default) synchronized.  But this is a remote method demo so we'll add the code
  // only in the server layer

   // To access methods on an inner object, the parent object has to exist on the client too so it's defined
   // here and modified in the server layer.
   object motd {

   }
}
