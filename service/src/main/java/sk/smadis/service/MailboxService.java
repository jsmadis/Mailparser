package sk.smadis.service;

import sk.smadis.service.exceptions.MailparserServiceException;
import sk.smadis.storage.entity.Mailbox;

import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public interface MailboxService {
    Mailbox create(Mailbox mailbox) throws MailparserServiceException;

    Mailbox update(Mailbox mailbox)throws MailparserServiceException;

    void delete(Mailbox mailbox)throws MailparserServiceException;

    Mailbox findByName(String name)throws MailparserServiceException;

    Mailbox findById(Long id)throws MailparserServiceException;

    List<Mailbox> getAllMailboxes()throws MailparserServiceException;
}
