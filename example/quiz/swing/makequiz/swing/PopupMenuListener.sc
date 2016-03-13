/**
 * A MouseAdapter which listens for mouse events, and brings up a
 * popup menu when necessary.
 */
public class PopupMenuListener extends MouseAdapter {
   JPopupMenu popupMenu;

   public void mousePressed(MouseEvent event) {
      showPopupMenu(event);
   }
   
   public void mouseReleased(MouseEvent event) {
      showPopupMenu(event);
   }

   void showPopupMenu(MouseEvent event) {
      if (event.isPopupTrigger())
	 popupMenu.show(event.component, event.x, event.y);
   }
}