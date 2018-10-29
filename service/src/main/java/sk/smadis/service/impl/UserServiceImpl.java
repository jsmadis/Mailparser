package sk.smadis.service.impl;

import sk.smadis.service.UserService;
import sk.smadis.service.exceptions.MailparserServiceException;
import sk.smadis.storage.dao.UserDao;
import sk.smadis.storage.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Stateless
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User create(User user) throws MailparserServiceException {
        if (user == null) {
            throw new IllegalArgumentException("User shouldn't be null.");
        }
        try {
            return userDao.create(user);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public User update(User user) throws MailparserServiceException {
        if (user == null) {
            throw new IllegalArgumentException("User shouldn't be null.");
        }
        try {
            return userDao.update(user);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public void delete(User user) throws MailparserServiceException {
        if (user == null) {
            throw new IllegalArgumentException("User shouldn't be null.");
        }
        try {
            userDao.delete(user);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public User findById(Long id) throws MailparserServiceException {
        if (id == null) {
            throw new IllegalArgumentException("Id shouldn't be null.");
        }
        try {
            return userDao.findById(id);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() throws MailparserServiceException {
        try {
            return userDao.getAllUsers();
        }catch (Throwable t){
            throw new MailparserServiceException(t.getMessage());
        }

    }
}
