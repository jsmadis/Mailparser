package sk.smadis.email_client.sender;

import sk.smadis.api.dto.email_client.AttachmentDTO;
import sk.smadis.api.dto.email_client.BodyPartDTO;
import sk.smadis.api.dto.email_client.HeaderDTO;
import sk.smadis.api.dto.email_client.MessageDTO;
import sk.smadis.api.dto.email_client.MultipartDTO;
import sk.smadis.api.dto.email_client.TextDTO;
import sk.smadis.email_client.exceptions.ProcessingMessageException;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import java.io.IOException;
import java.util.List;

/**
 * Class that holds static methods for transformation between email_client DTO objects and javax.mail.Message atributes.
 *
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public class MessageSenderUtils {

    public static void fillMessage(MimeMessage message, MessageDTO messageDTO)
            throws MessagingException, ProcessingMessageException {
        fillHeaders(message, messageDTO.getHeaders());
        if (messageDTO.getMultipartBody() != null) {
            fillBody(message, messageDTO.getMultipartBody());
        }
        if (messageDTO.getBodyText() != null) {
            fillBodyText(message, messageDTO.getBodyText());
        }
        fillRecipients(message, messageDTO);
    }

    private static void fillBodyText(MimePart mimePart, TextDTO textDTO) throws MessagingException {
        mimePart.setText(textDTO.getText(), textDTO.getCharset(), textDTO.getSubtype());
    }

    private static void fillRecipients(Message message, MessageDTO messageDTO) throws MessagingException {
        message.setFrom(processEmail(messageDTO.getFrom()));
        message.setRecipients(Message.RecipientType.TO, processEmails(messageDTO.getTo()));
        if (messageDTO.getCc() != null && !messageDTO.getCc().isEmpty()) {
            message.setRecipients(Message.RecipientType.CC, processEmails(messageDTO.getCc()));
        }
        if (messageDTO.getBcc() != null && !messageDTO.getBcc().isEmpty()) {
            message.setRecipients(Message.RecipientType.BCC, processEmails(messageDTO.getBcc()));
        }
        if (messageDTO.getReplyTo() != null && !messageDTO.getBcc().isEmpty()) {
            message.setReplyTo(processEmails(messageDTO.getReplyTo()));
        }
    }

    private static void fillHeaders(Part part, List<HeaderDTO> headers) throws MessagingException {
        if (headers != null) {
            for (HeaderDTO header : headers) {
                for (String value : header.getValues()) {
                    part.addHeader(header.getName(), value);
                }
            }
        }
    }

    private static void fillBody(Message message, MultipartDTO body) throws MessagingException, ProcessingMessageException {
        Multipart multipart = fillMultipart(body);
        message.setContent(multipart);
    }

    private static Multipart fillMultipart(MultipartDTO multipartDTO) throws MessagingException, ProcessingMessageException {
        Multipart multipart = new MimeMultipart();
        for (BodyPartDTO bodypartDTO : multipartDTO.getBodyparts()) {
            BodyPart bodyPart = fillBodyPart(bodypartDTO);
            multipart.addBodyPart(bodyPart);
        }
        return multipart;
    }

    private static MimeBodyPart fillBodyPart(BodyPartDTO bodyPartDTO) throws MessagingException, ProcessingMessageException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        fillHeaders(bodyPart, bodyPartDTO.getHeaders());
        switch (bodyPartDTO.getType()) {
            case TEXT:
                fillText(bodyPart, bodyPartDTO);
                break;
            case ATTACHMENT:
                fillAttachment(bodyPart, bodyPartDTO);
                break;
            default:
                throw new ProcessingMessageException("Wrong type of bodypart");
        }
        return bodyPart;
    }

    private static void fillText(MimeBodyPart bodyPart, BodyPartDTO bodyPartDTO) throws ProcessingMessageException, MessagingException {
        if (bodyPartDTO.getAttachment() != null) {
            throw new ProcessingMessageException("Type of body part was text but attribute attachment wasn't null");
        }
        if (bodyPartDTO.getText() == null) {
            throw new ProcessingMessageException("Type of body part was text but attribute text was null");
        }
        TextDTO textDTO = bodyPartDTO.getText();
        bodyPart.setText(textDTO.getText(), textDTO.getCharset(), textDTO.getSubtype());
        bodyPart.setDisposition(Part.INLINE);
    }

    private static void fillAttachment(MimeBodyPart bodyPart, BodyPartDTO bodyPartDTO) throws ProcessingMessageException, MessagingException {
        if (bodyPartDTO.getAttachment() == null) {
            throw new ProcessingMessageException("Type of body part was attachment but attribute attachment was null");
        }
        if (bodyPartDTO.getText() != null) {
            throw new ProcessingMessageException("Type of body part was attachment but attribute text wasn't null");
        }
        AttachmentDTO attachmentDTO = bodyPartDTO.getAttachment();
        bodyPart.setFileName(attachmentDTO.getFilename());
        try {
            bodyPart.attachFile(attachmentDTO.getPathToFile(), attachmentDTO.getType(), attachmentDTO.getEncoding());
        } catch (IOException e) {
            throw new ProcessingMessageException(e.getMessage());
        }
    }

    private static InternetAddress processEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email address shouldn't be null.");
        }
        InternetAddress internetAddress = null;
        try {
            internetAddress = new InternetAddress(email);
        } catch (AddressException e) {
            throw new ProcessingMessageException(e.getMessage());
        }
        return internetAddress;
    }

    private static InternetAddress[] processEmails(List<String> emails) {
        if (emails == null) {
            throw new IllegalArgumentException("Email addresses shouldn't be null.");
        }
        if (emails.isEmpty()) {
            throw new IllegalArgumentException("Email addresses shouldn't be empty.");
        }
        InternetAddress[] internetAddresses = new InternetAddress[emails.size()];
        for (int i = 0; i < internetAddresses.length; i++) {
            internetAddresses[i] = processEmail(emails.get(i));
        }
        return internetAddresses;
    }

}
