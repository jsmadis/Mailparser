package sk.smadis.api.dto.mailparser;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    private Long id;

    private String name;

    private String restURL;

    private List<MailboxDTO> mailboxes = new ArrayList<>();

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<MailboxDTO> getMailboxes() {
        return mailboxes;
    }

    public void setMailboxes(List<MailboxDTO> mailboxes) {
        this.mailboxes = mailboxes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(getId(), userDTO.getId()) &&
                Objects.equals(getName(), userDTO.getName()) &&
                Objects.equals(getRestURL(), userDTO.getRestURL()) &&
                Objects.equals(getMailboxes(), userDTO.getMailboxes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getRestURL(), getMailboxes());
    }
}
