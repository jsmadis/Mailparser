package sk.smadis.api.dto.mailparser.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import sk.smadis.api.dto.mailparser.MailboxDTO;
import sk.smadis.api.dto.mailparser.enums.EmailComponent;
import sk.smadis.api.dto.mailparser.enums.FileLanguage;
import sk.smadis.api.dto.mailparser.enums.ParsingType;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public class ParsingRuleCreateDTO {
    @NotNull
    private String name;

    @NotNull
    private ParsingType parsingType;

    @NotNull
    private EmailComponent component;

    private String rule;

    private MailboxDTO mailbox;

    private FileLanguage fileLanguage = FileLanguage.ENG;


    public ParsingRuleCreateDTO() {
    }

    @JsonCreator
    public ParsingRuleCreateDTO(final @JsonProperty("name") String name,
                                final @JsonProperty("parsingType") ParsingType parsingType,
                                final @JsonProperty("component") EmailComponent component,
                                final @JsonProperty("rule") String rule,
                                final @JsonProperty("fileLanguage") FileLanguage fileLanguage,
                                final @JsonProperty("mailbox") MailboxDTO mailbox) {
        this.name = name;
        this.parsingType = parsingType;
        this.component = component;
        this.rule = rule;
        this.fileLanguage = fileLanguage;
        this.mailbox = mailbox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParsingType getParsingType() {
        return parsingType;
    }

    public void setParsingType(ParsingType parsingType) {
        this.parsingType = parsingType;
    }

    public EmailComponent getComponent() {
        return component;
    }

    public void setComponent(EmailComponent component) {
        this.component = component;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public MailboxDTO getMailbox() {
        return mailbox;
    }

    public void setMailbox(MailboxDTO mailbox) {
        this.mailbox = mailbox;
    }

    public FileLanguage getFileLanguage() {
        return fileLanguage;
    }

    public void setFileLanguage(FileLanguage fileLanguage) {
        this.fileLanguage = fileLanguage;
    }
}
