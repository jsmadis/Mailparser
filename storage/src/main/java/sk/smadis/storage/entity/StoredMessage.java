package sk.smadis.storage.entity;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Entity
public class StoredMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String messageID;

    @OneToMany(mappedBy = "storedMessage",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<ParsedData> parsedData = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "mailbox_id")
    private Mailbox mailbox;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar receivedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public List<ParsedData> getParsedData() {
        return parsedData;
    }

    public void setParsedData(List<ParsedData> parsedData) {
        this.parsedData = parsedData;
    }

    public void addParsedData(ParsedData parsedData) {
        this.parsedData.add(parsedData);
    }


    public void removeParsedData(ParsedData parsedData) {
        this.parsedData.remove(parsedData);
    }

    public Mailbox getMailbox() {
        return mailbox;
    }

    public void setMailbox(Mailbox mailbox) {
        this.mailbox = mailbox;
    }

    public Calendar getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Calendar receivedDate) {
        this.receivedDate = receivedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoredMessage)) return false;
        StoredMessage that = (StoredMessage) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getMessageID(), that.getMessageID()) &&
                Objects.equals(getParsedData(), that.getParsedData()) &&
                Objects.equals(getReceivedDate(), that.getReceivedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMessageID(), getParsedData(), getReceivedDate());
    }
}
