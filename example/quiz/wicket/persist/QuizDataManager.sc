// Wicket requires model objects to be Serializable
QuizDataManager implements Serializable {
   transient EntityManager entityManager;
}
