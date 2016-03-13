// Since model's UnitConverter does not extend anything, we can just redefine this
// type to avoid one level of object containment
UnitConverter extends Activity {
   // Converts the value strings to/from numbers.  numberToString to defines
   // a reverse method so it can be used in 2-way bindings.
   object numberConverter extends NumberConverter {
   }

   object mainView extends LinearLayout {
      orientation = VERTICAL;

      // currentConverter will always point to the selected item in the combo box
      Converter currentConverter := converters.get(converterChoice.selectedItemPosition);

      // Combo box to choose the current conversion algorithm
      object converterChoice extends CSpinner {
         adapter = new ArrayAdapter<Converter>(UnitConverter.this, android.R.layout.simple_spinner_item, converters);         
      }

      class UnitTextField extends CEditText {
         singleLine = true;
         layoutParams = new LinearLayout.LayoutParams(FILL_PARENT, WRAP_CONTENT, 0);
         inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED;
      }

      class UnitLabel extends CTextView {
         singleLine = true;
         layoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, 0);
         { setPadding(5,0,5,0); }
      }

      object unit1 extends LinearLayout {
         object unit1Label extends UnitLabel {
            textString := currentConverter.unit1;  // Display's converter's "unit 1"
         }
         object unit1Field extends UnitTextField {
            textString :=: numberConverter.numberToString(currentConverter.value1);
         }
      }

      object unit2 extends LinearLayout {
         object unit2Label extends UnitLabel {
            textString := currentConverter.unit2;
         }
         object unit2Field extends UnitTextField {
            textString :=: numberConverter.numberToString(currentConverter.value2);
         }
      }

      object errorLabel extends CTextView {
         // The number converter provides an error when an invalid number is supplied
         textString := numberConverter.error;
         textColor := Color.RED;
      }
   }
}
