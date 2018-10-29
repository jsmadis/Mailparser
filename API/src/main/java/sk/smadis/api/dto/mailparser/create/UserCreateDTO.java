package sk.smadis.api.dto.mailparser.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public class UserCreateDTO {
    @NotNull
    private String name;

    private String restURL;


    public UserCreateDTO() {
    }

    @JsonCreator
    public UserCreateDTO(final @JsonProperty("name") String name,
                         final @JsonProperty("restURL") String restURL) {
        this.name = name;
        this.restURL = restURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRestURL() {
        return restURL;
    }

    public void setRestURL(String restURL) {
        this.restURL = restURL;
    }
}
