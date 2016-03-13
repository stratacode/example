PickQuiz {
   /**
    * Returns a new ListAdapter which supplies all the available quiz
    * names.  The quiz names are obtained from the quiz content
    * provider.
    */
   ListAdapter createQuizNamesListAdapter() {
      // Perform a managed query. The Activity will handle closing and
      // requerying the cursor when needed.
      Cursor cursor = managedQuery(Quizes.CONTENT_URI, null, null, null, null);
      return new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor,
				     new String[] { Quizes.NAME }, new int[] { android.R.id.text1 });
   }

   /**
    * Creates a new Intent in response to an item in the ListView
    * being clicked.  Passes the quiz URI into the Intent.
    */
   Intent createTakeQuizIntent(ListView l, View v, int position, long id) {
      Uri uri = ContentUris.withAppendedId(Quizes.CONTENT_URI, id);
      return new Intent(null, uri, this, TakeQuiz.class);
   }
}