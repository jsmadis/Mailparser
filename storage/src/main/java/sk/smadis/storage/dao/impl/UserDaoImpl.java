package sk.smadis.storage.dao.impl;

import org.apache.log4j.Logger;
import sk.smadis.storage.dao.UserDao;
import sk.smadis.storage.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Stateless
public class UserDaoImpl implements UserDao {

    private Logger logger = Logger.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager em;


    @Override
    public User create(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        if (user.getId() != null) {
            throw new IllegalArgumentException("User does not have ID null");
        }
        logger.info("Creating user: " + user);

        em.persist(user);
        em.flush();
        return user;
    }

    @Override
    public User update(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        if (user.getId() == null) {
            throw new IllegalArgumentException("User can not be updated with null id");
        }
        logger.info("Updating user: " + user);

        em.merge(user);
        em.flush();
        return user;
    }

    @Override
    public User delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is null");
        }
        User user = em.find(User.class, id);
        if(user == null) return null;
        logger.info("Deleting user: " + user);

        em.remove(em.merge(user));
        em.flush();
        return user;
    }

    @Override
    public User findById(Long id) {
        logger.info("Find user by id: " + id);
        return em.find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {
        logger.info("Get all users.");
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }
}
