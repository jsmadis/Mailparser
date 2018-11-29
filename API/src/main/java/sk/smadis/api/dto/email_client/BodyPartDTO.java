package sk.smadis.api.dto.email_client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import sk.smadis.api.dto.email_client.enums.BodyPartType;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public class BodyPartDTO {

    @NotNull
    private List<HeaderDTO> headers = new ArrayList<>();

    @NotNull
    private BodyPartType type;

    private TextDTO text;

    private AttachmentDTO attachment;

    public BodyPartDTO() {
    }

    @JsonCreator
    public BodyPartDTO(final @JsonProperty("headers") List<HeaderDTO> headers,
                       final @JsonProperty("type") BodyPartType type,
                       final @JsonProperty("text") TextDTO text,
                       final @JsonProperty("attachment") AttachmentDTO attachment) {
        this.headers = headers;
        this.type = type;
        this.text = text;
        this.attachment = attachment;
    }

    public List<HeaderDTO> getHeaders() {
        return headers;
    }

    public void setHeaders(List<HeaderDTO> headers) {
        this.headers = headers;
    }

    public BodyPartType getType() {
        return type;
    }

    public void setType(BodyPartType type) {
        this.type = type;
    }

    public TextDTO getText() {
        return text;
    }

    public void setText(TextDTO text) {
        this.text = text;
    }

    public AttachmentDTO getAttachment() {
        return attachment;
    }

    public void setAttachment(AttachmentDTO attachment) {
        this.attachment = attachment;
    }
}
