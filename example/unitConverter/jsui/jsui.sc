package sc.example.unitConverter;

@sc.js.JSSettings(prefixAlias="scex_")
// With server
example.unitConverter.jsui extends jetty.schtml, js.schtml, js.sync, coreui {
// Without server
//unitConverter.jsui extends js.schtml, coreui {
   codeType = sc.layer.CodeType.UI;
}
