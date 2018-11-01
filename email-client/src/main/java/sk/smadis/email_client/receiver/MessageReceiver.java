package sk.smadis.email_client.receiver;

import org.apache.log4j.Logger;
import sk.smadis.email_client.EmailToParse;
import sk.smadis.email_client.exceptions.ParsingMessageException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Class that gets emails from mail-server
 *
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Stateless
public class MessageReceiver {

    @Inject
    private MessageParserUtils messageParserUtils;

    private Properties properties;
    private Session session;
    private Logger logger = Logger.getLogger(this.getClass());

    public MessageReceiver() {
        properties = new Properties();
        Properties sessionProperties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("receiver.properties"));
            sessionProperties.load(getClass().getClassLoader().getResourceAsStream("receiver-credentials.properties"));
        } catch (IOException e) {
            logger.error("Error while processing properties files.", e);
        }
        session = createSession(sessionProperties.getProperty("username"), sessionProperties.getProperty("password"));
    }


    public List<EmailToParse> getNewMessages() {
        List<EmailToParse> emailToParses = new ArrayList<>();
        try {
            Store store = session.getStore("imap");
            store.connect(properties.getProperty("mail.imap.host"), Integer.valueOf(properties.getProperty("mail.imap.port")),
                    properties.getProperty("username"), properties.getProperty("password"));

            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
            Message[] messages = folder.getMessages();
            emailToParses = messageParserUtils.parseEmails(messages);

            // WARNING: This will delete every email in INBOX folder
            for (Message m : messages) {
                m.setFlag(Flags.Flag.DELETED, true);
            }

            folder.close(true);
            store.close();
        } catch (MessagingException e) {
            logger.error("Error while getting new messages from mail-server.", e);
        } catch (ParsingMessageException e) {
            logger.error("Error while parsing emails.", e);
        }
        return emailToParses;

    }

    private Session createSession(final String username, final String password) {
        return Session.getInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }
}

