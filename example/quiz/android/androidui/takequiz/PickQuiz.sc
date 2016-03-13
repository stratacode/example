/**
 * The top level Activity which lists all the available quiz names,
 * allowing the user to pick one.
 */
public class PickQuiz extends ListActivity implements QuizConstants {
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setListAdapter(createQuizNamesListAdapter());
   }

   @Override
   protected void onListItemClick(ListView l, View v, int position, long id) {
      super.onListItemClick(l, v, position, id);
      startActivity(createTakeQuizIntent(l, v, position, id));
   }

   /**
    * Returns a new ListAdapter which supplies all the available quiz
    * names.  The quiz names are obtained from the static QuizData
    * object.
    */
   ListAdapter createQuizNamesListAdapter() {
      return new ArrayAdapter<String>
	 (this, android.R.layout.simple_list_item_1, QuizData.allQuizNames);
   }

   /**
    * Creates a new Intent in response to an item in the ListView
    * being clicked.  Passes the quiz name into the Intent.
    */
   Intent createTakeQuizIntent(ListView l, View v, int position, long id) {
      String quizName = (String) l.getItemAtPosition(position);
      Intent intent = new Intent(this, TakeQuiz.class);
      intent.putExtra(KEY_QUIZ_NAME, quizName);
      return intent;
   }
}
