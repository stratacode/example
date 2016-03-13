AnalyzeSituation extends CWebPage {
   object analyzeFactorForm extends CForm<AnalyzeSituation> {
      // Display information about the current factor
      object factorDisplayNameLabel extends CLabel {
         textValue := currentFactor.displayName;
      }
      object factorShortDescriptionLabel extends CLabel {
         textValue := currentFactor.shortDescription;
      }
      object factorLongDescriptionLabel extends CLabel {
         textValue := currentFactor.longDescription;
      }

      // Display a group of factor value choices
      object factorValueChoiceButtons extends CRadioGroup<FactorValue> {
         required = true;
         object factorValueChoices extends CListView<FactorValue> {
            list = Arrays.asList(FactorValue.values());

            scope<ListItem> object factorValueChoice implements Serializable {
               object factorValueChoiceRadio extends CRadio<FactorValue> {
                  choiceValue = listItemValue;
               }
               object factorValueChoiceText extends CLabel {
                  textValue := listItemValue.displayName;
               }
            }
         }
      }

      // When Next button is pressed, record the value for the current
      // factor, and iterate to the next factor
      object nextButton extends Button {
         visible := (factorIndex < numFactors-1);
         public void onSubmit() {
            if (!hasError()) {
	           FactorValue factorValue = factorValueChoiceButtons.currentValue;
               setSituationFactor(factorValue);
               factorValueChoiceButtons.clearSelection();
	        }
	     }
      }

      // When Done button is pressed, go to the DisplayResults page
      object doneButton extends Button {
         visible := (factorIndex == numFactors-1);
	     public void onSubmit() {
	        if (!hasError()) {
	           FactorValue factorValue = factorValueChoiceButtons.currentValue;
	           situation.setFactorValue(currentFactor, factorValue);
	           setResponsePage(new DisplayResults(situation));
	        }
	     }
      }      
   }

   // The error message panel
   object feedback extends FeedbackPanel {
   }
}
