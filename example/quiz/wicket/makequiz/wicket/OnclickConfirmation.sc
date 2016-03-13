/**
 * An onclick attribute modifier that inserts the javascript needed to
 * pop up a confirmation dialog.
 */
public class OnclickConfirmation extends AbstractBehavior {
   String message;
   
   @Override
   public void onComponentTag(org.apache.wicket.Component component, ComponentTag tag) {
      super.onComponentTag(component, tag);
      String prev = tag.getAttributes().getString("onclick");
      String onclick = "if (!confirm('" + message + "')) return false; " + ((prev == null) ? "" : prev);
      tag.getAttributes().put("onclick", onclick);
   }
}