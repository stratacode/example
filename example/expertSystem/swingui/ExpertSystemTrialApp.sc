@MainInit
class ExpertSystemTrialApp extends AppFrame {
   size = SwingUtil.dimension(800, 400);
   object analyzeSituation extends AnalyzeSituation {
      size := ExpertSystemTrialApp.this.size;
      visible := !analyzeSituation.answersComplete;
   }

   object displayResults extends DisplayResults {
      timeDrivenDecision := analyzeSituation.timeDrivenDecision;
      developmentDrivenDecision := analyzeSituation.developmentDrivenDecision;
      size := ExpertSystemTrialApp.this.size;
      visible := analyzeSituation.answersComplete;
      doneButton {
         clickCount =: analyzeSituation.resetSituation();
      }
   }
}
