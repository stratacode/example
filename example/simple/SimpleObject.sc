@MainInit
object SimpleObject extends SimpleClass {
   int d = 4;
   int e;
   String f := d == 4 ? "d is 4" : "d is not 4";
}
