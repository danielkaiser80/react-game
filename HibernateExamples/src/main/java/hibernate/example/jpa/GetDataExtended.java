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

class GetDataExtended {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetData.class);

    private GetDataExtended() {
        /* EMPTY */
    }

    public static void main(String... args) {
        EntityManager em = HibernateUtil.getEntityManager();
        CriteriaBuilder cb = HibernateUtil.getCriteriaBuilder();

        CriteriaQuery<Contact> cq = cb.createQuery(Contact.class);

        Root<Contact> root = cq.from(Contact.class);
        cq.select(root);
        cq.where(cb.greaterThan(root.get(Contact_.id), 5)).distinct(true);

        List<Contact> contactList = em.createQuery(cq).getResultList();

        contactList.forEach(contact -> LOGGER.info("Id: {} | Name: {} | Address: {}.", contact.getId(),
                contact.getName(), contact.getAddress()));
        HibernateUtil.shutdown();
    }
}