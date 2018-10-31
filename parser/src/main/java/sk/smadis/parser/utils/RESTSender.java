package sk.smadis.parser.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.log4j.Logger;
import sk.smadis.email_client.EmailToParse;
import sk.smadis.storage.entity.Mailbox;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@ApplicationScoped
public class RESTSender {
    private Logger logger = Logger.getLogger(this.getClass());


    /**
     * Sends an email (without attachments) to REST endpoint if the mailbox is set up correctly.
     *
     * @param mailbox Mailbox
     * @param email   Email
     */
    public void sendToRest(Mailbox mailbox, EmailToParse email) {
        if (!hasEverything(mailbox)) return;

        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.post(mailbox.getUser().getRestURL()).body(email).asJson();
            int code = jsonResponse.getStatus();
            logger.info("Sending email to rest: " + mailbox.getUser().getRestURL() + " Status code: " + code);
            if (!email.getAttachments().isEmpty()) {
                Unirest.post(mailbox.getUser().getRestURL()).field("files", email.getFiles()).asJson();
            }
        } catch (UnirestException e) {
            String message = "Error while sending email to rest. " +
                    "Rest url: " +
                    mailbox.getUser().getRestURL();
            logger.error(message, e);

        }
    }

    /**
     * Checks if Mailbox has everything needed to send email via REST.
     *
     * @param mailbox Mailbox
     * @return True if email can be send via REST, false otherwise
     */
    private boolean hasEverything(Mailbox mailbox) {
        return mailbox.isResendRaw() &&
                !(mailbox.getUser().getRestURL() == null) &&
                !mailbox.getUser().getRestURL().equals("");
    }
}
