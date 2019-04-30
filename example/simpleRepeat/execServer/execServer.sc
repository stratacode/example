// Runs using both client/server but marks top level component with exec="server"
// so works like the ui and jetty.schtm but including the JS runtime.  It
// is good for testing server tags functionality in the JS runtime
public example.simpleRepeat.execServer extends ui, js.schtml, jetty.schtml, js.sync {
}
