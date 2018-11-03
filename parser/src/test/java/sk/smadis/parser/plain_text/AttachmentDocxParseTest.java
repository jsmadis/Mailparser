package sk.smadis.parser.plain_text;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import sk.smadis.email_client.EmailToParse;
import sk.smadis.parser.AbstractParserTest;
import sk.smadis.storage.entity.Mailbox;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@RunWith(JUnit4.class)
public class AttachmentDocxParseTest extends AbstractParserTest {
    @Override
    public Mailbox setupMailbox() {
        return attachmentRuleMailbox();
    }


    @Test
    public void testPlainTextParseAttachmentPDF() {
        assertThat(parsedDataList).hasSize(1);
        assertThat(parsedDataList.get(0).getValues()).hasSize(1);
        assertThat(parsedDataList.get(0).getValues().get(0)).isEqualTo("Testing attachments");
        assertThat(parsedDataList.get(0).getStoredMessage()).isEqualTo(storedMessages.get(0));

    }

    @Override
    public List<EmailToParse> createEmails() {
        List<EmailToParse> emails = new ArrayList<>();
        EmailToParse emailToParse = new EmailToParse();
        emailToParse.addFile(new File(getClass().getClassLoader().getResource("testing_attachment.docx").getFile()))
                .messageId("123456")
                .receivedDate(Calendar.getInstance().getTime())
                .to(createTo());

        emails.add(emailToParse);
        return emails;
    }
}
