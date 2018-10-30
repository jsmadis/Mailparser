package sk.smadis.api.dto.email_client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public class TextDTO {
    @NotNull
    private String text;

    private String charset;

    private String subtype = "plain";

    public TextDTO() {
    }

    @JsonCreator
    public TextDTO(final @JsonProperty("text") String text,
                   final @JsonProperty("charset") String charset,
                   final @JsonProperty("subtype") String subtype) {
        this.text = text;
        this.charset = charset;
        if (subtype != null) {
            this.subtype = subtype;
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }
}
