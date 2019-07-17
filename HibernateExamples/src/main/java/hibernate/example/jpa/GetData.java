package hibernate.example.jpa;

import hibernate.example.model.Contact;
import hibernate.example.model.Contact_;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

/**
 * Example class to get data from SQLite using JPA
 *
 * @author dkaiser
 */
class GetData {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetData.class);

    private GetData() {
        /* EMPTY */
    }

    public static void main(String... args) {
        // Fetching saved data
        EntityManager em = HibernateUtil.getEntityManager();
        CriteriaBuilder cb = HibernateUtil.getCriteriaBuilder();

        CriteriaQuery<Contact> cq = cb.createQuery(Contact.class);

        Root<Contact> root = cq.from(Contact.class);
        cq.select(root);
        cq.where(cb.equal(root.get(Contact_.age), 25));
        List<Contact> contactList = em.createQuery(cq).getResultList();

        LOGGER.info("There are {} results.", contactList.size());

        contactList.stream().map(Objects::toString).forEach(LOGGER::info);

        HibernateUtil.shutdown();
    }
}