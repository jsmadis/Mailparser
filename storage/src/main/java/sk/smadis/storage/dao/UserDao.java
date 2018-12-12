package sk.smadis.storage.dao;

import sk.smadis.storage.entity.User;

import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public interface UserDao {
    /**
     * Saves(creates) user into database
     *
     * @param user User to create
     * @return Created user
     */
    User create(User user);

    /**
     * Updates user
     *
     * @param user User to update
     * @return Updated user
     */
    User update(User user);

    /**
     * Deletes user
     *
     * @param id ID of User to delete
     * @return Deleted user
     */
    User delete(Long id);

    /**
     * Finds user by ID
     *
     * @param id ID of user
     * @return User
     */
    User findById(Long id);

    /**
     * Gets all Users
     *
     * @return List of Users
     */
    List<User> getAllUsers();
}
