UnitConverter extends CWebPage {
   // Converts the value strings to/from numbers.  numberToString to defines
   // a reverse method so it can be used in 2-way bindings.
   transient object numberConverter extends sc.util.NumberConverter {
   }

   // Wicket requires this of model objects
   Converter implements java.io.Serializable {
   }

   int currentConverterIndex = 0;
   Converter currentConverter = converters.get(currentConverterIndex);

   object feedback extends FeedbackPanel {
      outputMarkupId = true;
   }

   object unitConverterForm extends CForm<UnitConverter> {
      object converterChoice extends CDropDownChoice {
         choiceValues = converters;
         currentValue :=: currentConverter;
         selectionChangedNotifications = true;
      }

      object unit1Label extends CLabel {
         textValue := currentConverter.unit1;
      }
      object unit1Field extends CTextField<Double> {
         required = true;
         type = Double.class;
         fieldValue :=: currentConverter.value1;
      }
      object unit2Label extends CLabel {
         textValue := currentConverter.unit2;
      }
      object unit2Field extends CTextField<Double> {
         required = true;
         type = Double.class;
         fieldValue :=: currentConverter.value2;
      }
   }

   public UnitConverter() {
   }
}
