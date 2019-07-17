package hibernate.example.session;

import hibernate.example.model.Child;
import hibernate.example.model.Parent;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Insert some database objects into the database
 */
class AddChildApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(OutputChildApp.class);
    private static SessionFactory sessionFactory = null;

    private AddChildApp() {
        /* EMPTY */
    }

    private static void configureSessionFactory() {
        // A SessionFactory is set up once for an application!
        // configures settings from hibernate.cfg.xml
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata()
                    .buildSessionFactory();
        } catch (final Exception e) {
            // The registry would be destroyed by the SessionFactory, but as an Exception
            // occurred when building the SessionFactory, we have to destroy it manually.
            LOGGER.error("An error occurred.", e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static void main(String... args) {
        // Configure the session factory
        configureSessionFactory();

        Transaction tx = null;

        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            Parent p = session.get(Parent.class, 1, LockMode.PESSIMISTIC_WRITE);

            final Child c = new Child();
            p.addChild(c);

            // Committing the change to the database
            session.flush();
            tx.commit();
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);

            // Rolling back the changes to make the data consistent in case of
            // any failure in between multiple database write operations.
            if (tx != null)
                tx.rollback();
        }
        sessionFactory.close();
    }
}