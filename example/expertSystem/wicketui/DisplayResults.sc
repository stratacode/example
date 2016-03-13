DisplayResults extends CWebPage {
   object displayResultsForm extends CForm<DisplayResults> {
      object timeDrivenDisplayNameLabel extends CLabel {
         textValue := timeDrivenDecision.displayName;
      }
      object timeDrivenDescriptionLabel extends CLabel {
         textValue := timeDrivenDecision.description;
      }
      object developmentDrivenDisplayNameLabel extends CLabel {
         textValue := developmentDrivenDecision.displayName;
      }
      object developmentDrivenDescriptionLabel extends CLabel {
         textValue := developmentDrivenDecision.description;
      }
      object doneButton extends Button {
	 public void onSubmit() {
	    setResponsePage(AnalyzeSituation.class);
	 }
      } 
   }
}
