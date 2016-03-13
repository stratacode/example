UnitConverter {
   // Converts the value strings to/from numbers.  numberToString to defines
   // a reverse method so it can be used in 2-way bindings.
   object numberConverter extends sc.util.NumberConverter {
   }

   int currentConverterIndex = 0;
   // currentConverter will always point to the selected item in the combo box
   Converter currentConverter := converters.get(currentConverterIndex);
}
