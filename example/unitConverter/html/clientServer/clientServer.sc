package sc.example.unitConverter;

// Use this as the JS prefix for classes instead of "sc_example_unitConverter"
@sc.js.JSSettings(prefixAlias="scex_")
// Runs two separate applications the server (jetty.shtml) 
// and the browser (js.schtml) with synchronization between the
// two enabled (js.sync).
example.unitConverter.html.clientServer extends example.unitConverter.html.core, jetty.schtml, js.schtml, js.sync {
   codeType = sc.layer.CodeType.UI;
}
