// This is a simple layer which specifies the layers to run this app.  No code in this layer currently.
// By extending jetty.schtml we run on the server and js.template we run on the client.
// For client-only need to use "data" cause serverData won't run on the client.
example.todo.main extends jsui, data, jetty.schtml, js.schtml {
}
