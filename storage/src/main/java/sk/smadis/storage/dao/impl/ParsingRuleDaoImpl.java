package sk.smadis.storage.dao.impl;

import org.apache.log4j.Logger;
import sk.smadis.storage.dao.ParsingRuleDao;
import sk.smadis.storage.entity.Mailbox;
import sk.smadis.storage.entity.ParsedData;
import sk.smadis.storage.entity.ParsingRule;
import sk.smadis.storage.entity.StoredMessage;
import sk.smadis.storage.entity.enums.EmailComponent;
import sk.smadis.storage.entity.enums.ParsingType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Stateless
public class ParsingRuleDaoImpl implements ParsingRuleDao {

    private Logger logger = Logger.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public ParsingRule create(ParsingRule parsingRule) {
        if (parsingRule == null) {
            throw new IllegalArgumentException("Parsing rule is null");
        }
        if (parsingRule.getId() != null) {
            throw new IllegalArgumentException("Parsing rules id should be null");
        }
        checkCompatibility(parsingRule);

        logger.info("Creating parsing rule: " + parsingRule);

        Mailbox mailbox = em.find(Mailbox.class, parsingRule.getMailbox().getId());

        parsingRule.setMailbox(mailbox);
        mailbox.addParsingRule(parsingRule);

        em.persist(parsingRule);
        em.flush();
        return parsingRule;
    }

    @Override
    public ParsingRule update(ParsingRule parsingRule) {
        if (parsingRule == null) {
            throw new IllegalArgumentException("Parsing rule is null");
        }
        if (parsingRule.getId() == null) {
            throw new IllegalArgumentException("Parsing rule id shouldn't be null");
        }
        checkCompatibility(parsingRule);

        logger.info("Updating parsing rule: " + parsingRule);
        em.merge(parsingRule);
        em.flush();
        return parsingRule;
    }

    @Override
    public ParsingRule delete(ParsingRule parsingRule) {
        if (parsingRule == null) {
            throw new IllegalArgumentException("Parsing rule is null");
        }
        em.setFlushMode(FlushModeType.COMMIT);
        if (!parsingRule.getParsedData().isEmpty()) {
            deleteParsedData(parsingRule.getParsedData());
        }

        logger.info("Deleting parsing rule:" + parsingRule);

        Mailbox mailbox = parsingRule.getMailbox();
        mailbox.removeParsingRule(parsingRule);
        parsingRule.setMailbox(null);

        em.merge(mailbox);
        em.remove(em.merge(parsingRule));
        em.flush();
        return parsingRule;
    }

    @Override
    public ParsingRule findById(Long id) {
        logger.info("Find parsing rule by id: " + id);
        return em.find(ParsingRule.class, id);
    }

    @Override
    public List<ParsingRule> getAllParsingRules() {
        logger.info("Get all parsing rules.");
        return em.createQuery("select p from ParsingRule p", ParsingRule.class)
                .getResultList();
    }

    private void deleteParsedData(List<ParsedData> parsedDataList) {
        for (int i = 0; i < parsedDataList.size(); i++) {
            ParsedData parsedData = parsedDataList.get(i);
            if (parsedData.getStoredMessage() != null) {
                StoredMessage storedMessage = parsedData.getStoredMessage();
                storedMessage.removeParsedData(parsedData);
                parsedData.setStoredMessage(null);
            }
            parsedData.getParsingRule().removeParsedData(parsedData);
            parsedData.setParsingRule(null);
            em.remove(em.merge(parsedData));
        }
    }

    private void checkCompatibility(ParsingRule parsingRule) {
        if (parsingRule.getParsingType().equals(ParsingType.HTML) &&
                (parsingRule.getComponent().equals(EmailComponent.HEADERS) ||
                        parsingRule.getComponent().equals(EmailComponent.SUBJECT))) {
            throw new IllegalArgumentException("Parsing type html is incompatible with headers or subject");
        }
    }
}
