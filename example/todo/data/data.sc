example.todo.data extends model {
  // Do not put this layer into both client and server when it's enabled.
  // It should only go in the server which with sync enabled will init
  // the client.  If it's client-only, it's included.
  includeForInit = true;
}
