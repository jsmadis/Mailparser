package sk.smadis.api.dto.email_client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public class AttachmentDTO {
    @NotNull
    private String filename;

    @NotNull
    private String type;

    @NotNull
    private String encoding;

    private String pathToFile;

    public AttachmentDTO() {
    }

    @JsonCreator
    public AttachmentDTO(final @JsonProperty("filename") String filename,
                         final @JsonProperty("type") String type,
                         final @JsonProperty("encoding") String encoding,
                         final @JsonProperty("pathToFile") String pathToFile) {
        this.filename = filename;
        this.type = type;
        this.encoding = encoding;
        this.pathToFile = pathToFile;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }
}
