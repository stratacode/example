/**
 * The top level Activity which lists all the existing quiz names.
 * Each quiz can be either edited or deleted, and new quizes can be
 * created.
 */
public class QuizList extends ListActivity implements QuizConstants {
   private static final String TAG = "QuizList";

   // Menu item ids
   private static final int MENU_ITEM_INSERT = Menu.FIRST;
   private static final int MENU_ITEM_EDIT = Menu.FIRST + 1;
   private static final int MENU_ITEM_DELETE = Menu.FIRST + 2;

   // We need to supply our own layout here because we specify a
   // custom empty view, to be displayed when no quizes exist.
   object mainView extends LinearLayout {
      layoutParams = new LinearLayout.LayoutParams(FILL_PARENT, FILL_PARENT);
      object quizListView extends CListView {
	 id = android.R.id.list;
	 layoutParams = new LinearLayout.LayoutParams(FILL_PARENT, WRAP_CONTENT);
      }
      object noQuizesView extends CTextView {
	 id = android.R.id.empty;
	 textSize = 20;
	 layoutParams = new LinearLayout.LayoutParams(FILL_PARENT, WRAP_CONTENT);
	 { setText(R.string.no_quizes);
	   setPadding(5,5,5,0); }
      }
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // Perform a managed query. The Activity will handle closing and
      // requerying the cursor when needed.
      Cursor cursor = managedQuery(Quizes.CONTENT_URI, null, null, null, null);
      setListAdapter(new SimpleCursorAdapter
		     (this, android.R.layout.simple_list_item_1, cursor,
		      new String[] { Quizes.NAME }, new int[] { android.R.id.text1 }));
      
      // Inform the list we provide context menus for items
      registerForContextMenu(getListView());
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      boolean result = super.onCreateOptionsMenu(menu);

      // Add a single menu item to insert a new quiz
      menu.add(Menu.NONE, MENU_ITEM_INSERT, Menu.NONE, R.string.menu_insert_quiz)
	 .setIcon(android.R.drawable.ic_menu_add);

      return result;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case MENU_ITEM_INSERT:
	 // Launch activity to insert a new quiz
	 startActivity(new Intent(this, NameQuiz.class));
	 return true;
      }
      return super.onOptionsItemSelected(item);
   }

   @Override
   public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
      super.onCreateContextMenu(menu, view, menuInfo);

      AdapterView.AdapterContextMenuInfo info;
      try {
	 info = (AdapterView.AdapterContextMenuInfo) menuInfo;
      } catch (ClassCastException e) {
	 Log.e(TAG, "Invalid ContextMenuInfo", e);
	 return;
      }
      Cursor cursor = (Cursor) getListAdapter().getItem(info.position);
      if (cursor == null)
	 return;

      // Set the context menu's header to the selected quiz name
      menu.setHeaderTitle(cursor.getString(cursor.getColumnIndex(Quizes.NAME)));

      // Add menu items to edit and delete the quiz
      menu.add(Menu.NONE, MENU_ITEM_EDIT, Menu.NONE, R.string.menu_edit_quiz);
      menu.add(Menu.NONE, MENU_ITEM_DELETE, Menu.NONE, R.string.menu_delete_quiz);
   }
      
   @Override
   public boolean onContextItemSelected(MenuItem item) {
      AdapterView.AdapterContextMenuInfo info;
      try {
	 info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
      } catch (ClassCastException e) {
	 Log.e(TAG, "Invalid ContextMenuInfo", e);
	 return false;
      }

      final Uri quizUri = ContentUris.withAppendedId(Quizes.CONTENT_URI, info.id);

      switch (item.getItemId()) {
      case MENU_ITEM_EDIT:
	 // Launch activity to edit the selected quiz
	 Intent intent = new Intent(null, quizUri, this, QuestionList.class);
	 Cursor cursor = (Cursor) getListAdapter().getItem(info.position);
	 intent.putExtra(KEY_QUIZ_NAME, cursor.getString(cursor.getColumnIndex(Quizes.NAME)));
	 startActivity(intent);
	 return true;
      case MENU_ITEM_DELETE:
	 // Delete the quiz that the context menu is for
	 DialogUtil.showConfirmDialog
	    (this, "Delete Quiz", "This quiz will be deleted.", 
	     new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int id) {
		   getContentResolver().delete(quizUri, null, null);
		}
	     }, 
	     null);
	 return true;
      }
      return false;
   }

   @Override
   protected void onListItemClick(ListView l, View v, int position, long id) {
      super.onListItemClick(l, v, position, id);

      // Launch activity to edit the selected quiz
      Uri quizUri = ContentUris.withAppendedId(Quizes.CONTENT_URI, id);
      Intent intent = new Intent(null, quizUri, this, QuestionList.class);
      Cursor cursor = (Cursor) l.getItemAtPosition(position);
      intent.putExtra(KEY_QUIZ_NAME, cursor.getString(cursor.getColumnIndex(Quizes.NAME)));
      startActivity(intent);
   }
}
