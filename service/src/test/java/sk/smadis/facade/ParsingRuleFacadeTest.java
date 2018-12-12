package sk.smadis.facade;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import sk.smadis.api.dto.mailparser.MailboxDTO;
import sk.smadis.api.dto.mailparser.ParsedDataDTO;
import sk.smadis.api.dto.mailparser.ParsingRuleDTO;
import sk.smadis.api.dto.mailparser.StoredMessageDTO;
import sk.smadis.api.dto.mailparser.UserDTO;
import sk.smadis.api.dto.mailparser.create.ParsingRuleCreateDTO;
import sk.smadis.api.dto.mailparser.enums.EmailComponent;
import sk.smadis.api.dto.mailparser.enums.ParsingType;
import sk.smadis.utils.DTOFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@RunWith(Arquillian.class)
public class ParsingRuleFacadeTest extends AbstractFacadeTest {
    @Test
    public void testCreateParsingRule(){
        ParsingRuleCreateDTO parsingRuleCreate = DTOFactory.
                createParsingRuleDTO("rule", ParsingType.HTML, EmailComponent.ATTACHMENT, "rule");
        parsingRuleCreate.setMailbox(mailboxDTO);

        ParsingRuleDTO parsingRule = parsingRuleFacade.create(parsingRuleCreate);

        UserDTO user = userFacade.findById(userDTO.getId());
        MailboxDTO mailbox = mailboxFacade.findById(mailboxDTO.getId());

        assertThat(parsingRule.getId()).isNotNull();
        assertThat(parsingRule.getMailbox()).isEqualTo(mailbox);
        assertThat(mailbox.getParsingRules()).containsExactlyInAnyOrder(parsingRule, parsingRuleDTO);
        assertThat(user.getMailboxes().get(0).getParsingRules()).containsExactlyInAnyOrder(parsingRule,parsingRuleDTO);
    }

    @Test
    public void testUpdateParsingRule(){
        ParsingRuleDTO parsingRule = parsingRuleFacade.findById(parsingRuleDTO.getId());
        parsingRule.setComponent(EmailComponent.HEADERS);
        parsingRule.setName("updated");

        parsingRuleFacade.update(parsingRule);

        ParsingRuleDTO updated = parsingRuleFacade.findById(parsingRuleDTO.getId());

        assertThat(updated.getId()).isEqualTo(parsingRule.getId());

        UserDTO user = userFacade.findById(userDTO.getId());
        MailboxDTO mailbox = mailboxFacade.findById(mailboxDTO.getId());


        assertThat(mailbox.getParsingRules()).containsExactlyInAnyOrder(updated);
        assertThat(user.getMailboxes().get(0).getParsingRules()).containsExactlyInAnyOrder(updated);
        assertThat(updated.getMailbox()).isEqualTo(mailbox);
    }

    @Test
    public void testDeleteParsingRule(){
        ParsingRuleDTO parsingRule = parsingRuleFacade.findById(parsingRuleDTO.getId());

        parsingRuleFacade.delete(parsingRule.getId());

        UserDTO user = userFacade.findById(userDTO.getId());
        MailboxDTO mailbox = mailboxFacade.findById(mailboxDTO.getId());
        StoredMessageDTO message = messageFacade.findById(storedMessageDTO.getId());
        parsingRule = parsingRuleFacade.findById(parsingRuleDTO.getId());
        ParsedDataDTO parsedData = parsedDataFacade.findById(parsedDataDTO.getId());

        assertThat(user).isNotNull();
        assertThat(user.getMailboxes()).isNotEmpty();
        assertThat(mailbox).isNotNull();
        assertThat(message).isNotNull();
        assertThat(parsingRule).isNull();
        assertThat(parsedData).isNull();
    }

    @Test
    public void testGetAllParsingRules(){
        assertThat(parsingRuleFacade.getAllParsingRules()).containsExactlyInAnyOrder(parsingRuleDTO);
    }
}
