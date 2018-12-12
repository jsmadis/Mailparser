package sk.smadis.storage.dao;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import sk.smadis.storage.entity.Mailbox;
import sk.smadis.storage.entity.User;
import sk.smadis.storage.utils.EntityFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@RunWith(Arquillian.class)
public class UserDaoTest extends AbstractDaoTest {
    @Test
    public void testCreateUser() {
        User user = EntityFactory.createUser("user");
        userDao.create(user);

        User createdUser = userDao.findById(user.getId());

        assertThat(createdUser).isEqualToComparingFieldByFieldRecursively(user);
    }

    @Test
    public void testUpdateUser() {
        Long id = user1.getId();

        user1.setName("updated name");

        userDao.update(user1);

        assertThat(user1.getId()).isEqualTo(id);

        User user = userDao.findById(id);

        assertThat(user).isEqualToComparingFieldByFieldRecursively(user1);
        assertThat(user.getName()).isEqualTo("updated name");
        assertThat(user.getMailboxes()).containsOnly(mailbox1);

        Mailbox mailbox = mailboxDao.findById(mailbox1.getId());

        assertThat(mailbox.getUser()).isEqualToComparingFieldByFieldRecursively(user);
    }

    @Test
    public void testDeleteUser() {
        Long id = user1.getId();
        userDao.delete(user1.getId());

        assertThat(userDao.findById(id)).isNull();

        Mailbox mailbox1Deleted = mailboxDao.findById(mailbox1.getId());

        assertThat(mailbox1Deleted).isNull();

        assertThat(parsingRuleDao.findById(parsedData1.getId())).isNull();
        assertThat(messageDao.findById(message1.getId())).isNull();
        assertThat(parsedDataDao.findById(parsedData1.getId())).isNull();
    }

    @Test
    public void testFindUserById() {
        User user = userDao.findById(user1.getId());

        assertThat(user).isEqualToComparingFieldByField(user1);
    }

    @Test
    public void testFindUserByIdNotInDB() {
        assertThat(userDao.findById(10000L)).isNull();
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = userDao.getAllUsers();

        assertThat(users).containsOnly(user1);
    }
}
