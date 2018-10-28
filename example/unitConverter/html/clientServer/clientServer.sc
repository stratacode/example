package sc.example.unitConverter;

@sc.js.JSSettings(prefixAlias="scex_")
// With server
example.unitConverter.html.clientServer extends example.unitConverter.html.core, jetty.schtml, js.schtml, js.sync {
// Without server
//unitConverter.jsui extends js.schtml, coreui {
   codeType = sc.layer.CodeType.UI;
}
