// Run the todo example with code running on both client and server by extending both the js and jetty layers.
// Include jsui make sure we have the jsui layer running in both runtimes.  We extend jsdata so there's some
// sample data and although it also extends jsui, it is excluded from the client in a client/server environment so
// it's not enough to extend jsdata alone.
example.todo.clientServer extends jsui, jsdata, js.schtml, jetty.schtml, js.sync {
}
