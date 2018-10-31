package sk.smadis.parser.plain_text;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import sk.smadis.email_client.EmailToParse;
import sk.smadis.parser.AbstractParserTest;
import sk.smadis.storage.entity.Mailbox;
import sk.smadis.storage.entity.ParsingRule;
import sk.smadis.storage.entity.enums.EmailComponent;
import sk.smadis.storage.entity.enums.ParsingType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@RunWith(JUnit4.class)
public class BodyParseTest extends AbstractParserTest {

    @Override
    public Mailbox setupMailbox() {
        Mailbox mailbox = new Mailbox();
        ParsingRule parsingRule = new ParsingRule();
        parsingRule.setMailbox(mailbox);
        parsingRule.setComponent(EmailComponent.BODY);
        parsingRule.setParsingType(ParsingType.TEXT);
        parsingRule.setRule(".*.");
        mailbox.addParsingRule(parsingRule);
        return mailbox;
    }


    @Test
    public void testPlainTextParseBody() {
        assertThat(parsedDataList).hasSize(1);
        assertThat(parsedDataList.get(0).getValues()).hasSize(1);
        assertThat(parsedDataList.get(0).getValues().get(0)).isEqualTo("Testing body");
        assertThat(parsedDataList.get(0).getStoredMessage()).isEqualTo(storedMessages.get(0));

    }

    @Override
    public List<EmailToParse> createEmails() {
        List<EmailToParse> emails = new ArrayList<>();
        EmailToParse emailToParse = new EmailToParse();
        emailToParse.bodyPlainText("Testing body")
                .messageId("123456")
                .receivedDate(Calendar.getInstance().getTime())
                .to(createTo());

        emails.add(emailToParse);
        return emails;
    }
}