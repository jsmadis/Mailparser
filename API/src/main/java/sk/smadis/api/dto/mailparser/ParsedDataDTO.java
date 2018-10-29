package sk.smadis.api.dto.mailparser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class ParsedDataDTO {
    private Long id;

    private ParsingRuleDTO parsingRule;

    private StoredMessageDTO storedMessage;

    private String fileName;

    private List<String> values;

    public ParsedDataDTO() {
    }

    @JsonCreator
    public ParsedDataDTO(final @JsonProperty("id") Long id,
                         final @JsonProperty("storedMessage") StoredMessageDTO storedMessage,
                         final @JsonProperty("parsingRule") ParsingRuleDTO parsingRule,
                         final @JsonProperty("fileName") String fileName,
                         final @JsonProperty("values") List<String> values) {
        this.id = id;
        this.fileName = fileName;
        this.storedMessage = storedMessage;
        this.parsingRule = parsingRule;
        this.values = values;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParsingRuleDTO getParsingRule() {
        return parsingRule;
    }

    public void setParsingRule(ParsingRuleDTO parsingRule) {
        this.parsingRule = parsingRule;
    }

    public StoredMessageDTO getStoredMessage() {
        return storedMessage;
    }

    public void setStoredMessage(StoredMessageDTO storedMessage) {
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
        if (!(o instanceof ParsedDataDTO)) return false;
        ParsedDataDTO that = (ParsedDataDTO) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getFileName(), that.getFileName()) &&
                Objects.equals(getValues(), that.getValues());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFileName(), getValues());
    }
}
