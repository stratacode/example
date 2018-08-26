import sc.obj.MainSettings;

object ConfigObj extends ConfigClass {
   strProp = "default configuration";
   intProp = -1; // default configuration to be overridden

   @MainSettings
   public static void main(String[] args) {
      ConfigObj config = new ConfigObj();
      System.out.println("*** ConfigObj/Class example:");
      System.out.println("strProp: " + config.strProp);
      System.out.println("intProp: " + config.intProp);
   }
}
