// This layer will only run on the server because it extends a server-only layer explicitly.
// Ordinarily, this will be a real dependency - e.g. extending a database layer or something.
public example.remoteMethod.server extends shared, jetty.schtml {
}
