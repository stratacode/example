UnitConverter {
 
   // Adds three more converters - we could replace them by just redefining
   // the type of the array, as long as the new array was a of a compatible type like this:
   // object converters extends sc.util.ComponentList<Converter> ...
   // The annotations AddBefore and AddAfter allow you to add elements before or after an 
   // element in the previous layer
   converters {
      object volume extends Converter {
         value1 = 1.0;
         value2 :=: value1 / 0.94635295;
         unit1 = "Liters";
         unit2 = "Quarts";
         title = "Volume";
      }
      object weight extends Converter {
         value1 :=: value2 / 0.035273962;
         value2 = 0;
         unit1 = "Grams";
         unit2 = "Ounces (US)";
         title = "Weight";
      }
      object area extends Converter {
         value1 = 0;
         value2 :=: value1 / 2.5899881;
         unit1 = "Sq Kilometers";
         unit2 = "Sq Miles";
         title = "Area";
      }
      /*
      object power extends Converter {
         value1 = 0;
         value2 :=: value1 * 0.00134;
         unit1 = "Watts";
         unit2 = "Horsepower";
         title = "Power";
      }
      object power2 extends Converter {
         value1 = 0;
         value2 :=: value1 * 0.00134;
         unit1 = "Watts2";
         unit2 = "Horsepower2";
         title = "Power2";
      }
      object power3 extends Converter {
         value1 = 0;
         value2 :=: value1 * 0.00134;
         unit1 = "Watts3";
         unit2 = "Horsepower3";
         title = "Power3";
      }
      object power4 extends Converter {
         value1 = 0;
         value2 :=: value1 * 0.00134;
         unit1 = "Watts4";
         unit2 = "Horsepower4";
         title = "Power4";
      }
      object power5 extends Converter {
         value1 = 0;
         value2 :=: value1 * 0.00134;
         unit1 = "Watts5";
         unit2 = "Horsepower5";
         title = "Power5";
      }
      object power6 extends Converter {
         value1 = 0;
         value2 :=: value1 * 0.00134;
         unit1 = "Watts6";
         unit2 = "Horsepower6";
         title = "Power6";
      }
      */
   }
}
