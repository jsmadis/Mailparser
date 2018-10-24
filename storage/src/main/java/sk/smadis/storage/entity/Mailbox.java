package sk.smadis.storage.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */

@Entity
public class Mailbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private boolean resendRaw = false;

    @OneToMany(mappedBy = "mailbox", cascade = {CascadeType.PERSIST, CascadeType.REMOVE,
            CascadeType.MERGE}, orphanRemoval = true)
    private List<ParsingRule> parsingRules = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "mailbox", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<StoredMessage> storedMessages = new ArrayList<>();

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

    public boolean isResendRaw() {
        return resendRaw;
    }

    public void setResendRaw(boolean resendRaw) {
        this.resendRaw = resendRaw;
    }

    public List<ParsingRule> getParsingRules() {
        return parsingRules;
    }

    public void setParsingRules(List<ParsingRule> parsingRules) {
        this.parsingRules = parsingRules;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<StoredMessage> getStoredMessages() {
        return storedMessages;
    }

    public void setStoredMessages(List<StoredMessage> storedMessages) {
        this.storedMessages = storedMessages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mailbox)) return false;
        Mailbox mailbox = (Mailbox) o;
        return isResendRaw() == mailbox.isResendRaw() &&
                Objects.equals(getId(), mailbox.getId()) &&
                Objects.equals(getName(), mailbox.getName()) &&
                Objects.equals(getParsingRules(), mailbox.getParsingRules()) &&
                Objects.equals(getStoredMessages(), mailbox.getStoredMessages());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), isResendRaw(), getParsingRules(), getStoredMessages());
    }
}
