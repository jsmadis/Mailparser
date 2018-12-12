package sk.smadis.facade;

import sk.smadis.api.dto.mailparser.MailboxDTO;
import sk.smadis.api.dto.mailparser.create.MailboxCreateDTO;
import sk.smadis.api.facade.MailboxFacade;
import sk.smadis.facade.mapper.AvoidCycleMappingContext;
import sk.smadis.facade.mapper.Mapper;
import sk.smadis.service.MailboxService;
import sk.smadis.storage.entity.Mailbox;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Stateless
public class MailboxFacadeImpl implements MailboxFacade {

    @Inject
    private MailboxService mailboxService;

    @Override
    public MailboxDTO create(MailboxCreateDTO mailboxDTO) {

        Mailbox mappedMailbox = Mapper.INSTANCE.createDtoToMailbox(mailboxDTO, new AvoidCycleMappingContext());
        Mailbox mailbox = mailboxService.create(mappedMailbox);
        return Mapper.INSTANCE.mailboxToDTOMailbox(mailbox, new AvoidCycleMappingContext());
    }

    @Override
    public void update(MailboxDTO mailboxDTO) {
        Mailbox mappedMailbox = Mapper.INSTANCE.mailboxDTOToMailbox(mailboxDTO, new AvoidCycleMappingContext());
        mailboxService.update(mappedMailbox);
    }

    @Override
    public void delete(Long id) {
        mailboxService.delete(id);
    }

    @Override
    public MailboxDTO findById(Long id) {
        Mailbox mailbox = mailboxService.findById(id);
        return Mapper.INSTANCE.mailboxToDTOMailbox(mailbox, new AvoidCycleMappingContext());
    }

    @Override
    public MailboxDTO findByName(String name) {
        Mailbox mailbox = mailboxService.findByName(name);
        return Mapper.INSTANCE.mailboxToDTOMailbox(mailbox, new AvoidCycleMappingContext());
    }

    @Override
    public List<MailboxDTO> getAllMailboxes() {
        return Mapper.INSTANCE.listMailboxesToDto(mailboxService.getAllMailboxes(), new AvoidCycleMappingContext());
    }
}
