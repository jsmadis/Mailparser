package sk.smadis.parser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import sk.smadis.email_client.EmailToParse;
import sk.smadis.parser.parsers.AttachmentParser;
import sk.smadis.parser.parsers.EmailParser;
import sk.smadis.parser.parsers.HTMLParser;
import sk.smadis.parser.parsers.Parser;
import sk.smadis.parser.parsers.PlainTextParser;
import sk.smadis.parser.utils.DocumentUtils;
import sk.smadis.parser.utils.FileUtils;
import sk.smadis.parser.utils.OcrUtils;
import sk.smadis.parser.utils.RESTSender;
import sk.smadis.service.MailboxService;
import sk.smadis.service.ParsedDataService;
import sk.smadis.service.StoredMessageService;
import sk.smadis.storage.entity.Mailbox;
import sk.smadis.storage.entity.ParsedData;
import sk.smadis.storage.entity.ParsingRule;
import sk.smadis.storage.entity.StoredMessage;
import sk.smadis.storage.entity.enums.EmailComponent;
import sk.smadis.storage.entity.enums.ParsingType;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public abstract class AbstractParserTest {
    @Mock
    private MailboxService mailboxService;

    @Mock
    private ParsedDataService parsedDataService;

    @Mock
    private StoredMessageService storedMessageService;

    @Inject
    @InjectMocks
    protected Parser parser = new EmailParser();

    @Spy
    private RESTSender restSender;

    @Spy
    private HTMLParser htmlParser;

    @Spy
    @InjectMocks
    private PlainTextParser plainTextParser;

    @Spy
    @InjectMocks
    private AttachmentParser attachmentParser;

    @Spy
    private OcrUtils ocrUtils;

    @Spy
    private DocumentUtils documentUtils;

    @Spy
    private FileUtils fileUtils;

    protected Mailbox mailbox = null;
    protected List<StoredMessage> storedMessages = new ArrayList<>();
    protected List<ParsedData> parsedDataList = new ArrayList<>();

    @Before
    public void prepareTests() {
        mailbox = setupMailbox();
        MockitoAnnotations.initMocks(this);

        when(mailboxService.findByName(any(String.class))).then(invocation -> mailbox);

        when(storedMessageService.create(any(StoredMessage.class)))
                .then(invocation -> {
                    StoredMessage message = invocation.getArgumentAt(0, StoredMessage.class);
                    storedMessages.add(message);
                    return message;
                });

        when(parsedDataService.create(any(ParsedData.class)))
                .then(invocation -> {
                    ParsedData parsedData = invocation.getArgumentAt(0, ParsedData.class);
                    parsedDataList.add(parsedData);
                    return parsedData;
                });

        processEmails();
    }

    @Test
    public void testMessage() {
        assertThat(storedMessages).hasSize(1);
        assertThat(storedMessages.get(0).getMessageID()).isEqualTo("123456");
    }

    public abstract Mailbox setupMailbox();

    public void processEmails() {
        parser.processEmails(createEmails());
    }

    public abstract List<EmailToParse> createEmails();

    public List<String> createTo() {
        List<String> to = new ArrayList<>();
        to.add("test@localhost");
        return to;
    }

    protected Mailbox attachmentRuleMailbox(){
        Mailbox mailbox = new Mailbox();
        ParsingRule parsingRule = new ParsingRule();
        parsingRule.setMailbox(mailbox);
        parsingRule.setComponent(EmailComponent.ATTACHMENT);
        parsingRule.setParsingType(ParsingType.TEXT);
        parsingRule.setRule(".*.");
        mailbox.addParsingRule(parsingRule);
        return mailbox;
    }
}
