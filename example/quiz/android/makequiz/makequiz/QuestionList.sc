/**
 * An Activity which lists all the existing questions for a given
 * quiz.  Each question can be either edited or deleted, and new
 * questions can be created.
 */
public class QuestionList extends ListActivity implements QuizConstants {
   private static final String TAG = "QuestionList";

   private static final String[] PROJECTION = new String[] { Questions._ID, Questions.QUESTION };

   // Menu item ids
   private static final int MENU_ITEM_INSERT = Menu.FIRST;
   private static final int MENU_ITEM_EDIT = Menu.FIRST + 1;
   private static final int MENU_ITEM_DELETE = Menu.FIRST + 2;

   // Used to inflate layout XML files into View objects
   LayoutInflater layoutInflater;

   // Base URI for this quiz's questions
   Uri questionsUri;

   // We need to supply our own layout here because we specify a
   // custom empty view, to be displayed when no questions exist.
   object mainView extends LinearLayout {
      layoutParams = new LinearLayout.LayoutParams(FILL_PARENT, FILL_PARENT);
      object questionListView extends CListView {
	 id = android.R.id.list;
	 layoutParams = new LinearLayout.LayoutParams(FILL_PARENT, WRAP_CONTENT);
      }
      object noQuestionsView extends CTextView {
	 id = android.R.id.empty;
	 textSize = 20;
	 layoutParams = new LinearLayout.LayoutParams(FILL_PARENT, WRAP_CONTENT);
	 { setText(R.string.no_questions);
	   setPadding(5,5,5,0); }
      }
   }
      
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      layoutInflater = LayoutInflater.from(this);

      // Set the title of the window to reflect the quiz name
      Bundle extras = intent.getExtras();
      String quizName = extras.getString(KEY_QUIZ_NAME);
      setTitle(quizName);

      // The quiz URI is passed in with the Intent
      Uri quizUri = getIntent().getData();
      questionsUri = Uri.withAppendedPath(quizUri, Questions.URI_PATH_SEGMENT);

      // Perform a managed query. The Activity will handle closing and
      // requerying the cursor when needed.
      Cursor cursor = managedQuery(questionsUri, PROJECTION, null, null, null);

      // Use a custom list item layout, which constrains the text to a
      // single line, and puts ellipsis at the end.
      setListAdapter(new SimpleCursorAdapter
		     (this, R.layout.list_item, cursor,
		      new String[] { Questions.QUESTION }, new int[] { R.id.text1 }));

      // Inform the list we provide context menus for items
      registerForContextMenu(getListView());
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      boolean result = super.onCreateOptionsMenu(menu);

      // Add a single menu item to insert a new question
      menu.add(Menu.NONE, MENU_ITEM_INSERT, Menu.NONE, R.string.menu_insert_question)
	 .setIcon(android.R.drawable.ic_menu_add);

      return result;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case MENU_ITEM_INSERT:
	 // Launch activity to create a new question
	 Intent intent = new Intent(Intent.ACTION_INSERT, questionsUri, this, EditQuestion.class);
	 intent.putExtra(KEY_QUESTION_INDEX, getListAdapter().getCount());
	 startActivity(intent);
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

      // Set the context menu's header to the selected question text.
      // Just for kicks, we use a custom menu header here: the
      // standard one allows the title to wrap onto the second line,
      // and we just want to keep it single-line.
      String question = cursor.getString(cursor.getColumnIndex(Questions.QUESTION));
      View headerView = layoutInflater.inflate(R.layout.context_menu_header, null);
      TextView titleView = (TextView) headerView.findViewById(R.id.title);      
      titleView.setText(question);
      menu.setHeaderView(headerView);

      // Add menu items to edit and delete the question
      menu.add(Menu.NONE, MENU_ITEM_EDIT, Menu.NONE, R.string.menu_edit_question);
      menu.add(Menu.NONE, MENU_ITEM_DELETE, Menu.NONE, R.string.menu_delete_question);
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

      final Uri questionUri = ContentUris.withAppendedId(questionsUri, info.id);

      switch (item.getItemId()) {
      case MENU_ITEM_EDIT:
	 // Launch activity to edit the selected question
	 Intent intent = new Intent(Intent.ACTION_EDIT, questionUri, this, EditQuestion.class);
	 intent.putExtra(KEY_QUESTION_INDEX, info.position);
	 startActivity(intent);
	 return true;
      case MENU_ITEM_DELETE:
	 // Delete the question that the context menu is for
	 DialogUtil.showConfirmDialog
	    (this, "Delete Question", "This question will be deleted.", 
	     new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int id) {
		   getContentResolver().delete(questionUri, null, null);
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

      // Launch activity to edit the selected question
      Uri questionUri = ContentUris.withAppendedId(questionsUri, id);
      Intent intent = new Intent(Intent.ACTION_EDIT, questionUri, this, EditQuestion.class);
      intent.putExtra(KEY_QUESTION_INDEX, position);
      startActivity(intent);
   }
}
