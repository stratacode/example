// Need to extend html.schtml since that's the main framework used in all runtimes.  We extend server because this page
// depends on features that are only available in the server layers.  Although server also extends html.schtml indirectly
// we'll lose that dependency if we are used in JS because the server itself is not in the JS runtime.  Then the only
// layer we extend is 'util' and we don't find the extra HtmlPage tag deefinition that makes html.schtml work properly.
example.remoteMethod.ui extends server, html.schtml, util {
   void init() {
      // The server methods are not defined in the desktop so just avoid putting this layer there.
      //excludeProcess("Desktop");

      // If the JS runtime is present, we'll include this layer in it as well.  But if not, we'll run only on the server.
      // because we extend 'server' which extends just jetty.schtml to tie it to the server, we want to open the the possibility
      // of this layer also running on the client in a different configuration.  It depends on what other layers are included
      includeRuntimes("js", "java");
   }
}
