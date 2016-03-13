/**
 * A WebPage class used by all quiz pages.
 */
public class QuizWebPage extends WebPage {
   /**
    * Sets the page that will respond to this request.  Unlike
    * setResponsePage, this method is not final and can be overriden.
    */
   public <C extends Page> void gotoPage(Class<C> cls) {
      setResponsePage(cls);
   }

   /**
    * Sets the page that will respond to this request.  Unlike
    * setResponsePage, this method is not final and can be overriden.
    */
   public void gotoPage(Page page) {
      setResponsePage(page);
   }
}