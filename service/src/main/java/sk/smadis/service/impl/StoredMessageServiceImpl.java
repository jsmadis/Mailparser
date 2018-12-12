package sk.smadis.service.impl;

import sk.smadis.service.StoredMessageService;
import sk.smadis.service.exceptions.MailparserServiceException;
import sk.smadis.storage.dao.StoredMessageDao;
import sk.smadis.storage.entity.StoredMessage;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Calendar;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Stateless
public class StoredMessageServiceImpl implements StoredMessageService {
    @Inject
    private StoredMessageDao storedMessageDao;

    @Override
    public StoredMessage create(StoredMessage storedMessage) throws MailparserServiceException {
        if (storedMessage == null) {
            throw new IllegalArgumentException("Message shouldn't be null.");
        }
        try {
            return storedMessageDao.create(storedMessage);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public StoredMessage update(StoredMessage message) throws MailparserServiceException {
        if (message == null) {
            throw new IllegalArgumentException("Message shouldn't be null.");
        }
        try {
            return storedMessageDao.update(message);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public void delete(Long id) throws MailparserServiceException {
        if (id == null) {
            throw new IllegalArgumentException("Message id shouldn't be null.");
        }
        try {
            storedMessageDao.delete(id);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public StoredMessage findById(Long id) throws MailparserServiceException {
        if (id == null) {
            throw new IllegalArgumentException("Message id shouldn't be null.");
        }
        try {
            return storedMessageDao.findById(id);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public List<StoredMessage> getAllStoredMessages() throws MailparserServiceException {
        try {
            return storedMessageDao.getAllStoredMessages();
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public List<StoredMessage> getLastStoredMessages(int count) {
        try {
            return storedMessageDao.getLastStoredMessages(count);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public List<StoredMessage> getStoredMessagesAfter(Calendar date) {
        try {
            return storedMessageDao.getStoredMessagesAfter(date);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }
}
