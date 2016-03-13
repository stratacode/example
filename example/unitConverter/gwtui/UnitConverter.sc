@GWTModule 
UnitConverter {
   // Converts the value strings to/from numbers.  numberToString to defines
   // a reverse method so it can be used in 2-way bindings.
   object numberConverter extends sc.util.NumberConverter {
   }

   object mainView extends VerticalPanel {
      // Combo box to choose the current conversion algorithm
      object converterChoice extends CListBox {
         items := converters;
         visibleItemCount = 1; // Turns a list into a drop-down box
         selectedIndex = 0;
      }

      // currentConverter will always point to the selected item in the combo box
      Converter currentConverter := converters.get(converterChoice.selectedIndex);

      object unit1Panel extends FlowPanel {
         object unit1Label extends InlineLabel {
            text := currentConverter.unit1;  // Display's converter's "unit 1"
         }
         object unit1Field extends CTextBox {
            // Bind's text property to current converter's value1 after converter to/from string
            text :=: numberConverter.numberToString(currentConverter.value1);
         }
      }

      object unit2Panel extends FlowPanel {
         object unit2Label extends InlineLabel {
            text := currentConverter.unit2;
         }
         object unit2Field extends CTextBox {
            text :=: numberConverter.numberToString(currentConverter.value2);
         }
      }

      object errorLabel extends Label {
         // The number converter provides an error when an invalid number is supplied
         text := numberConverter.error;
      }
   }
}
