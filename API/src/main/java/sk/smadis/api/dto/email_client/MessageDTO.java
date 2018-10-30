package sk.smadis.api.dto.email_client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public class MessageDTO {
    @Size(min = 1)
    @NotNull
    private List<HeaderDTO> headers = new ArrayList<>();

    private MultipartDTO multipartBody;

    private TextDTO bodyText;

    private String from;

    private List<String> to;

    private List<String> replyTo;

    private List<String> bcc;

    private List<String> cc;

    public MessageDTO() {
    }

    @JsonCreator
    public MessageDTO(final @JsonProperty("headers") List<HeaderDTO> headers,
                      final @JsonProperty("bodyMultipart") MultipartDTO multipartBody,
                      final @JsonProperty("bodyText") TextDTO bodyText,
                      final @JsonProperty("from") String from,
                      final @JsonProperty("to") List<String> to,
                      final @JsonProperty("replyTo") List<String> replyTo,
                      final @JsonProperty("bcc") List<String> bcc,
                      final @JsonProperty("cc") List<String> cc) {
        this.headers = headers;
        this.multipartBody = multipartBody;
        this.bodyText = bodyText;
        this.from = from;
        this.to = to;
        this.replyTo = replyTo;
        this.bcc = bcc;
        this.cc = cc;
    }

    public List<HeaderDTO> getHeaders() {
        return headers;
    }

    public void setHeaders(List<HeaderDTO> headers) {
        this.headers = headers;
    }

    public MultipartDTO getMultipartBody() {
        return multipartBody;
    }

    public void setMultipartBody(MultipartDTO multipartBody) {
        this.multipartBody = multipartBody;
    }

    public TextDTO getBodyText() {
        return bodyText;
    }

    public void setBodyText(TextDTO bodyText) {
        this.bodyText = bodyText;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public List<String> getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(List<String> replyTo) {
        this.replyTo = replyTo;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }
}
