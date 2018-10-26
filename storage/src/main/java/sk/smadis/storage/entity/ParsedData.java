package sk.smadis.storage.entity;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Entity
public class ParsedData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "parsingRule_id")
    private ParsingRule parsingRule;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "storedMessage_id")
    private StoredMessage storedMessage;

    private String fileName;

    @ElementCollection
    private List<String> values;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParsingRule getParsingRule() {
        return parsingRule;
    }

    public void setParsingRule(ParsingRule parsingRule) {
        this.parsingRule = parsingRule;
    }

    public StoredMessage getStoredMessage() {
        return storedMessage;
    }

    public void setStoredMessage(StoredMessage storedMessage) {
        this.storedMessage = storedMessage;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParsedData)) return false;
        ParsedData that = (ParsedData) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getFileName(), that.getFileName()) &&
                Objects.equals(getValues(), that.getValues());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFileName(), getValues());
    }
}
