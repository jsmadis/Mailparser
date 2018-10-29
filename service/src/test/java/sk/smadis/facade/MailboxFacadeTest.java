package sk.smadis.facade;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import sk.smadis.api.dto.mailparser.MailboxDTO;
import sk.smadis.api.dto.mailparser.ParsedDataDTO;
import sk.smadis.api.dto.mailparser.ParsingRuleDTO;
import sk.smadis.api.dto.mailparser.StoredMessageDTO;
import sk.smadis.api.dto.mailparser.UserDTO;
import sk.smadis.api.dto.mailparser.create.MailboxCreateDTO;
import sk.smadis.utils.DTOFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@RunWith(Arquillian.class)
public class MailboxFacadeTest extends AbstractFacadeTest {
    @Test
    public void testCreateMailbox() {
        MailboxCreateDTO mailbox = DTOFactory.createMailboxDTO("mailbox");
        mailbox.setUser(userDTO);

        MailboxDTO createdMailbox = mailboxFacade.create(mailbox);

        assertThat(createdMailbox.getId()).isNotNull();
        assertThat(createdMailbox.getName()).isNotNull();

        UserDTO user = userFacade.findById(userDTO.getId());
        assertThat(createdMailbox.getUser()).isEqualTo(user);
        assertThat(user.getMailboxes()).containsExactlyInAnyOrder(createdMailbox, mailboxDTO);
    }

    @Test
    public void testUpdateMailbox() {
        MailboxDTO mailbox = mailboxFacade.findById(mailboxDTO.getId());
        mailbox.setName("blabla");

        mailboxFacade.update(mailbox);

        MailboxDTO updated = mailboxFacade.findById(mailbox.getId());

        assertThat(updated.getName()).isEqualTo("blabla");

        UserDTO user = userFacade.findById(userDTO.getId());

        assertThat(user.getMailboxes()).containsExactly(updated);
        assertThat(updated.getUser()).isEqualTo(user);

    }

    @Test
    public void testDeleteMailbox() {
        MailboxDTO mailbox = mailboxFacade.findById(mailboxDTO.getId());

        mailboxFacade.delete(mailbox);

        UserDTO user = userFacade.findById(userDTO.getId());
        mailbox = mailboxFacade.findById(mailbox.getId());
        StoredMessageDTO message = messageFacade.findById(storedMessageDTO.getId());
        ParsingRuleDTO parsingRule = parsingRuleFacade.findById(parsingRuleDTO.getId());
        ParsedDataDTO parsedData = parsedDataFacade.findById(parsedDataDTO.getId());

        assertThat(user.getMailboxes()).isEmpty();
        assertThat(mailbox).isNull();
        assertThat(message).isNull();
        assertThat(parsingRule).isNull();
        assertThat(parsedData).isNull();
    }

    @Test
    public void testGetAllMailboxes() {
        assertThat(mailboxFacade.getAllMailboxes()).containsExactly(mailboxDTO);
    }
}

