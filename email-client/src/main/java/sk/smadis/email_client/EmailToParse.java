package sk.smadis.email_client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.activation.DataSource;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class representation of javax.mail.Message for internal usage.
 * <p>
 * The message is accessible only when connected to mail-server is open.
 * Therefore I needed to create a class representation which will be separate from mail-server.
 *
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public class EmailToParse {

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<String> to;

    private String messageId;

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<String> headers;

    private String subject;

    private String bodyHtml;

    private String bodyPlainText;

    @JsonIgnore
    private List<DataSource> attachments = new ArrayList<>();

    @JsonIgnore
    private List<File> files = new ArrayList<>();

    @JsonIgnore
    private Date receivedDate;

    public List<String> getTo() {
        return to;
    }

    public EmailToParse to(List<String> to) {
        this.to = to;
        return this;
    }

    public String getMessageId() {
        return messageId;
    }

    public EmailToParse messageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public EmailToParse headers(List<String> headers) {
        this.headers = headers;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public EmailToParse subject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public EmailToParse bodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
        return this;
    }

    public String getBodyPlainText() {
        return bodyPlainText;
    }

    public EmailToParse bodyPlainText(String bodyPlainText) {
        this.bodyPlainText = bodyPlainText;
        return this;
    }

    public List<DataSource> getAttachments() {
        return attachments;
    }

    public EmailToParse attachments(List<DataSource> attachments) {
        this.attachments = attachments;
        return this;
    }

    public EmailToParse addAttachment(DataSource attachment) {
        attachments.add(attachment);
        return this;
    }

    public EmailToParse receivedDate(Date date) {
        receivedDate = date;
        return this;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public EmailToParse files(List<File> files) {
        this.files = files;
        return this;
    }

    public EmailToParse addFile(File file) {
        files.add(file);
        return this;
    }

    public List<File> getFiles() {
        return files;
    }
}
