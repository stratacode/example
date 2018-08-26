import sc.obj.MainSettings;

// All-in-one class example where you have a single class that is configured in downstream layers.  If modified
// by dynamic types, Config itself becomes a dynamic type.  See the ConfigClass/ConfigObj pattern for a way to
// split up compiled parts and dynamic parts of an implementation.
class Config {
   String strProp;
   int intProp;

   @MainSettings
   public static void main(String[] args) {
      Config config = new Config();
      System.out.println("*** SimpleConfiguration example");
      System.out.println("strProp: " + config.strProp);
      System.out.println("intProp: " + config.intProp);
   }
}

