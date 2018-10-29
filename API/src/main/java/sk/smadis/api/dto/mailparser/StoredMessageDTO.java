package sk.smadis.api.dto.mailparser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class StoredMessageDTO {
    private Long id;

    private String messageID;

    private List<ParsedDataDTO> parsedData = new ArrayList<>();

    private MailboxDTO mailbox;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Calendar receivedDate;

    public StoredMessageDTO() {
    }

    @JsonCreator
    public StoredMessageDTO(final @JsonProperty("id") Long id,
                            final @JsonProperty("messageID") String messageId,
                            final @JsonProperty("parsedData") List<ParsedDataDTO> parsedData,
                            final @JsonProperty("receivedDate") Calendar receivedDate,
                            final @JsonProperty("mailbox") MailboxDTO mailbox) {
        this.id = id;
        this.messageID = messageId;
        this.receivedDate = receivedDate;
        this.parsedData = parsedData;
        this.mailbox = mailbox;
    }

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

    public List<ParsedDataDTO> getParsedData() {
        return parsedData;
    }

    public void setParsedData(List<ParsedDataDTO> parsedData) {
        this.parsedData = parsedData;
    }

    public MailboxDTO getMailbox() {
        return mailbox;
    }

    public void setMailbox(MailboxDTO mailbox) {
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
        if (!(o instanceof StoredMessageDTO)) return false;
        StoredMessageDTO that = (StoredMessageDTO) o;
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
