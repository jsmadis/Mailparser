package sk.smadis.service.impl;

import sk.smadis.service.MailboxService;
import sk.smadis.service.exceptions.MailparserServiceException;
import sk.smadis.storage.dao.MailboxDao;
import sk.smadis.storage.entity.Mailbox;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Stateless
public class MailboxServiceImpl implements MailboxService {
    @Inject
    private MailboxDao mailboxDao;

    @Override
    public Mailbox create(Mailbox mailbox) throws MailparserServiceException {
        if (mailbox == null) {
            throw new IllegalArgumentException("Mailbox shouldn't be null.");
        }
        try {
            mailbox.setName(createUniqueLocalName(mailbox.getName()));
            return mailboxDao.create(mailbox);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public Mailbox update(Mailbox mailbox) throws MailparserServiceException {
        if (mailbox == null) {
            throw new IllegalArgumentException("Mailbox shouldn't be null.");
        }
        try {
            return mailboxDao.update(mailbox);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public void delete(Mailbox mailbox) throws MailparserServiceException {
        if (mailbox == null) {
            throw new IllegalArgumentException("Mailbox shouldn't be null.");
        }
        try {
            mailboxDao.delete(mailbox);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public Mailbox findByName(String name) throws MailparserServiceException {
        if (name == null) {
            throw new IllegalArgumentException("Name shouldn't be null.");
        }
        try {
            return mailboxDao.findByName(name);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public Mailbox findById(Long id) throws MailparserServiceException {
        if (id == null) {
            throw new IllegalArgumentException("Id shouldn't be null.");
        }
        try {
            return mailboxDao.findById(id);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public List<Mailbox> getAllMailboxes() throws MailparserServiceException {
        try {
            return mailboxDao.getAllMailboxes();
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }


    /**
     * Creates unique valid name for mailbox.
     *
     * @param prefix mailbox name prefix
     * @return Mailbox name
     */
    private String createUniqueLocalName(String prefix) {
        if (prefix == null) prefix = "";
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[10];
        secureRandom.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        String name = encoder.encodeToString(bytes);
        return prefix + name;
    }
}
