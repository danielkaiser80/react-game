package hibernate.example.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;

class HibernateUtil {

	private static final EntityManagerFactory emFactory;
	private static final EntityManager entityManager;
	private static final CriteriaBuilder criteriaBuilder;

	private HibernateUtil() {
		/* EMPTY */}

	static {
		// change according to database
		emFactory = Persistence.createEntityManagerFactory("sqlite");
		entityManager = emFactory.createEntityManager();
		criteriaBuilder = emFactory.getCriteriaBuilder();
	}

	/**
	 * @return the entityManager
	 */
	static EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @return the criteriaBuilder
	 */
	static CriteriaBuilder getCriteriaBuilder() {
		return criteriaBuilder;
	}

	static void shutdown() {
		entityManager.close();
		emFactory.close();
	}
}