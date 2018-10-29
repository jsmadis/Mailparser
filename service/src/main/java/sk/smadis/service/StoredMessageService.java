package sk.smadis.service;

import sk.smadis.service.exceptions.MailparserServiceException;
import sk.smadis.storage.entity.StoredMessage;

import java.util.Calendar;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public interface StoredMessageService {
    StoredMessage create(StoredMessage storedMessage, Long mailboxID) throws MailparserServiceException;

    StoredMessage update(StoredMessage storedMessage) throws MailparserServiceException;

    void delete(StoredMessage storedMessage) throws MailparserServiceException;

    StoredMessage findById(Long id) throws MailparserServiceException;

    List<StoredMessage> getAllStoredMessages() throws MailparserServiceException;

    List<StoredMessage> getLastStoredMessages(int count) throws MailparserServiceException;

    List<StoredMessage> getStoredMessagesAfter(Calendar date) throws MailparserServiceException;
}
