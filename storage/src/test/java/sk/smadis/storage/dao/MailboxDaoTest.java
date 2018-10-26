package sk.smadis.storage.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.arquillian.junit.Arquillian;
import sk.smadis.storage.entity.Mailbox;
import sk.smadis.storage.entity.ParsedData;
import sk.smadis.storage.entity.ParsingRule;
import sk.smadis.storage.entity.StoredMessage;
import sk.smadis.storage.entity.User;
import sk.smadis.storage.entity.enums.EmailComponent;
import sk.smadis.storage.entity.enums.ParsingType;
import sk.smadis.storage.utils.EntityFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@RunWith(Arquillian.class)
public class MailboxDaoTest extends AbstractDaoTest {

    @Test
    public void testCreateMailbox() {
        Mailbox mailbox = EntityFactory.createMailbox("name");
        mailbox.setUser(user1);

        mailboxDao.create(mailbox);

        Mailbox created = mailboxDao.findById(mailbox.getId());

        assertThat(created).isEqualToComparingFieldByField(mailbox);

        User user = userDao.findById(user1.getId());

        assertThat(created.getUser()).isEqualToComparingFieldByField(user);
        assertThat(user.getMailboxes()).containsOnly(mailbox1, created);
    }


    @Test
    public void testUpdateMailbox() {
        Long id = mailbox1.getId();
        mailbox1.setName("updated name");
        mailboxDao.update(mailbox1);

        assertThat(mailbox1.getId()).isEqualTo(id);

        Mailbox updated = mailboxDao.findById(id);

        assertThat(updated.getName()).isEqualTo("updated name");
        assertThat(updated.getUser()).isEqualToComparingFieldByField(user1);
        assertThat(updated.getParsingRules()).containsExactlyInAnyOrder(parsingRule1);

        User user = userDao.findById(user1.getId());

        assertThat(user.getMailboxes()).contains(updated);
    }

    @Test
    public void testDeleteMailbox() {
        Long id = mailbox1.getId();
        mailboxDao.delete(mailbox1);

        assertThat(mailboxDao.findById(id)).isNull();

        User user = userDao.findById(user1.getId());
        assertThat(user).isNotNull();
        assertThat(user.getMailboxes()).isEmpty();

        ParsingRule parsingRule = parsingRuleDao.findById(parsingRule1.getId());
        assertThat(parsingRule).isNull();
    }

    @Test
    public void testFindMailboxById() {
        Mailbox mailbox = mailboxDao.findById(mailbox1.getId());

        assertThat(mailbox).isEqualToComparingFieldByField(mailbox1);
        assertThat(mailbox.getParsingRules()).containsExactlyInAnyOrder(parsingRule1);
        assertThat(mailbox.getStoredMessages()).contains(message1);
    }

    @Test
    public void testFindMailboxByIdNotInDB() {
        Mailbox mailbox = mailboxDao.findById(1000000L);
        assertThat(mailbox).isNull();
    }

    @Test
    public void testFindAllMailboxes() {
        List<Mailbox> mailboxes = mailboxDao.getAllMailboxes();

        assertThat(mailboxes).containsOnly(mailbox1);
    }


    @Test
    public void testFindMailboxByName() {
        Mailbox mailbox = mailboxDao.findByName("mailbox1");

        assertThat(mailbox).isEqualToComparingFieldByFieldRecursively(mailbox1);
    }

    @Test
    public void testFindMailboxByNameNotInDB() {
        Mailbox mailbox = mailboxDao.findByName("testFindMailbox");
        assertThat(mailbox).isNull();
    }

    @Test
    public void testMessageDependency() {
        Mailbox mailbox = mailboxDao.findById(mailbox1.getId());
        StoredMessage firstMessage = messageDao.findById(message1.getId());

        assertThat(mailbox.getStoredMessages()).contains(firstMessage);
    }

    @Test
    public void testParsingRuleDependency() {
        Mailbox mailbox = mailboxDao.findById(mailbox1.getId());
        ParsingRule firstParsingRule = parsingRuleDao.findById(parsingRule1.getId());

        assertThat(mailbox.getParsingRules()).containsExactlyInAnyOrder(firstParsingRule);
    }

    @Test
    public void testParsedDataDependency() {
        StoredMessage firstMessage = messageDao.findById(message1.getId());
        ParsingRule firstParsingRule = parsingRuleDao.findById(parsingRule1.getId());
        ParsedData firstParsedData = parsedDataDao.findById(parsedData1.getId());

        assertThat(firstParsingRule.getParsedData()).containsExactly(firstParsedData);

        assertThat(firstMessage.getParsedData()).containsExactly(firstParsedData);
    }
}
