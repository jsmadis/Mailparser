package sk.smadis.storage.entity;


import sk.smadis.storage.entity.enums.EmailComponent;
import sk.smadis.storage.entity.enums.FileLanguage;
import sk.smadis.storage.entity.enums.ParsingType;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Entity
public class ParsingRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private ParsingType parsingType;

    @NotNull
    private EmailComponent component;

    private String rule;

    private FileLanguage fileLanguage = FileLanguage.ENG;

    @ManyToOne(cascade = { CascadeType.MERGE})
    @JoinColumn(name = "mailbox_id")
    private Mailbox mailbox;

    @OneToMany(mappedBy = "parsingRule",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<ParsedData> parsedData = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParsingRule)) return false;
        ParsingRule that = (ParsingRule) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                parsingType == that.parsingType &&
                component == that.component &&
                Objects.equals(rule, that.rule) &&
                fileLanguage == that.fileLanguage &&
                Objects.equals(parsedData, that.parsedData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parsingType, component, rule, fileLanguage, parsedData);
    }
}
