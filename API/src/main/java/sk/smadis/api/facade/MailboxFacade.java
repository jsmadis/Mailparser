package sk.smadis.api.facade;

import sk.smadis.api.dto.mailparser.MailboxDTO;
import sk.smadis.api.dto.mailparser.create.MailboxCreateDTO;

import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public interface MailboxFacade {
    MailboxDTO create(MailboxCreateDTO mailboxCreateDTO);

    void update(MailboxDTO mailboxDTO);

    void delete(MailboxDTO mailboxDTO);

    MailboxDTO findById(Long id);

    MailboxDTO findByName(String name);

    List<MailboxDTO> getAllMailboxes();
}
