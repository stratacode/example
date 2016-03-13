UnitConverter extends WebPage {
   // Converts the value strings to/from numbers.  numberToString to defines
   // a reverse method so it can be used in 2-way bindings.
   transient object numberConverter extends sc.util.NumberConverter {
   }

   // Wicket requires this of model objects
   Converter implements java.io.Serializable {
   }

   int currentConverterIndex = 0;
   Converter currentConverter = converters.get(currentConverterIndex);

   class UCTextField extends CTextField<Double> {
      required = true;
      type = Double.class;
      changeEvent = "onkeyup";
      throttleDelay = Duration.milliseconds(200);
   }

   class UCLabel extends CLabel {
      outputMarkupId = true;
   }

   object feedback extends FeedbackPanel {
      outputMarkupId = true;
   }

   object unitConverterForm extends CForm<UnitConverter> {
      outputMarkupId = true;

      object converterChoice extends CDropDownChoice {
         changeEvent = "onchange";
         choiceValues = converters;
         currentValue :=: currentConverter;
      }

      object unit1Label extends UCLabel {
         textValue := currentConverter.unit1;
      }
      object unit1Field extends UCTextField {
         fieldValue :=: currentConverter.value1;
      }
      object unit2Label extends UCLabel {
         textValue := currentConverter.unit2;
      }
      object unit2Field extends UCTextField {
         fieldValue :=: currentConverter.value2;
      }
   }

}
