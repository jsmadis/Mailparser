package sk.smadis.storage.dao;

import sk.smadis.storage.entity.Mailbox;

import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public interface MailboxDao {

    /**
     * Saves(creates) mailbox to database
     *
     * @param mailbox Mailbox to create
     * @return Created mailbox
     */
    Mailbox create(Mailbox mailbox);

    /**
     * Updates mailbox
     *
     * @param mailbox Mailbox to update
     * @return Updated mailbox
     */
    Mailbox update(Mailbox mailbox);

    /**
     * Deletes mailbox from database
     *
     * @param id ID of mailbox to delete
     * @return Deleted mailbox
     */
    Mailbox delete(Long id);

    /**
     * Gets mailbox by ID
     *
     * @param id ID of mailbox
     * @return Mailbox
     */
    Mailbox findById(Long id);

    /**
     * Gets all mailboxes in database
     *
     * @return List of Mailboxes
     */
    List<Mailbox> getAllMailboxes();

    /**
     * Finds mailbox by unique name
     *
     * @param name Unique name of mailbox
     * @return Mailbox
     */
    Mailbox findByName(String name);
}
