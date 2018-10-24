package sk.smadis.storage.dao;

import sk.smadis.storage.entity.StoredMessage;

import java.util.Calendar;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public interface StoredMessageDao {
    /**
     * Saves(creates) stored message into database
     *
     * @param storedMessage StoreMessage
     * @return Created StoredMessage
     */
    StoredMessage create(StoredMessage storedMessage);

    /**
     * Updates stored message
     *
     * @param storedMessage StoredMessage to update
     * @return updated stored message
     */
    StoredMessage update(StoredMessage storedMessage);

    /**
     * Deletes stored message
     *
     * @param storedMessage StoredMessage to delete
     * @return Deleted StoredMessage
     */
    StoredMessage delete(StoredMessage storedMessage);

    /**
     * Finds StoredMessage by id
     *
     * @param id ID of StoredMessage
     * @return StoredMessage
     */
    StoredMessage findById(Long id);

    /**
     * Gets all StoredMessages
     *
     * @return List of StoredMessages
     */
    List<StoredMessage> getAllStoredMessages();

    /**
     * Gets {count} last messages
     *
     * @param count Count of messages
     * @return List of StoredMessages
     */
    List<StoredMessage> getLastStoredMessages(int count);

    /**
     * Gets messages after given date
     *
     * @param date Date
     * @return List of StoredMessages
     */
    List<StoredMessage> getStoredMessagesAfter(Calendar date);
}
