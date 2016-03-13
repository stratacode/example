import java.util.HashMap;

class Situation {
   Map<SituationalFactor, FactorValue> factorValues = 
      new HashMap<SituationalFactor, FactorValue>();

   FactorValue getFactorValue(SituationalFactor factor) {
      return factorValues.get(factor);
   }

   void setFactorValue(SituationalFactor factor, FactorValue factorValue) {
      factorValues.put(factor, factorValue);
   }

   // Note: these methods are abstract but the class is not.  The implementations
   // must be supplied by a subsequent layer.  
   abstract LeadershipStyle timeDrivenModel();

   abstract LeadershipStyle developmentDrivenModel();
}
