import java.util.Arrays;

import sc.dyn.DynUtil;
import sc.util.ArrayUtil;
import sc.util.StringUtil;

AnalyzeSituation extends JPanel implements PanelStyle {
   int textWidth = 7;
   int textAreaPad = 10;
   int lineWidth := (int) (size.width / textWidth);

   LeadershipStyle timeDrivenDecision;
   LeadershipStyle developmentDrivenDecision;

   // Display information about the current factor
   object factorDisplayNameLabel extends JLabel {
      text :="<html><font size='+1'>" + currentFactor.displayName + "</font></html>" ;
      location := SwingUtil.point(xpad, ypad);
      size := preferredSize;
   }
   object factorShortDescriptionLabel extends JLabel {
      text :="<html><font size='+0'>" + currentFactor.shortDescription ;
      location := SwingUtil.point(xpad, factorDisplayNameLabel.location.y + factorDisplayNameLabel.size.height + ypad);
      size := preferredSize;
   }
   object factorLongDescriptionLabel extends JTextArea {
      text := StringUtil.insertLinebreaks(currentFactor.longDescription, lineWidth);
      location := SwingUtil.point(xpad, factorShortDescriptionLabel.location.y + factorShortDescriptionLabel.size.height + ypad);
      size := SwingUtil.dimension(preferredSize.width + textAreaPad, preferredSize.height);
      editable = false;

      foreground := factorDisplayNameLabel.foreground;
      background := factorDisplayNameLabel.background;
      // Now doing line breaks via the insertLinebreaks method because then we can dynamically compute the size
      //lineWrap = true;
      //wrapStyleWord = true;
   }

   object factorButtonGroup extends ButtonGroup {
      // Pulls out the first element - the factorValueChoiceRadio - from each element in the ListView's factorValueChoice component list
      // TODO: why not put the scope<ListItem> on the JRadioButton to save the tricky level of indirection?  I would rewrite it but like
      // the fact that we test a binding expr of this complexity.
      buttons := (List<AbstractButton>) Arrays.asList(DynUtil.getObjChildrenArray2DRange(ArrayUtil.listToArray(factorValueChoices.componentList), null, 0, 1));
   }

   object factorValueChoices extends ListView<FactorValue> {
      values = Arrays.asList(FactorValue.values());
      int choiceStartY := (int) (factorLongDescriptionLabel.location.y + factorLongDescriptionLabel.size.height + ypad);

      scope<ListItem> object factorValueChoice {
         object factorValueChoiceRadio extends JRadioButton {
            location := SwingUtil.point(xpad, listItemPrev == null ? choiceStartY : (int) (listItemPrev.factorValueChoiceRadio.location.y + listItemPrev.factorValueChoiceRadio.size.height + ypad));
            size := preferredSize;
            text := listItemValue.displayName;
         }
      }
   }

   factorValueChoices.factorValueChoice.factorValueChoiceRadio lastRadioButton := ((factorValueChoices.factorValueChoice) factorValueChoices.lastComponent).factorValueChoiceRadio;
   int lastListY := (int) (lastRadioButton.location.y + lastRadioButton.size.height);

   // When Next button is pressed, record the value for the current
   // factor, and iterate to the next factor
   object nextButton extends JButton {
      visible := (factorIndex < numFactors-1);
      location := SwingUtil.point(xpad, lastListY + ypad);
      size := preferredSize;
      text = "Next";

      clickCount =: doNext();
   
      void doNext() {
         int selectedIndex = factorButtonGroup.selectedIndex;
         FactorValue factorValue;
         if (selectedIndex != -1) {
            factorValue = FactorValue.values()[selectedIndex];
            situationFactor = factorValue;
         }
      }      
   }

   void setSituationFactor(FactorValue value) {
      super.setSituationFactor(value);
      factorButtonGroup.clearSelection();
   }

   object doneButton extends JButton {
      visible := (factorIndex == numFactors-1);
      location := SwingUtil.point(xpad, lastListY + ypad);
      size := preferredSize;
      text = "Done";

      clickCount =: doDone();

      void doDone() {
         int selectedIndex = factorButtonGroup.selectedIndex;
         FactorValue factorValue;
         if (selectedIndex != -1) {
            factorValue = FactorValue.values()[selectedIndex];
            setSituationFactor(factorValue);
            answersComplete = true;

            timeDrivenDecision = situation.timeDrivenModel();
            developmentDrivenDecision = situation.developmentDrivenModel();
         }
      }
   }

   void resetSituation() {
      super.resetSituation();
      factorButtonGroup.clearSelection();
      timeDrivenDecision = null;
      developmentDrivenDecision = null;
   }
}
