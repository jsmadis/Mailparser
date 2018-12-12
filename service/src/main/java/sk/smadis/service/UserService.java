package sk.smadis.service;

import sk.smadis.service.exceptions.MailparserServiceException;
import sk.smadis.storage.entity.User;

import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public interface UserService {
    User create(User user) throws MailparserServiceException;

    User update(User user) throws MailparserServiceException;

    void delete(Long id) throws MailparserServiceException;

    User findById(Long id) throws MailparserServiceException;

    List<User> getAllUsers() throws MailparserServiceException;
}
