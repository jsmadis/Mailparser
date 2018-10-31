package sk.smadis.parser.parsers;

import org.apache.log4j.Logger;
import sk.smadis.email_client.EmailToParse;
import sk.smadis.parser.utils.RESTSender;
import sk.smadis.service.MailboxService;
import sk.smadis.service.ParsedDataService;
import sk.smadis.service.StoredMessageService;
import sk.smadis.storage.entity.Mailbox;
import sk.smadis.storage.entity.ParsedData;
import sk.smadis.storage.entity.ParsingRule;
import sk.smadis.storage.entity.StoredMessage;
import sk.smadis.storage.entity.enums.ParsingType;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Stateless
public class EmailParser implements Parser {
    public static final String EMAIL_DOMAIN = "localhost";

    private Logger logger = Logger.getLogger(this.getClass());

    @Inject
    private MailboxService mailboxService;

    @Inject
    private ParsedDataService parsedDataService;

    @Inject
    private StoredMessageService storedMessageService;

    @Inject
    private RESTSender restSender;

    @Inject
    private HTMLParser htmlParser;

    @Inject
    private PlainTextParser plainTextParser;


    @Override
    public void processEmails(List<EmailToParse> emailsToParse) {
        for (EmailToParse email : emailsToParse) {
            processEmail(email);
        }
    }

    @Override
    public void processEmail(EmailToParse emailToParse) {
        List<String> mailboxNames = extractMailboxNames(emailToParse);

        List<Mailbox> mailboxes = getMailboxes(mailboxNames);

        logger.debug("Process email for given mailbox names: " + mailboxNames);

        List<StoredMessage> storedMessages = new ArrayList<>();
        for (Mailbox mailbox : mailboxes) {
            storedMessages.add(parseEmail(mailbox, emailToParse));
            restSender.sendToRest(mailbox, emailToParse);
        }
    }


    /**
     * Parses emails and saves it as StoredMessage to database.
     *
     * @param mailbox      Mailbox
     * @param emailToParse email to parse
     * @return StoredMessage
     */
    private StoredMessage parseEmail(Mailbox mailbox, EmailToParse emailToParse) {
        List<ParsingRule> parsingRules = mailbox.getParsingRules();
        if (parsingRules == null || parsingRules.isEmpty()) {
            logger.debug("Mailbox :" + mailbox.getName() + " doesn't have any parsing rule");
            return null;
        }

        StoredMessage messageToStore = createStoredMessage(mailbox, emailToParse);

        for (ParsingRule rule : parsingRules) {
            parseData(messageToStore, rule, emailToParse);
        }
        return messageToStore;
    }


    /**
     * Parses email based on given ParsingRule and saves ParsedData to database.
     * Connects ParsedData with ParsingRule and StoredMessage
     *
     * @param storedMessage StoredMessage
     * @param rule          ParsingRule
     * @param email         email to parse
     */
    private void parseData(StoredMessage storedMessage, ParsingRule rule, EmailToParse email) {
        List<ParsedData> parsedDataList = new ArrayList<>();
        if (email.getBodyHtml() != null && rule.getParsingType() == ParsingType.TEXT) {
            String message = "Email contained html and parsing rule was set to TEXT --> skip. Parsing rule: " +
                    rule + System.lineSeparator() + "Email:" + email;
            logger.debug(message);
            return;
        }
        try {
            switch (rule.getParsingType()) {
                case HTML:
                    if (email.getBodyHtml() == null) {
                        return;
                    }
                    parsedDataList = htmlParser.parseHTML(email, rule);
                    break;
                case TEXT:
                    parsedDataList = plainTextParser.parsePlainText(email, rule);
                    break;
            }
        } catch (Exception e) {
            logger.error("Error while parsing message.", e);
        }
        if (parsedDataList == null) return;
        if (parsedDataList.isEmpty()) return;

        createParsedData(parsedDataList, rule, storedMessage);
    }


    /**
     * Saves ParsedData to Database via ParsedDataService.
     *
     * @param parsedDataList List of ParsedData to save
     * @param rule           ParsingRule
     * @param storedMessage  StoredMessage
     */
    private void createParsedData(List<ParsedData> parsedDataList, ParsingRule rule, StoredMessage storedMessage) {
        for (ParsedData parsedData : parsedDataList) {
            parsedData.setParsingRule(rule);
            parsedData.setStoredMessage(storedMessage);
            parsedDataService.create(parsedData);
        }
    }

    /**
     * Extracts local-part from email (to) recipient.
     *
     * @param emailToParse email to parse
     * @return List of local-parts of email addresses
     */
    private List<String> extractMailboxNames(EmailToParse emailToParse) {
        return emailToParse.getTo()
                .stream().filter(s -> s.split("@")[1].equals(EMAIL_DOMAIN))
                .map(s -> s.split("@")[0])
                .collect(Collectors.toList());
    }


    /**
     * Gets Mailboxes for given names.
     *
     * @param mailboxNames list of mailbox names (local-part of email address)
     * @return list of Mailboxes
     */
    private List<Mailbox> getMailboxes(List<String> mailboxNames) {
        List<Mailbox> mailboxes = new ArrayList<>();
        for (String mailboxName : mailboxNames) {
            Mailbox mailbox = mailboxService.findByName(mailboxName);
            if (mailbox != null) mailboxes.add(mailbox);
        }
        return mailboxes;
    }

    /**
     * Saves StoredMessage into database via StoredMessageService.
     *
     * @param mailbox      Mailbox
     * @param emailToParse email to parse
     * @return StoredMessage
     */
    private StoredMessage createStoredMessage(Mailbox mailbox, EmailToParse emailToParse) {
        StoredMessage messageToStore = new StoredMessage();
        messageToStore.setMessageID(emailToParse.getMessageId());

        Calendar receivedDate = Calendar.getInstance();
        receivedDate.setTime(emailToParse.getReceivedDate());
        messageToStore.setReceivedDate(receivedDate);
        messageToStore.setMailbox(mailbox);

        return storedMessageService.create(messageToStore);
    }
}
