### Simple Parser

This example shows you how to use apis to manipulate
Java code without specifying layer definition files and using
the normal "LayeredSystem" main routine which is used by the
'scc' command.

Instead, you create the LayeredSystem programmatically, configure
it's options, then use methods on the system to retrieve JavaModel
or TypeDeclaration instances.  These are the modifiable AST objects.

This example reads one or more types, enumerates the fields, and methods
in each top-level class, and optionally does a simple replace
of a class name, method name, or field name.  Although we do not show changes 
expressions using those types, that's feasible by digging just a little deeper into the API.

You run the program with a srcPath containing Java source files.  You can
also set an optional classPath to support any source in that
srcPath.

### Running the example

Edit run.sh script, change the path to where you have built or installed the "sc.jar".

See runTest.sh for a set of tests on the testSrc directory provided.

If you use -find <name>, you provide a prefix for matching class names.  In this case
we load all source files and create a type index to do the search more efficiently.
It's stored in the 'idx' directory where you run the sample.
