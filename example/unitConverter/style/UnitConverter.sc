UnitConverter {
   xpad = 10 ;
   ypad = 5;
   gap = 20 ;
   foreground := UIManager.getColor("Label.foreground");
   background := UIManager.getColor("Label.background");
   errorLabel {
     foreground = Color.RED;
   }

   // Instead of using the PanelStyle and ComponentStyle interfaces we can also wire up properties directly
   converterChoice {
     foreground := UnitConverter.this.foreground;
     background := UnitConverter.this.background;
   }

   unit1Label {
     foreground := UnitConverter.this.foreground;
   }
   unit2Label {
     foreground := UnitConverter.this.foreground;
   }

   unit1Field {
     foreground := UnitConverter.this.foreground;
     background := brighter(UnitConverter.this.background);
   }
   unit2Field {
     foreground := UnitConverter.this.foreground;
     background := brighter(UnitConverter.this.background);
   }

   // Due to a limitation in data binding, we can't use the .brighter method directly and be notified of changes
   static Color brighter(Color c) {
     return c.brighter();   
   }
}
