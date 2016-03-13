RemoteMethod {
   void echoHello() {
      System.out.println("hello!");
   }

   String sayHello(String name) {
      return "Hello: " + name;
   }

   Number addNumbers(double a, double b) {
      return a + b;
   }

   ArrayList<Number> addListOfNumbers(ArrayList<Number> a, ArrayList<Number> b) {
      ArrayList<Number> res = new ArrayList<Number>();
      int len = Math.min(a.size(), b.size());
      for (int i = 0; i < len; i++)
         res.add(a.get(i).doubleValue() + b.get(i).doubleValue());
      return res;
   }

   motd {
      String messageStr = "LayerCake is now StrataCode";
      String getMessage() {
         return messageStr;
      }
   }
}
