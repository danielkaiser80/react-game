package pointtest;

import hibernate.example.model.Point;
import hibernate.example.model.Point_;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Example class for using the Hibernate entity manager with structured queries
 *
 * @author dkaiser
 */
class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private Main() {
    }

    /**
     * Main method to start the program
     *
     * @param args not used
     */
    public static void main(String... args) {
        // Open a database connection
        // (create a new database if it doesn't exist yet):
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("sqlite");
        final EntityManager em = emf.createEntityManager();

        // delete all points
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Point> criteriaDelete = cb.createCriteriaDelete(Point.class);
        criteriaDelete.from(Point.class);
        em.createQuery(criteriaDelete).executeUpdate();
        em.getTransaction().commit();

        // Store some Point objects in the database:
        em.getTransaction().begin();
        IntStream.range(0, 100).mapToObj(Point::new).forEach(em::persist);
        em.getTransaction().commit();

        // Find the number of Point objects in the database:
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(Point.class)));

        LOGGER.info("Total Points: " + em.createQuery(countQuery).getSingleResult());

        // Find the average X value:
        CriteriaQuery<Double> avgQuery = cb.createQuery(Double.class);
        avgQuery.select(cb.avg(avgQuery.from(Point.class).get(Point_.x)));

        LOGGER.info("Average x: " + em.createQuery(avgQuery).getSingleResult());

        // Retrieve all the Point objects from the database:
        CriteriaQuery<Point> pointQuery = cb.createQuery(Point.class);
        Root<Point> root = pointQuery.from(Point.class);
        pointQuery.select(root);

        final List<Point> results = em.createQuery(pointQuery).getResultList();
        results.stream().map(Point::toString).forEach(LOGGER::info);

        // Close the database connection
        em.close();
        emf.close();
    }
}