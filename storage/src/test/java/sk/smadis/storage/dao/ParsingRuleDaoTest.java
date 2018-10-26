package sk.smadis.storage.dao;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import sk.smadis.storage.entity.Mailbox;
import sk.smadis.storage.entity.ParsedData;
import sk.smadis.storage.entity.ParsingRule;
import sk.smadis.storage.entity.enums.EmailComponent;
import sk.smadis.storage.entity.enums.ParsingType;
import sk.smadis.storage.utils.EntityFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@RunWith(Arquillian.class)
public class ParsingRuleDaoTest extends AbstractDaoTest {
    @Test
    public void testCreateParsingRule(){
        ParsingRule parsingRule = new EntityFactory.ParsingRuleBuilder()
                .name("parsingRule")
                .parsingType(ParsingType.TEXT)
                .emailComponent(EmailComponent.ATTACHMENT)
                .rule("rule1").build();
        parsingRule.setMailbox(mailbox1);

        parsingRuleDao.create(parsingRule);

        ParsingRule created = parsingRuleDao.findById(parsingRule.getId());
        Mailbox mailbox = mailboxDao.findById(mailbox1.getId());

        assertThat(created).isEqualToComparingFieldByFieldRecursively(parsingRule);

        assertThat(mailbox.getParsingRules()).contains(parsingRule);
    }

    @Test
    public void testUpdateParsingRule(){
        Long id = parsingRule1.getId();
        parsingRule1.setRule("updated rule");
        parsingRule1.setName("updated name");
        parsingRule1.setComponent(EmailComponent.HEADERS);

        parsingRuleDao.update(parsingRule1);

        ParsingRule parsingRule = parsingRuleDao.findById(id);
        Mailbox mailbox = mailboxDao.findById(mailbox1.getId());

        assertThat(parsingRule.getName()).isEqualTo("updated name");
        assertThat(parsingRule.getRule()).isEqualTo("updated rule");
        assertThat(parsingRule.getComponent()).isEqualByComparingTo(EmailComponent.HEADERS);
        assertThat(parsingRule.getMailbox()).isEqualToComparingFieldByField(mailbox);

        assertThat(mailbox.getParsingRules()).contains(parsingRule);
    }

    @Test
    public void testDeleteParsingRule(){
        Long id = parsingRule1.getId();
        parsingRuleDao.delete(parsingRule1);

        assertThat(parsingRuleDao.findById(id)).isNull();

        Mailbox mailbox = mailboxDao.findById(mailbox1.getId());
        assertThat(mailbox).isNotNull();
        assertThat(mailbox.getParsingRules()).doesNotContain(parsingRule1);

        ParsedData deletedParsedData1 = parsedDataDao.findById(parsedData1.getId());

        assertThat(deletedParsedData1).isNull();
    }

    @Test
    public void testFindParsingRuleById(){
        ParsingRule parsingRule = parsingRuleDao.findById(parsingRule1.getId());

        assertThat(parsingRule).isEqualToComparingFieldByFieldRecursively(parsingRule1);
    }

    @Test
    public void testFindParsingRuleByIdNotInDB(){
        assertThat(parsingRuleDao.findById(100000L)).isNull();
    }

    @Test
    public void testGetAllParsingRules(){
        List<ParsingRule> parsingRules = parsingRuleDao.getAllParsingRules();

        assertThat(parsingRules).containsOnly(parsingRule1);
    }
}
