package hibernate.example.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Example for Hibernate presentation
 */
public class Person {

    private static Person ourInstance = new Person();

    private Long id;
    private String name;
    private String address;

    private SessionFactory sessionFactory;

    private Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public static Person getInstance() {
        return ourInstance;
    }

    /**
     * Constructor for Hibernate
     */
    private Person() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private void töten() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            //do some work
            //...
            transaction.commit();
        } catch (final Exception pE) {
            if (transaction != null) transaction.rollback();
            throw pE;
        }
    }

    private Session getCurrentSession() {

        return null;
    }

    /**
     * Returns the hashcode of the primary key. If the primary key is <code>null</code>
     * <code>super.hashCode()</code> is returned.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        if (getId() == null)
            return super.hashCode();
        else
            return getId().hashCode();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
