package sk.smadis.parser.jobs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import sk.smadis.email_client.EmailToParse;
import sk.smadis.email_client.receiver.MessageReceiver;
import sk.smadis.parser.parsers.Parser;
import sk.smadis.service.MailboxService;
import sk.smadis.service.UserService;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public class ProcessEmails implements Job {
    @Inject
    private MessageReceiver messageReceiver;

    @Inject
    private UserService userService;

    @Inject
    private MailboxService mailboxService;

    @Inject
    private Parser parser;

    public ProcessEmails() {
        setupUnirest();
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<EmailToParse> emailToParse = messageReceiver.getNewMessages();
        parser.processEmails(emailToParse);
    }


    /**
     * Setup method for Unirest.
     */
    private static void setupUnirest(){
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
