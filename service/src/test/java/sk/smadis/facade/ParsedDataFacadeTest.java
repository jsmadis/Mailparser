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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@RunWith(Arquillian.class)
public class ParsedDataFacadeTest extends AbstractFacadeTest {

    @Test
    public void testCreateParsedData(){
        List<String> values = new ArrayList<>();
        values.add("one");
        values.add("two");

        ParsedDataDTO parsedData = DTOFactory.createParsedDataDTO(values,
                storedMessageDTO, parsingRuleDTO);

        ParsedDataDTO created = parsedDataFacade.create(parsedData);

        ParsingRuleDTO parsingRule = parsingRuleFacade.findById(parsingRuleDTO.getId());
        StoredMessageDTO message  = messageFacade.findById(storedMessageDTO.getId());
        MailboxDTO mailbox = mailboxFacade.findById(mailboxDTO.getId());

        assertThat(created.getId()).isNotNull();
        assertThat(created.getStoredMessage()).isEqualToComparingFieldByField(message);
        assertThat(created.getValues()).containsExactly("one", "two");
        assertThat(created.getParsingRule()).isEqualToComparingFieldByField(parsingRule);
        assertThat(message.getParsedData()).containsExactlyInAnyOrder(created, parsedDataDTO);
        assertThat(parsingRule.getParsedData()).containsExactlyInAnyOrder(created, parsedDataDTO);
        assertThat(mailbox.getStoredMessages().get(1).getParsedData()).containsExactlyInAnyOrder(created, parsedDataDTO);
        assertThat(mailbox.getParsingRules().get(0).getParsedData()).containsExactlyInAnyOrder(created, parsedDataDTO);
    }

    @Test
    public void testUpdateParsedData(){
        ParsedDataDTO parsedData = parsedDataFacade.findById(parsedDataDTO.getId());
        List<String> values = new ArrayList<>();
        values.add("updated");
        parsedData.setValues(values);
        parsedDataFacade.update(parsedData);

        ParsedDataDTO updated = parsedDataFacade.findById(parsedDataDTO.getId());

        assertThat(updated.getValues()).containsExactly("updated");


        StoredMessageDTO message = messageFacade.findById(storedMessageDTO.getId());
        ParsingRuleDTO parsingRule = parsingRuleFacade.findById(parsingRuleDTO.getId());

        assertThat(updated.getStoredMessage()).isEqualToComparingFieldByField(message);
        assertThat(updated.getParsingRule()).isEqualToComparingFieldByField(parsingRule);
        assertThat(message.getParsedData()).containsExactlyInAnyOrder(parsedData);
        assertThat(parsingRule.getParsedData()).containsExactlyInAnyOrder(parsedData);
    }

    @Test
    public void testDeleteParsedData(){
        ParsedDataDTO parsedData = parsedDataFacade.findById(parsedDataDTO.getId());

        parsedDataFacade.delete(parsedData);

        UserDTO user = userFacade.findById(userDTO.getId());
        MailboxDTO mailbox = mailboxFacade.findById(mailboxDTO.getId());
        StoredMessageDTO message = messageFacade.findById(storedMessageDTO.getId());
        ParsingRuleDTO parsingRule = parsingRuleFacade.findById(parsingRuleDTO.getId());
        parsedData = parsedDataFacade.findById(parsedDataDTO.getId());

        assertThat(user).isNotNull();
        assertThat(user.getMailboxes()).isNotEmpty();
        assertThat(mailbox).isNotNull();
        assertThat(message).isNotNull();
        assertThat(parsingRule).isNotNull();
        assertThat(parsedData).isNull();
    }

    @Test
    public void testGetAllParsedData(){
        assertThat(parsedDataFacade.getAllParsedData()).containsExactlyInAnyOrder(parsedDataDTO);
    }
}
