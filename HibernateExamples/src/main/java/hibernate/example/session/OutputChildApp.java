package hibernate.example.session;

import hibernate.example.model.Parent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Insert some database objects into the database
 */
class OutputChildApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutputChildApp.class);
    private static SessionFactory sessionFactory = null;

    private OutputChildApp() {
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

        try (Session session = sessionFactory.openSession()) {
            Parent p = session.get(Parent.class, 1);
            p.getChildren().stream().map(Object::toString).forEach(LOGGER::info);
        } catch (final Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        sessionFactory.close();
    }
}