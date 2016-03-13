class DisplayResults {
   LeadershipStyle timeDrivenDecision;
   LeadershipStyle developmentDrivenDecision;

   DisplayResults() {}

   DisplayResults(Situation situation) {
      timeDrivenDecision = situation.timeDrivenModel();
      developmentDrivenDecision = situation.developmentDrivenModel();
   }
}
