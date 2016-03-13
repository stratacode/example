/**
 * Manages quiz data via an EntityManager.  Like EntityManager, this
 * class is not thread-safe; a QuizDataManager instance is meant to be
 * used by a single thread.
 */
public class QuizDataManager {
   private static String PERSISTENCE_UNIT = "quizData";

   // A single global entity manager factory
   private static EntityManagerFactory factory = 
      Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

   EntityManager entityManager;

   /**
    * Lazily initializes the entity manager when we need it. 
    */
   public EntityManager getEntityManager() {
      if (entityManager == null) 
	 entityManager = factory.createEntityManager();
      return entityManager;
   }

   /**
    * Shuts down the entity manager.
    */
   public void shutdown() {
      if (entityManager != null) {
	 entityManager.close();
	 entityManager = null;
      }
   }

   /**
    * Performs a shutdown on object cleanup, if necessary.
    */
   @Override 
   protected void finalize() {
      shutdown();
   }

   /**
    * Retrieves the names of all the quiz objects stored in the
    * database.
    */
   public List<String> getAllQuizNames() {
      EntityTransaction tx = getEntityManager().getTransaction();
      tx.begin();
      try {
	 Query query = getEntityManager().createQuery("select q.name from Quiz q");
	 List<String> results = (List<String>) query.getResultList();
	 tx.commit();
	 return results;
      }
      catch (RuntimeException e) {
	 if (tx.isActive())
	    tx.rollback();
	 throw e;
      }
   }

   /**
    * Finds and returns the Quiz with the given name, or null if one
    * doesn't exist.
    */
   public Quiz findQuiz(String quizName) {
      if (quizName == null)
	 return null;

      EntityTransaction tx = getEntityManager().getTransaction();
      tx.begin();
      try {
         Quiz quiz = getEntityManager().find(Quiz.class, quizName);
	 tx.commit();
	 return quiz;
      }
      catch (RuntimeException e) {
	 if (tx.isActive())
	    tx.rollback();
	 throw e;
      }
   }

   /**
    * Returns true if a quiz with the given name already exists.
    */
   public boolean quizExists(String quizName) {
      return (findQuiz(quizName) != null);
   }

   /**
    * Persists the given Quiz object in the database.
    * 
    * @throw javax.persistence.EntityExistsException if a quiz with
    * this name already exists
    */
   public void persistQuiz(Quiz quiz) {
      EntityTransaction tx = getEntityManager().getTransaction();
      tx.begin();
      try {
         getEntityManager().persist(quiz);
	 tx.commit();
      }
      // Openjpa wraps an EntityExistsException in a RollbackException, 
      // which makes it hard to single out the case where the entity 
      // already exists.
      catch (RollbackException e) {
	 if (tx.isActive())
	    tx.rollback();
	 Throwable cause = e.getCause();
	 if ((cause != null) && (cause instanceof EntityExistsException))
	    throw (EntityExistsException) cause;
	 else 
	    throw e;
      }
      catch (RuntimeException e) {
	 if (tx.isActive())
	    tx.rollback();
	 throw e;
      }
   }

   /**
    * Discards any changes made to the given Quiz, overwriting it with
    * data found in the database.
    *
    * @throw EntityNotFoundException if the quiz no longer exists in
    * the database
    */
   public void discardQuizChanges(Quiz quiz) {
      EntityTransaction tx = getEntityManager().getTransaction();
      tx.begin();
      try {
	 getEntityManager().refresh(quiz);
	 tx.commit();
      }
      catch (RuntimeException e) {
	 if (tx.isActive())
	    tx.rollback();
	 throw e;
      }
   }

   /**
    * Deletes the quiz with the given name from the database.
    * 
    * @throw EntityNotFoundException if a quiz with the given name
    * does not exist in the database
    * @throw OptimisticLockException in case of a concurrent
    * modification by another transaction
    */
   public void deleteQuiz(String quizName) {
      EntityTransaction tx = getEntityManager().getTransaction();
      tx.begin();
      try {
         Quiz quiz = getEntityManager().find(Quiz.class, quizName);
	 if (quiz == null)
	    throw new EntityNotFoundException("Quiz " + quizName + " not found");
	 getEntityManager().remove(quiz);
	 tx.commit();
      }
      catch (RollbackException e) {
	 if (tx.isActive())
	    tx.rollback();
	 Throwable cause = e.getCause();
	 if ((cause != null) && (cause instanceof OptimisticLockException))
	    throw (OptimisticLockException) cause;
	 else 
	    throw e;
      }
      catch (RuntimeException e) {
	 if (tx.isActive())
	    tx.rollback();
	 throw e;
      }
   }

   /**
    * Finds and returns the Question with the given id, or null if one
    * doesn't exist.
    */
   public Question findQuestion(long questionId) {
      EntityTransaction tx = getEntityManager().getTransaction();
      tx.begin();
      try {
         Question question = getEntityManager().find(Question.class, questionId);
	 tx.commit();
	 return question;
      }
      catch (RuntimeException e) {
	 if (tx.isActive())
	    tx.rollback();
	 throw e;
      }
   }

   /**
    * Adds the given Question to a quiz with the given name, and
    * persists the Question in the database.
    * 
    * @throw EntityNotFoundException if a quiz with the given name
    * does not exist in the database
    * @throw OptimisticLockException in case of a concurrent
    * modification by another transaction
    */
   public void addQuestion(String quizName, Question question) {
      EntityTransaction tx = getEntityManager().getTransaction();
      tx.begin();
      try {
         Quiz quiz = getEntityManager().find(Quiz.class, quizName);
	 if (quiz == null)
	    throw new EntityNotFoundException("Quiz " + quizName + " not found");
	 quiz.questions.add(question);
	 tx.commit();
      }
      catch (RollbackException e) {
	 if (tx.isActive())
	    tx.rollback();
	 Throwable cause = e.getCause();
	 if ((cause != null) && (cause instanceof OptimisticLockException))
	    throw (OptimisticLockException) cause;
	 else 
	    throw e;
      }
      catch (RuntimeException e) {
	 if (tx.isActive())
	    tx.rollback();
	 throw e;
      }
   }

   /**
    * Updates the given Question in the database.
    *
    * @throw OptimisticLockException in case of a concurrent
    * modification by another transaction
    */
   public void updateQuestion(Question question) {
      EntityTransaction tx = getEntityManager().getTransaction();
      tx.begin();
      try {
	 // In the case where question is already attached, this is a
	 // no-op.
	 getEntityManager().merge(question);
	 tx.commit();
      }
      catch (RollbackException e) {
	 if (tx.isActive())
	    tx.rollback();
	 Throwable cause = e.getCause();
	 if ((cause != null) && (cause instanceof OptimisticLockException))
	    throw (OptimisticLockException) cause;
	 else 
	    throw e;
      }
      catch (RuntimeException e) {
	 if (tx.isActive())
	    tx.rollback();
	 throw e;
      }
   }

   /**
    * Discards any changes made to the given Question, overwriting it
    * with data found in the database.
    *
    * @throw EntityNotFoundException if the question no longer exists
    * in the database
    */
   public void discardQuestionChanges(Question question) {
      EntityTransaction tx = getEntityManager().getTransaction();
      tx.begin();
      try {
	 if (getEntityManager().contains(question))
	    getEntityManager().refresh(question);
	 tx.commit();
      }
      catch (RuntimeException e) {
	 if (tx.isActive())
	    tx.rollback();
	 throw e;
      }
   }

   /**
    * Deletes the question with the given id from the quiz with the
    * given name, and from the database.
    * 
    * @throw EntityNotFoundException if a quiz with the given name
    * does not exist in the database, or if a question with the given
    * id is not found in this quiz
    * @throw OptimisticLockException in case of a concurrent
    * modification by another transaction
    */
   public void deleteQuestion(String quizName, long questionId) {
      EntityTransaction tx = getEntityManager().getTransaction();
      tx.begin();
      try {
         Quiz quiz = getEntityManager().find(Quiz.class, quizName);
	 if (quiz == null)
	    throw new EntityNotFoundException("Quiz " + quizName + " not found");
	 int numQuestions = quiz.questions.size();
	 boolean deleted = false;
	 for (int i = 0; i < numQuestions; i++) {
	    Question question = quiz.questions.get(i);
	    if (question.id == questionId) {
	       quiz.questions.remove(i);
	       getEntityManager().remove(question);
	       deleted = true;
	       break;
	    }
	 }
	 if (!deleted)
	    throw new EntityNotFoundException
	       ("Question with id " + questionId + " not found in quiz " + quizName);
	 tx.commit();
      }
      catch (RollbackException e) {
	 if (tx.isActive())
	    tx.rollback();
	 Throwable cause = e.getCause();
	 if ((cause != null) && (cause instanceof OptimisticLockException))
	    throw (OptimisticLockException) cause;
	 else 
	    throw e;
      }
      catch (RuntimeException e) {
	 if (tx.isActive())
	    tx.rollback();
	 throw e;
      }
   }
}
