package sk.smadis.api.dto.mailparser.create;

import sk.smadis.api.dto.mailparser.UserDTO;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public class MailboxCreateDTO {
    private String name;

    private boolean resendRaw;

    private UserDTO user;

    public MailboxCreateDTO() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isResendRaw() {
        return resendRaw;
    }

    public void setResendRaw(boolean resendRaw) {
        this.resendRaw = resendRaw;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
