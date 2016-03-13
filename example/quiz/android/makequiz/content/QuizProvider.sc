/**
 * Provides access to a database of quizes and their questions.
 */
public class QuizProvider extends ContentProvider {
   private static final String TAG = "QuizProvider";

   private static final String DATABASE_NAME = "quiz.db";
   private static final int DATABASE_VERSION = 1;

   private static final String QUIZ_TABLE_NAME = "quiz";
   private static final String QUESTION_TABLE_NAME = "question";

   private static final int QUIZES = 1;
   private static final int QUIZ_ID = 2;
   private static final int QUESTIONS = 3;
   private static final int QUESTION_ID = 4;

   private static final UriMatcher uriMatcher;
   private static Map<String, String> quizProjectionMap;
   private static Map<String, String> questionProjectionMap;

   static {
      uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
      uriMatcher.addURI(QuizContent.AUTHORITY, "quizes", QUIZES);
      uriMatcher.addURI(QuizContent.AUTHORITY, "quizes/#", QUIZ_ID);
      uriMatcher.addURI(QuizContent.AUTHORITY, "quizes/#/questions", QUESTIONS);
      uriMatcher.addURI(QuizContent.AUTHORITY, "quizes/#/questions/#", QUESTION_ID);

      quizProjectionMap = new HashMap<String, String>();
      quizProjectionMap.put(Quizes._ID, Quizes._ID);
      quizProjectionMap.put(Quizes.NAME, Quizes.NAME);

      questionProjectionMap = new HashMap<String, String>();
      questionProjectionMap.put(Questions._ID, Questions._ID);
      questionProjectionMap.put(Questions.QUIZ_ID, Questions.QUIZ_ID);
      questionProjectionMap.put(Questions.QUESTION, Questions.QUESTION);
      questionProjectionMap.put(Questions.ANSWER_CHOICES, Questions.ANSWER_CHOICES);
      questionProjectionMap.put(Questions.ANSWER_INDEX, Questions.ANSWER_INDEX);
      questionProjectionMap.put(Questions.ANSWER_DETAIL, Questions.ANSWER_DETAIL);
   }

   /**
    * This class helps open, create, and upgrade the database file.
    */
   private static class DatabaseHelper extends SQLiteOpenHelper {
      DatabaseHelper(Context context) {
	 super(context, DATABASE_NAME, null, DATABASE_VERSION);
      }

      public void onCreate(SQLiteDatabase db) {
	 db.execSQL("CREATE TABLE " + QUIZ_TABLE_NAME + " ("
                    + Quizes._ID + " INTEGER PRIMARY KEY,"
                    + Quizes.NAME + " TEXT);");
	 db.execSQL("CREATE TABLE " + QUESTION_TABLE_NAME + " ("
                    + Questions._ID + " INTEGER PRIMARY KEY,"
                    + Questions.QUIZ_ID + " INTEGER,"
                    + Questions.QUESTION + " TEXT,"
                    + Questions.ANSWER_CHOICES + " BLOB,"
                    + Questions.ANSWER_INDEX + " INTEGER,"
                    + Questions.ANSWER_DETAIL + " TEXT,"
		    + "FOREIGN KEY(" + Questions.QUIZ_ID + ") "
		    + "REFERENCES " + QUIZ_TABLE_NAME + "(" + Quizes._ID + "));");
      }

      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	 Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
	       + newVersion + ", which will destroy all old data");
	 db.execSQL("DROP TABLE IF EXISTS " + QUESTION_TABLE_NAME);
	 db.execSQL("DROP TABLE IF EXISTS " + QUIZ_TABLE_NAME);
	 onCreate(db);
      }
   }

   private DatabaseHelper dbHelper;

   @Override
   public boolean onCreate() {
      dbHelper = new DatabaseHelper(getContext());
      return true;
   }

   @Override
   public String getType(Uri uri) {
      switch (uriMatcher.match(uri)) {
      case QUIZES:
	 return Quizes.CONTENT_TYPE;
      case QUIZ_ID:
	 return Quizes.CONTENT_ITEM_TYPE;
      case QUESTIONS:
	 return Questions.CONTENT_TYPE;
      case QUESTION_ID:
	 return Questions.CONTENT_ITEM_TYPE;
      default:
	 throw new IllegalArgumentException("Invalid URI " + uri);
      }
   }

   @Override
   public Uri insert(Uri uri, ContentValues values) {
      String tableName, nullColumnName;

      switch (uriMatcher.match(uri)) {
      case QUIZES:
	 tableName = QUIZ_TABLE_NAME;
	 nullColumnName = Quizes.NAME;
	 break;
      case QUESTIONS: 
	 tableName = QUESTION_TABLE_NAME;
	 nullColumnName = Questions.QUESTION;
	 values = new ContentValues(values);
	 values.put(Questions.QUIZ_ID, uri.getPathSegments().get(1));
	 break;
      default:
	 throw new IllegalArgumentException("Invalid URI " + uri);
      }

      SQLiteDatabase db = dbHelper.getWritableDatabase();
      long rowId = db.insert(tableName, nullColumnName, values);
      if (rowId > 0) {
	 Uri insertedUri = ContentUris.withAppendedId(uri, rowId);
	 getContext().getContentResolver().notifyChange(insertedUri, null);
	 return insertedUri;
      }
      throw new SQLException("Failed to insert row into " + uri);
   }

   @Override
   public Cursor query(Uri uri, String[] projection, 
		       String selection, String[] selectionArgs, String sortOrder) 
   {
      SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

      switch (uriMatcher.match(uri)) {
      case QUIZ_ID:
	 qb.appendWhere(Quizes._ID + "=" + uri.getPathSegments().get(1));
      case QUIZES:
	 qb.setTables(QUIZ_TABLE_NAME);
	 qb.setProjectionMap(quizProjectionMap);
	 break;

      case QUESTION_ID:
	 qb.appendWhere(Questions._ID + "=" + uri.getPathSegments().get(3) + " AND ");
      case QUESTIONS:
	 qb.setTables(QUESTION_TABLE_NAME);
	 qb.setProjectionMap(questionProjectionMap);
	 qb.appendWhere(Questions.QUIZ_ID + "=" + uri.getPathSegments().get(1));
	 break;

      default:
	 throw new IllegalArgumentException("Invalid URI " + uri);
      }

      SQLiteDatabase db = dbHelper.getReadableDatabase();
      Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);

      // Tell the cursor what uri to watch, so it knows when its source data changes
      cursor.setNotificationUri(getContext().getContentResolver(), uri);
      return cursor;
    }

   @Override
   public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
      String tableName;
      
      switch (uriMatcher.match(uri)) {
      case QUIZ_ID:
	 where = Quizes._ID + "=" + uri.getPathSegments().get(1) + 
	    (!StringUtil.isEmpty(where) ? " AND (" + where + ")" : "");
      case QUIZES:
	 tableName = QUIZ_TABLE_NAME;
	 break;
	 
      case QUESTION_ID:
	 where = Questions._ID + "=" + uri.getPathSegments().get(3) + 
	    " AND " + Questions.QUIZ_ID + "=" + uri.getPathSegments().get(1) + 
	    (!StringUtil.isEmpty(where) ? " AND (" + where + ")" : "");
      case QUESTIONS:
	 tableName = QUESTION_TABLE_NAME;
	 break;
	 
      default:
	 throw new IllegalArgumentException("Invalid URI " + uri);
      }
      
      SQLiteDatabase db = dbHelper.getWritableDatabase();
      int count = db.update(tableName, values, where, whereArgs);
      getContext().getContentResolver().notifyChange(uri, null);
      return count;
   }

   @Override
   public int delete(Uri uri, String where, String[] whereArgs) {
      String tableName;

      switch (uriMatcher.match(uri)) {
      case QUIZ_ID:
	 where = Quizes._ID + "=" + uri.getPathSegments().get(1) + 
	    (!StringUtil.isEmpty(where) ? " AND (" + where + ")" : "");
      case QUIZES:
	 tableName = QUIZ_TABLE_NAME;
	 break;

      case QUESTION_ID:
	 where = Questions._ID + "=" + uri.getPathSegments().get(3) + 
	    " AND " + Questions.QUIZ_ID + "=" + uri.getPathSegments().get(1) + 
	    (!StringUtil.isEmpty(where) ? " AND (" + where + ")" : "");
      case QUESTIONS:
	 tableName = QUESTION_TABLE_NAME;
	 break;

      default:
	 throw new IllegalArgumentException("Invalid URI " + uri);
      }

      SQLiteDatabase db = dbHelper.getWritableDatabase();
      int count = db.delete(tableName, where, whereArgs);
      getContext().getContentResolver().notifyChange(uri, null);
      return count;
   }
}
