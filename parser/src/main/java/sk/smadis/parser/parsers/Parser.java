package sk.smadis.parser.parsers;

import sk.smadis.email_client.EmailToParse;

import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public interface Parser {
    /**
     * Calls processEmail on every email from list.
     *
     * @param emailsToParse List of emails to parse
     */
    void processEmails(List<EmailToParse> emailsToParse);

    /**
     * Finds mailboxes with name same as local-part of email recipients.
     * Creates StoredMessage for each email.
     * Looks for parsing rules of given mailbox.
     * Parses email based on given parsing rules and it will save results to ParsedData entity.
     * Connects ParsedData entities with StoredMessage and ParsingRule.
     * Tries to send email via REST call to rest endpoint.
     *
     * @param emailToParse email to parse
     */
    void processEmail(EmailToParse emailToParse);
}
