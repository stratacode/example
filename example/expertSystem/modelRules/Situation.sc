Situation {
   // Time driven model
   LeadershipStyle timeDrivenModel() {
      FactorValue decisionSignificance = getFactorValue(SituationalFactor.DECISION_SIGNIFICANCE);
      switch (decisionSignificance) {
         case HIGH: {
            FactorValue importanceOfCommitment = getFactorValue(SituationalFactor.IMPORTANCE_OF_COMMITMENT);
            switch (importanceOfCommitment) {
               case HIGH: {
                  FactorValue leaderExpertise = getFactorValue(SituationalFactor.LEADER_EXPERTISE);
                  switch (leaderExpertise) {
                     case HIGH: {
                        FactorValue likelihoodOfCommitment = getFactorValue(SituationalFactor.LIKELIHOOD_OF_COMMITMENT);
                        switch (likelihoodOfCommitment) {
                           case HIGH:
                              return LeadershipStyle.DECIDE;
                           case LOW: {
                              FactorValue goalAlignment = getFactorValue(SituationalFactor.GOAL_ALIGNMENT);
                              switch (goalAlignment) {
                                 case HIGH: {
                                    FactorValue groupExpertise = getFactorValue(SituationalFactor.GROUP_EXPERTISE);
                                    switch (groupExpertise) {
                                       case LOW: 
                                          return LeadershipStyle.CONSULT_GROUP;
                                       case HIGH: {
                                          FactorValue teamCompetence = getFactorValue(SituationalFactor.TEAM_COMPETENCE);
                                          switch (teamCompetence) {
                                             case HIGH:
                                                return LeadershipStyle.FACILITATE;
                                             case LOW:
                                                return LeadershipStyle.CONSULT_GROUP;
                                          }
                                       }
                                    }
                                 }
                                 case LOW:
                                    return LeadershipStyle.CONSULT_GROUP;
                              }
                           }
                        }
                     }
                     case LOW: {
                        FactorValue likelihoodOfCommitment = getFactorValue(SituationalFactor.LIKELIHOOD_OF_COMMITMENT);
                        switch (likelihoodOfCommitment) {
                           case LOW: {
                              FactorValue goalAlignment = getFactorValue(SituationalFactor.GOAL_ALIGNMENT);
                              switch (goalAlignment) {
                                 case HIGH: {
                                    FactorValue groupExpertise = getFactorValue(SituationalFactor.GROUP_EXPERTISE);
                                    switch (groupExpertise) {
                                       case HIGH: {
                                          FactorValue teamCompetence = getFactorValue(SituationalFactor.TEAM_COMPETENCE);
                                          switch (teamCompetence) {
                                             case HIGH:
                                                return LeadershipStyle.FACILITATE;
                                             case LOW:
                                                return LeadershipStyle.CONSULT_GROUP;
                                          }
                                       }
                                       case LOW:
                                          return LeadershipStyle.CONSULT_GROUP;
                                    }
                                 }
                                 case LOW:
                                    return LeadershipStyle.CONSULT_GROUP;
                              }
                           }
                           case HIGH: {
                              FactorValue goalAlignment = getFactorValue(SituationalFactor.GOAL_ALIGNMENT);
                              switch (goalAlignment) {
                                 case HIGH: {
                                    FactorValue groupExpertise = getFactorValue(SituationalFactor.GROUP_EXPERTISE);
                                    switch (groupExpertise) {
                                       case HIGH: {
                                          FactorValue teamCompetence = getFactorValue(SituationalFactor.TEAM_COMPETENCE);
                                          switch (teamCompetence) {
                                             case HIGH:
                                                return LeadershipStyle.DELEGATE;
                                             case LOW:
                                                return LeadershipStyle.CONSULT_INDIVIDUALLY;
                                          }
                                       }
                                       case LOW:
                                          return LeadershipStyle.CONSULT_INDIVIDUALLY;
                                    }
                                 }
                                 case LOW:
                                    return LeadershipStyle.CONSULT_INDIVIDUALLY;
                              }
                           }
                        }
                     }
                  }
               }
               case LOW: {
                  FactorValue leaderExpertise = getFactorValue(SituationalFactor.LEADER_EXPERTISE);
                  switch (leaderExpertise) {
                     case HIGH:
                        return LeadershipStyle.DECIDE;
                     case LOW: {
                        FactorValue goalAlignment = getFactorValue(SituationalFactor.GOAL_ALIGNMENT);
                        switch (goalAlignment) {
                           case HIGH: {
                              FactorValue groupExpertise = getFactorValue(SituationalFactor.GROUP_EXPERTISE);
                              switch (groupExpertise) {
                                 case HIGH: {
                                    FactorValue teamCompetence = getFactorValue(SituationalFactor.TEAM_COMPETENCE);
                                    switch (teamCompetence) {
                                       case HIGH:
                                          return LeadershipStyle.FACILITATE;
                                       case LOW:
                                          return LeadershipStyle.CONSULT_INDIVIDUALLY;
                                    }
                                 }
                                 case LOW:
                                    return LeadershipStyle.CONSULT_INDIVIDUALLY;
                              }
                           }
                           case LOW:
                              return LeadershipStyle.CONSULT_INDIVIDUALLY;
                        }
                     }
                  }
               }
            }
         }
         case LOW: {
            FactorValue importanceOfCommitment = getFactorValue(SituationalFactor.IMPORTANCE_OF_COMMITMENT);
            switch (importanceOfCommitment) {
               case HIGH: {
                  FactorValue likelihoodOfCommitment = getFactorValue(SituationalFactor.LIKELIHOOD_OF_COMMITMENT);
                  switch (likelihoodOfCommitment) {
                     case HIGH:
                        return LeadershipStyle.DECIDE;
                     case LOW: {
                        FactorValue teamCompetence = getFactorValue(SituationalFactor.TEAM_COMPETENCE);
                        switch (teamCompetence) {
                           case HIGH:
                              return LeadershipStyle.DELEGATE;
                           case LOW:
                              return LeadershipStyle.FACILITATE;
                        }
                     }
                  }
               }
               case LOW:
                  return LeadershipStyle.DECIDE;
            }
         }
      }
      return null;
   }

   // Development driven model
   LeadershipStyle developmentDrivenModel() {
      FactorValue decisionSignificance = getFactorValue(SituationalFactor.DECISION_SIGNIFICANCE);
      switch (decisionSignificance) {
         case LOW: {
            FactorValue importanceOfCommitment = getFactorValue(SituationalFactor.IMPORTANCE_OF_COMMITMENT);
            switch (importanceOfCommitment) {
               case LOW: 
                  return LeadershipStyle.DECIDE;
               case HIGH: {
                  FactorValue likelihoodOfCommitment = getFactorValue(SituationalFactor.LIKELIHOOD_OF_COMMITMENT);
                  switch (likelihoodOfCommitment) {
                     case LOW: 
                        return LeadershipStyle.DELEGATE;
                     case HIGH: 
                        return LeadershipStyle.DECIDE;
                  }
               }
            }
         }
         case HIGH: {
            FactorValue importanceOfCommitment = getFactorValue(SituationalFactor.IMPORTANCE_OF_COMMITMENT);
            switch (importanceOfCommitment) {
               case LOW: {
                  FactorValue goalAlignment = getFactorValue(SituationalFactor.GOAL_ALIGNMENT);
                  switch (goalAlignment) {
                     case LOW: 
                        return LeadershipStyle.CONSULT_GROUP;
                     case HIGH: {
                        FactorValue groupExpertise = getFactorValue(SituationalFactor.GROUP_EXPERTISE);
                        switch (groupExpertise) {
                           case LOW: 
                              return LeadershipStyle.CONSULT_GROUP;
                           case HIGH: {
                              FactorValue teamCompetence = getFactorValue(SituationalFactor.TEAM_COMPETENCE);
                              switch (teamCompetence) {
                                 case LOW: 
                                    return LeadershipStyle.FACILITATE;
                                 case HIGH:
                                    return LeadershipStyle.DELEGATE;
                              }
                           }
                        }
                     }
                  }
               }
               case HIGH: {
                  FactorValue likelihoodOfCommitment = getFactorValue(SituationalFactor.LIKELIHOOD_OF_COMMITMENT);
                  switch (likelihoodOfCommitment) {
                     case LOW: {
                        FactorValue goalAlignment = getFactorValue(SituationalFactor.GOAL_ALIGNMENT);
                        switch (goalAlignment) {
                           case LOW: 
                              return LeadershipStyle.CONSULT_GROUP;
                           case HIGH: {
                              FactorValue groupExpertise = getFactorValue(SituationalFactor.GROUP_EXPERTISE);
                              switch (groupExpertise) {
                                 case LOW:
                                    return LeadershipStyle.FACILITATE;
                                 case HIGH: {
                                    FactorValue teamCompetence = getFactorValue(SituationalFactor.TEAM_COMPETENCE);
                                    switch (teamCompetence) {
                                       case LOW:
                                          return LeadershipStyle.FACILITATE;
                                       case HIGH:
                                          return LeadershipStyle.DELEGATE;
                                    }
                                 }
                              }
                           }
                        }
                     }
                     case HIGH: {
                        FactorValue goalAlignment = getFactorValue(SituationalFactor.GOAL_ALIGNMENT);
                        switch (goalAlignment) {
                           case LOW: 
                              return LeadershipStyle.CONSULT_GROUP;
                           case HIGH: {
                              FactorValue groupExpertise = getFactorValue(SituationalFactor.GROUP_EXPERTISE);
                              switch (groupExpertise) {
                                 case LOW: 
                                    return LeadershipStyle.CONSULT_GROUP;
                                 case HIGH: {
                                    FactorValue teamCompetence = getFactorValue(SituationalFactor.TEAM_COMPETENCE);
                                    switch (teamCompetence) {
                                       case LOW:
                                          return LeadershipStyle.FACILITATE;
                                       case HIGH: 
                                          return LeadershipStyle.DELEGATE;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
      return null;
   }
}
