/* A simple unit converter using StrataCode objects and data binding. */
class UnitConverter {
   static class Converter {
      double value1;
      double value2;
      String unit1, unit2; // Unit labels
      String title;
      String toString() {
         return title == null ? "<unset>" : title; 
      }
   }
   
   object converters extends ComponentList<Converter> {
      object temperature extends Converter {
         value1 = 0;
         value2 :=: value1 * 9.0 / 5.0 + 32;
         unit1 = "Celsius";
         unit2 = "Fahrenheit";
         title = "Temperature";
      }
      object distance extends Converter {
         value1 = 0;
         value2 :=: value1 * 0.62137119;
         unit1 = "Kilometers";
         unit2 = "Miles";
         title = "Distance";
      }
      object speed extends Converter {
         title = "Speed";
         value1 = 0;
         value2 :=: value1 / 1.609344;
         unit1 = "Kilometers/hour";
         unit2 = "Miles/hour";
      }
      object power extends Converter {
         value1 = 0;
         value2 :=: value1 * 0.00134;
         unit1 = "Watts";
         unit2 = "Horsepower";
         title = "Power";
      }
   }
}
