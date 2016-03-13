class AnalyzeSituation {
   SituationalFactor[] situationalFactors = SituationalFactor.values();
   int numFactors = situationalFactors.length;
   int factorIndex = 0;

   SituationalFactor currentFactor := factorIndex < situationalFactors.length ? situationalFactors[factorIndex] : null;
   boolean answersComplete = false;

   object situation extends Situation {
   }

   void resetSituation() {
      answersComplete = false;
      factorIndex = 0;
   }

   void setSituationFactor(FactorValue factorValue) {
      situation.setFactorValue(currentFactor, factorValue);
      factorIndex++;
   }
}
