/**
 * A QuizWebPage which has an associated QuizDataManager, used to
 * access quiz data.  
 */
public class DataManagedWebPage extends QuizWebPage {
   object dataManager extends QuizDataManager {
   }  
   
   /**
    * Sets the page that will respond to this request.  Shuts down the
    * data manager when exiting this page.
    */
   @Override
   public <C extends Page> void gotoPage(Class<C> cls) {
      super.gotoPage(cls);
      dataManager.shutdown();
   }

   /**
    * Sets the page that will respond to this request.  Shuts down the
    * data manager when exiting this page.
    */
   @Override
   public void gotoPage(Page page) {
      super.gotoPage(page);
      dataManager.shutdown();
   }
}