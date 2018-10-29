package sk.smadis.facade;

import sk.smadis.api.dto.mailparser.StoredMessageDTO;
import sk.smadis.api.facade.StoredMessageFacade;
import sk.smadis.facade.mapper.AvoidCycleMappingContext;
import sk.smadis.facade.mapper.Mapper;
import sk.smadis.service.StoredMessageService;
import sk.smadis.storage.entity.StoredMessage;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Calendar;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Stateless
public class StoredMessageFacadeImpl implements StoredMessageFacade {
    @Inject
    private StoredMessageService storedMessageService;

    @Override
    public StoredMessageDTO create(StoredMessageDTO storedMessageDTO) {
        StoredMessage message = Mapper.INSTANCE
                .messageDTOToMessage(storedMessageDTO, new AvoidCycleMappingContext());
        StoredMessage createdMessage = storedMessageService.create(message);
        return Mapper.INSTANCE
                .messageToDTOMessage(createdMessage, new AvoidCycleMappingContext());
    }

    @Override
    public void update(StoredMessageDTO storedMessageDTO) {
        StoredMessage message = Mapper.INSTANCE
                .messageDTOToMessage(storedMessageDTO, new AvoidCycleMappingContext());
        storedMessageService.update(message);
    }

    @Override
    public void delete(StoredMessageDTO storedMessageDTO) {
        StoredMessage message = Mapper.INSTANCE
                .messageDTOToMessage(storedMessageDTO, new AvoidCycleMappingContext());
        storedMessageService.delete(message);
    }

    @Override
    public StoredMessageDTO findById(Long id) {
        StoredMessage message = storedMessageService.findById(id);
        return Mapper.INSTANCE
                .messageToDTOMessage(message, new AvoidCycleMappingContext());
    }

    @Override
    public List<StoredMessageDTO> getAllStoredMessages() {
        List<StoredMessage> messages = storedMessageService.getAllStoredMessages();
        return Mapper.INSTANCE
                .listOfMessagesToDTOMessages(messages, new AvoidCycleMappingContext());
    }

    @Override
    public List<StoredMessageDTO> getLastMessages(int count) {
        List<StoredMessage> messages = storedMessageService.getLastStoredMessages(count);
        return Mapper.INSTANCE
                .listOfMessagesToDTOMessages(messages, new AvoidCycleMappingContext());
    }

    @Override
    public List<StoredMessageDTO> getMessagesAfter(Calendar date) {
        List<StoredMessage> messages = storedMessageService.getStoredMessagesAfter(date);
        return Mapper.INSTANCE
                .listOfMessagesToDTOMessages(messages, new AvoidCycleMappingContext());
    }
}
