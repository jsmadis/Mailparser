package sk.smadis.storage.dao.impl;

import org.apache.log4j.Logger;
import sk.smadis.storage.dao.StoredMessageDao;
import sk.smadis.storage.entity.Mailbox;
import sk.smadis.storage.entity.ParsedData;
import sk.smadis.storage.entity.StoredMessage;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Stateless
public class StoredMessageDaoImpl implements StoredMessageDao {
    private Logger logger = Logger.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public StoredMessage create(StoredMessage storedMessage) {
        if(storedMessage == null){
            throw new IllegalArgumentException("Message is null");
        }
        if(storedMessage.getId() != null) {
            throw new IllegalArgumentException("Message is already in DB");
        }
        logger.info("Creating message: " + storedMessage);
        Mailbox mailbox = em.merge(storedMessage.getMailbox());
        mailbox.addStoredMessage(storedMessage);

        em.persist(storedMessage);
        em.flush();
        return storedMessage;
    }

    @Override
    public StoredMessage update(StoredMessage message) {
        if(message == null){
            throw new IllegalArgumentException("Message is null");
        }
        if(message.getId() == null){
            throw new IllegalArgumentException("Message is not in DB");
        }
        logger.info("Updating message: " + message);
        em.merge(message);
        em.flush();
        return message;
    }

    @Override
    public StoredMessage delete(StoredMessage storedMessage) {
        if(storedMessage == null){
            throw new IllegalArgumentException("Message is null");
        }
        if(!storedMessage.getParsedData().isEmpty()){
            deleteParsedData(storedMessage.getParsedData());
        }
        logger.info("Deleting message: " + storedMessage);
        Mailbox mailbox = storedMessage.getMailbox();
        mailbox.getStoredMessages().remove(storedMessage);
        storedMessage.setMailbox(null);
        em.merge(mailbox);
        em.remove(em.merge(storedMessage));
        em.flush();
        return storedMessage;
    }

    @Override
    public StoredMessage findById(Long id) {
        logger.info("Find message by id: " + id);
        return em.find(StoredMessage.class, id);
    }

    @Override
    public List<StoredMessage> getAllStoredMessages() {
        logger.info("Get all messages.");
        return em.createQuery("select m from StoredMessage m", StoredMessage.class)
                .getResultList();
    }

    @Override
    public List<StoredMessage> getLastStoredMessages(int count) {
        logger.info("Get last " + count + " messages.");
        return em.createQuery("select m from StoredMessage m order by m.id desc", StoredMessage.class)
                .setMaxResults(count)
                .getResultList();
    }

    @Override
    public List<StoredMessage> getStoredMessagesAfter(Calendar date) {
        logger.info("Get messages after: " + date);
        return em.createQuery(
                "select m from StoredMessage m where m.receivedDate >= :date",
                StoredMessage.class).setParameter("date", date).getResultList();
    }


    private void deleteParsedData(List<ParsedData> parsedDataList){
        for (int i = 0; i < parsedDataList.size(); i++) {
            ParsedData parsedData = parsedDataList.get(i);
            if (parsedData.getParsingRule() != null) {
                parsedData.getParsingRule().removeParsedData(parsedData);
            }
            parsedData.setParsingRule(null);
            parsedData.getStoredMessage().removeParsedData(parsedData);
            parsedData.setStoredMessage(null);
            em.remove(em.merge(parsedData));
        }
    }

}
