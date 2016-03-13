package sc.example.unitConverter;

@sc.js.JSSettings(prefixAlias="scex_")
// With server
example.unitConverter.jsui extends jetty.schtml, js.schtml, coreui {
// Without server
//unitConverter.jsui extends js.schtml, coreui {
   codeFunction = sc.layer.CodeFunction.UI;
}
