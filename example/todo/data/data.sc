// A simple data layer that can run on the client.  Extends the clientOrServer layer this
// layer runs only on the client or server, not both (without dragging in an explicit dependency
// on either the client or the server)
example.todo.data extends jsui, js.clientOrServer {
}
