package sk.smadis.email_client.sender;

import org.apache.log4j.Logger;
import sk.smadis.api.dto.email_client.MessageDTO;
import sk.smadis.email_client.exceptions.ProcessingMessageException;

import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Stateless
public class MailSender {
    private Properties properties;
    private Session session;

    private Logger logger = Logger.getLogger(this.getClass());

    public MailSender() {
        properties = new Properties();
        Properties sessionProperties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("sender.properties"));
            sessionProperties.load(getClass().getClassLoader().getResourceAsStream("sender-credentials.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        session = createSession(sessionProperties.getProperty("username"), sessionProperties.getProperty("password"));
    }

    public void send(MessageDTO messageDTO) throws ProcessingMessageException {
        MimeMessage message = new MimeMessage(session);
        try {
            MessageSenderUtils.fillMessage(message, messageDTO);
            Transport.send(message);
        } catch (MessagingException e) {
            logger.error("Error while sending message.", e);
        }
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
