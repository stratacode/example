import sc.util.StringUtil;

DisplayResults extends JPanel implements PanelStyle {
   int textWidth = 7;
   int lineWidth := (int) (size.width / textWidth);
   int introTextSize = 5;
   int displayNameTextSize = 5;

   object introLabel extends JLabel {
      text := "<html><font size='" + introTextSize + "'>Your Results:</font></html>";
      location := SwingUtil.point(xpad, ypad);
      size := preferredSize;
   }

   object timeDrivenDisplayNameLabel extends JLabel {
      text :="<html><font size='" + displayNameTextSize + "'>Time Driven Model: <b>" + timeDrivenDecision.displayName + "</b></html>" ;
      location := SwingUtil.point(xpad, ypad + introLabel.size.height + introLabel.location.y);
      size := preferredSize;
   }
   object timeDrivenDescriptionLabel extends JTextArea {
      text := StringUtil.insertLinebreaks(timeDrivenDecision.description, lineWidth);
      location := SwingUtil.point(xpad, ypad + timeDrivenDisplayNameLabel.location.y + timeDrivenDisplayNameLabel.size.height);
      size := SwingUtil.dimension(preferredSize.width + 10, preferredSize.height);
   }
   object developmentDrivenDisplayNameLabel extends JLabel {
      text :="<html><font size='" + displayNameTextSize + "'>Development Driven Model: <b>" + developmentDrivenDecision.displayName + "</b></html>" ;
      location := SwingUtil.point(xpad, ypad + timeDrivenDescriptionLabel.location.y + timeDrivenDescriptionLabel.size.height);
      size := preferredSize;
   }
   object developmentDrivenDescriptionLabel extends JTextArea {
      text := StringUtil.insertLinebreaks(developmentDrivenDecision.description, lineWidth);
      location := SwingUtil.point(xpad, ypad + developmentDrivenDisplayNameLabel.location.y + developmentDrivenDisplayNameLabel.size.height);
      size := SwingUtil.dimension(preferredSize.width + 10, preferredSize.height);
   }
   object doneButton extends JButton {
      text = "Begin New Problem";
      location := SwingUtil.point(xpad, ypad + developmentDrivenDescriptionLabel.location.y + developmentDrivenDescriptionLabel.size.height);
      size := preferredSize;
   }
}
