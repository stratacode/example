/**
 * Dialog utilities.
 */
public class DialogUtil {
   /**
    * Displays an error dialog for the given Context, with the given
    * title and message, and a single OK button.
    */
   public static void showErrorDialog(Context context, String title, String message) {
      showErrorDialog(context, title, message, null);
   }

   /**
    * Displays an error dialog for the given Context, with the given
    * title, message, and a single OK button, with the given
    * listener.
    */
   public static void showErrorDialog(Context context, String title, String message,
				      DialogInterface.OnClickListener listener) 
   {
      showOkDialog(context, android.R.drawable.ic_dialog_alert, title, message, listener);
   }

   /**
    * Displays an info dialog for the given Context, with the given
    * title and message, and a single OK button.
    */
   public static void showInfoDialog(Context context, String title, String message) {
      showInfoDialog(context, title, message, null);
   }
   
   /**
    * Displays an info dialog for the given Context, with the given
    * title, message, and a single OK button, with the given
    * listener.
    */
   public static void showInfoDialog(Context context, String title, String message,
				      DialogInterface.OnClickListener listener) 
   {
      showOkDialog(context, android.R.drawable.ic_dialog_info, title, message, listener);
   }

   /**
    * Displays an AlertDialog for the given Context, with the given
    * title and message.  The dialog has a single OK button, with the
    * given listener.
    */
   public static void showOkDialog(Context context, int icon, String title, String message, 
				   DialogInterface.OnClickListener listener) 
   {
      new AlertDialog.Builder(context)
	 .setMessage(message)
	 .setTitle(title)
	 .setIcon(icon)
	 .setPositiveButton("OK", listener)
	 .show();
   }

   /**
    * Displays a confirmation dialog for the given Context, with the
    * given title and message.  The dialog has two buttons, OK and
    * Cancel, with the given listeners.
    */
   public static void showConfirmDialog(Context context, String title, String message, 
					DialogInterface.OnClickListener okListener,
					DialogInterface.OnClickListener cancelListener) 
   {
      showOkCancelDialog(context, android.R.drawable.ic_dialog_alert, title, message,
			 okListener, cancelListener);
   }

   /**
    * Displays an AlertDialog for the given Context, with the given
    * title and message.  The dialog has two buttons, OK and Cancel,
    * with the given listeners.
    */
   public static void showOkCancelDialog(Context context, int icon, String title, String message, 
					 DialogInterface.OnClickListener okListener,
					 DialogInterface.OnClickListener cancelListener) 
   {
      new AlertDialog.Builder(context)
	 .setMessage(message)
	 .setTitle(title)
	 .setIcon(icon)
	 .setPositiveButton("OK", okListener)
	 .setNegativeButton("Cancel", cancelListener)
	 .show();
   }
}
