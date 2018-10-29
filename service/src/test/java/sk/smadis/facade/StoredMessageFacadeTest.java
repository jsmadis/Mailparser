package sk.smadis.facade;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import sk.smadis.api.dto.mailparser.MailboxDTO;
import sk.smadis.api.dto.mailparser.ParsedDataDTO;
import sk.smadis.api.dto.mailparser.ParsingRuleDTO;
import sk.smadis.api.dto.mailparser.StoredMessageDTO;
import sk.smadis.api.dto.mailparser.UserDTO;
import sk.smadis.utils.DTOFactory;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@RunWith(Arquillian.class)
public class StoredMessageFacadeTest extends AbstractFacadeTest {
    @Test
    public void testCreateStoredMessage(){
        StoredMessageDTO message = DTOFactory.createStoredMessageDTO("1231421");
        message.setMailbox(mailboxDTO);

        StoredMessageDTO created =  messageFacade.create(message);

        MailboxDTO mailbox = mailboxFacade.findById(mailboxDTO.getId());

        assertThat(created.getId()).isNotNull();
        assertThat(created.getMailbox()).isEqualToComparingFieldByField(mailbox);
        assertThat(mailbox.getStoredMessages()).contains(storedMessageDTO, created);
    }

    @Test
    public void testUpdateStoredMessage(){
        StoredMessageDTO message = messageFacade.findById(storedMessageDTO.getId());

        message.setMessageID("updated");

        messageFacade.update(message);

        StoredMessageDTO updated = messageFacade.findById(message.getId());
        MailboxDTO mailbox = mailboxFacade.findById(mailboxDTO.getId());

        assertThat(updated.getMessageID()).isEqualTo("updated");
        assertThat(updated.getMailbox()).isEqualToComparingFieldByField(mailbox);
    }

    @Test
    public void testDeleteStoredMessage(){
        StoredMessageDTO message = messageFacade.findById(storedMessageDTO.getId());

        messageFacade.delete(message);

        UserDTO user = userFacade.findById(userDTO.getId());
        MailboxDTO mailbox = mailboxFacade.findById(mailboxDTO.getId());
        message = messageFacade.findById(storedMessageDTO.getId());
        ParsingRuleDTO parsingRule = parsingRuleFacade.findById(parsingRuleDTO.getId());
        ParsedDataDTO parsedData = parsedDataFacade.findById(parsedDataDTO.getId());

        assertThat(user).isNotNull();
        assertThat(user.getMailboxes()).isNotEmpty();
        assertThat(mailbox).isNotNull();
        assertThat(parsingRule).isNotNull();
        assertThat(message).isNull();
        assertThat(parsedData).isNull();
    }

    @Test
    public void testGetAllMessages(){
        assertThat(messageFacade.getAllStoredMessages()).contains(storedMessageDTO);
    }

    @Test
    public void testGetLastMessages(){
        assertThat(messageFacade.getLastMessages(1)).containsExactlyInAnyOrder(storedMessageDTO);
        assertThat(messageFacade.getLastMessages(10000)).hasSize(2).contains(storedMessageDTO);
    }
    @Test
    public void testGetMessagesAfter(){
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, 2015);
        assertThat(messageFacade.getMessagesAfter(date)).containsExactlyInAnyOrder(storedMessageDTO);

        date.set(Calendar.YEAR, 2000);
        assertThat(messageFacade.getMessagesAfter(date)).hasSize(2).contains(storedMessageDTO);
    }
}
