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
public class MultipartDTO {
    @Size(min = 1)
    @NotNull
    private List<BodyPartDTO> bodyparts = new ArrayList<>();

    public MultipartDTO() {
    }

    @JsonCreator
    public MultipartDTO(final @JsonProperty("bodyparts") List<BodyPartDTO> bodyparts) {
        this.bodyparts = bodyparts;
    }

    public List<BodyPartDTO> getBodyparts() {
        return bodyparts;
    }

    public void setBodyparts(List<BodyPartDTO> bodyparts) {
        this.bodyparts = bodyparts;
    }
}
