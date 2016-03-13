/**
 * A RuntimeException used by the ContentUtil class.
 */
public class ContentException extends RuntimeException {
   public ContentException() {
   }
   
   public ContentException(String message) {
      super(message);
   }
   
   public ContentException(Throwable throwable) {
      super(throwable);
   }
   
   public ContentException(String message, Throwable throwable) {
      super(message, throwable);
   }
}

