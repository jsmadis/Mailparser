package sk.smadis.storage.dao.impl;

import org.apache.log4j.Logger;
import sk.smadis.storage.dao.MailboxDao;
import sk.smadis.storage.entity.Mailbox;
import sk.smadis.storage.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Stateless
public class MailboxDaoImpl implements MailboxDao {
    private Logger logger = Logger.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager em;


    @Override
    public Mailbox create(Mailbox mailbox) {
        if (mailbox == null) {
            throw new IllegalArgumentException("Mailbox is null");
        }
        if (mailbox.getId() != null) {
            throw new IllegalArgumentException("Mailbox is already in DB");
        }
        logger.info("Creating mailbox: " + mailbox);

        User user = em.find(User.class, mailbox.getUser().getId());

        mailbox.setUser(user);
        user.addMailbox(mailbox);

        em.persist(mailbox);
        em.flush();
        return mailbox;
    }

    @Override
    public Mailbox update(Mailbox mailbox) {
        if (mailbox == null) {
            throw new IllegalArgumentException("Mailbox is null");
        }
        if (mailbox.getId() == null) {
            throw new IllegalArgumentException("Mailbox is not in DB");
        }
        logger.info("Updating mailbox: " + mailbox);

        em.merge(mailbox);
        em.flush();
        return mailbox;
    }

    @Override
    public Mailbox delete(Mailbox mailbox) {
        if (mailbox == null) {
            throw new IllegalArgumentException("Mailbox is null");
        }
        if (mailbox.getUser() != null) {
            User user = mailbox.getUser();
            user.removeMailbox(mailbox);
            mailbox.setUser(null);
            em.merge(user);
        }
        logger.info("Deleting mailbox: " + mailbox);

        em.remove(em.merge(mailbox));
        em.flush();
        return mailbox;
    }

    @Override
    public Mailbox findById(Long id) {
        logger.info("Find mailbox by id: " + id);
        return em.find(Mailbox.class, id);
    }

    @Override
    public List<Mailbox> getAllMailboxes() {
        logger.info("Get all mailboxes");
        return em.createQuery("select m from Mailbox m", Mailbox.class)
                .getResultList();

    }

    @Override
    public Mailbox findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name is null");
        }
        logger.info("Find mailbox by name: " + name);
        try {
            return em.createQuery("select m from Mailbox m fetch all properties where m.name = :name", Mailbox.class)
                    .setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
