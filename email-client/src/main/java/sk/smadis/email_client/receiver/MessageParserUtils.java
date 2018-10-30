package sk.smadis.email_client.receiver;

import org.apache.commons.mail.util.MimeMessageParser;
import org.apache.log4j.Logger;
import sk.smadis.email_client.EmailToParse;
import sk.smadis.email_client.exceptions.ParsingMessageException;

import javax.activation.DataSource;
import javax.enterprise.context.ApplicationScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@ApplicationScoped
public class MessageParserUtils {

    private Logger logger = Logger.getLogger(this.getClass());

    public  List<EmailToParse> parseEmails(Message[] messages) throws ParsingMessageException {
        List<EmailToParse> emailToParses = new ArrayList<>();
        for (Message m : messages) {
            if (m instanceof MimeMessage) {
                emailToParses.add(parseMimeEmail((MimeMessage) m));
            }
        }
        return emailToParses;
    }

    private  EmailToParse parseMimeEmail(MimeMessage message) throws ParsingMessageException {
        EmailToParse emailToParse = new EmailToParse();
        MimeMessageParser parser = new MimeMessageParser(message);

        try {
            parser.parse();
            emailToParse.bodyHtml(parser.getHtmlContent())
                    .bodyPlainText(parser.getPlainContent())
                    .attachments(parser.getAttachmentList())
                    .files(getFiles(parser.getAttachmentList()))
                    .receivedDate(message.getReceivedDate());
        } catch (Exception e) {
            throw new ParsingMessageException(e.getMessage());
        }


        try {
            emailToParse.messageId(message.getMessageID())
                    .to(Arrays.asList(message.getHeader("to")))
                    .subject(message.getSubject())
                    .headers(Collections.list(message.getAllHeaderLines()));
        } catch (MessagingException e) {
            throw new ParsingMessageException(e.getMessage());
        }
        return emailToParse;
    }

    private  List<File> getFiles(List<DataSource> dataSources){
        List<File> files = new ArrayList<>();
        for (DataSource datasource : dataSources) {
            File file = createFile(datasource);
            if(file != null){
                files.add(file);
            }
        }
        return files;
    }

    private  File createFile(DataSource dataSource){
        try {
            InputStream is = dataSource.getInputStream();

            //Feature request create separate dictionaries for mailboxes?
            File f = new File("/tmp/"  + dataSource.getName());
            FileOutputStream fos = new FileOutputStream(f);
            byte[] buf = new byte[4096];
            int bytesRead;
            while((bytesRead = is.read(buf)) != -1){
                fos.write(buf, 0, bytesRead);
            }
            fos.close();
            return f;

        } catch (IOException e) {
            logger.error("Error while creating files.", e);
            return null;
        }
    }
}
