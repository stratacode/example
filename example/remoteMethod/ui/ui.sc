example.remoteMethod.ui extends server, js.schtml, util {
   void init() {
      // The server methods are not defined in the desktop so just avoid putting this layer there.
      //excludeProcess("Desktop");
   }
}
