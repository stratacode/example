// This layer will only run on the server because it extends a server-only layer explicitly.
// This reflects a dependency from this code on server features - e.g. extending a database layer or something.
public example.remoteMethod.server extends shared, jetty.schtml {
}
