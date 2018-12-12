package sk.smadis.storage.dao.impl;

import org.apache.log4j.Logger;
import sk.smadis.storage.dao.ParsedDataDao;
import sk.smadis.storage.entity.ParsedData;
import sk.smadis.storage.entity.ParsingRule;
import sk.smadis.storage.entity.StoredMessage;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Stateless
public class ParsedDataDaoImpl implements ParsedDataDao {

    private Logger logger = Logger.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager em;


    @Override
    public ParsedData create(ParsedData parsedData) {
        if (parsedData == null) {
            throw new IllegalArgumentException("ParsedData is null");
        }
        if (parsedData.getId() != null) {
            throw new IllegalArgumentException("ParsedData is already in DB");
        }
        logger.info("Creating parsed data: " + parsedData);

        StoredMessage storedMessage = em.find(StoredMessage.class, parsedData.getStoredMessage().getId());
        ParsingRule parsingRule = em.find(ParsingRule.class, parsedData.getParsingRule().getId());

        parsedData.setStoredMessage(storedMessage);
        parsedData.setParsingRule(parsingRule);
        storedMessage.addParsedData(parsedData);
        parsingRule.addParsedData(parsedData);

        em.persist(parsedData);
        em.flush();
        return parsedData;
    }

    @Override
    public ParsedData update(ParsedData parsedData) {
        if (parsedData == null) {
            throw new IllegalArgumentException("ParsedData is null");
        }
        if (parsedData.getId() == null) {
            throw new IllegalArgumentException("ParsedData is not in DB");
        }
        logger.info("Updating parsed data: " + parsedData);
        em.merge(parsedData);
        em.flush();
        return parsedData;
    }

    @Override
    public ParsedData delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is null");
        }
        ParsedData parsedData = em.find(ParsedData.class, id);
        if (parsedData == null) return null;
        if (parsedData.getParsingRule() != null) {
            ParsingRule parsingRule = parsedData.getParsingRule();
            parsingRule.removeParsedData(parsedData);
            parsedData.setParsingRule(null);
            em.merge(parsingRule);
        }
        if (parsedData.getStoredMessage() != null) {
            StoredMessage storedMessage = parsedData.getStoredMessage();
            storedMessage.removeParsedData(parsedData);
            parsedData.setStoredMessage(null);
            em.merge(storedMessage);
        }
        logger.info("Deleting parsed data: " + parsedData);
        em.remove(em.merge(parsedData));
        em.flush();
        return parsedData;
    }

    @Override
    public ParsedData findById(Long id) {
        logger.info("Find parsed data by id: " + id);
        return em.find(ParsedData.class, id);
    }

    @Override
    public List<ParsedData> getAllParsedData() {
        logger.info("Get all parsed data.");
        return em.createQuery("select p from ParsedData p", ParsedData.class)
                .getResultList();
    }
}
