package sk.smadis.storage.dao;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import sk.smadis.storage.entity.Mailbox;
import sk.smadis.storage.entity.ParsedData;
import sk.smadis.storage.entity.StoredMessage;
import sk.smadis.storage.utils.EntityFactory;

import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@RunWith(Arquillian.class)
public class StoredMessageDaoTest extends AbstractDaoTest {
    @Test
    public void testCreateMessage() {
        StoredMessage message = EntityFactory.createMessage("dsafdsa213");
        message.setMailbox(mailbox1);
        messageDao.create(message);

        StoredMessage createdMessage = messageDao.findById(message.getId());

        assertThat(createdMessage).isEqualToComparingFieldByFieldRecursively(message);
    }

    @Test
    public void testUpdateMessage(){
        Long id = message1.getId();

        message1.setMessageID("updated id");

        messageDao.update(message1);

        assertThat(message1.getId()).isEqualTo(id);

        StoredMessage updatedMessage = messageDao.findById(id);

        assertThat(updatedMessage).isEqualToComparingFieldByFieldRecursively(message1);
        assertThat(updatedMessage.getMessageID()).isEqualTo("updated id");
        assertThat(updatedMessage.getMailbox()).isEqualToComparingFieldByFieldRecursively(mailbox1);

        Mailbox mailbox = mailboxDao.findById(mailbox1.getId());

        assertThat(mailbox.getStoredMessages()).contains(updatedMessage);
    }

    @Test
    public void testDeleteMessage(){
        Long id = message1.getId();
        messageDao.delete(message1);

        assertThat(messageDao.findById(id)).isNull();

        Mailbox mailbox = mailboxDao.findById(mailbox1.getId());
        ParsedData parsedData = parsedDataDao.findById(parsedData1.getId());

        assertThat(parsedData).isNull();
        assertThat(mailbox.getStoredMessages()).doesNotContain(message1);
    }

    @Test
    public void testFindMessageById(){
        StoredMessage message = messageDao.findById(message1.getId());

        assertThat(message).isEqualToComparingFieldByFieldRecursively(message1);
    }

    @Test
    public void testFindUserByIdNotInDB(){
        assertThat(messageDao.findById(1231231L)).isNull();
    }

    @Test
    public void testGetAllMessages(){
        List<StoredMessage> messages = messageDao.getAllStoredMessages();

        assertThat(messages).contains(message1);
    }

    @Test
    public void testGetLastMessages(){
        List<StoredMessage> messages = messageDao.getLastStoredMessages(1);

        assertThat(messages).containsExactlyInAnyOrder(message1);
    }

    @Test
    public void testGetMessageAfter(){
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, 2017);
        List<StoredMessage> messages = messageDao.getStoredMessagesAfter(date);

        assertThat(messages).containsExactly(message1);
    }

    @Test
    public void testGetMessagesAfter(){
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, 2000);
        List<StoredMessage> messages = messageDao.getStoredMessagesAfter(date);

        assertThat(messages)
                .hasSize(3)
                .contains(message1);
    }
}
