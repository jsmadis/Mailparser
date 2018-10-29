package sk.smadis.api.facade;

import sk.smadis.api.dto.mailparser.StoredMessageDTO;

import java.util.Calendar;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public interface StoredMessageFacade {
    StoredMessageDTO create(StoredMessageDTO storedMessageDTO);

    void update(StoredMessageDTO storedMessageDTO);

    void delete(StoredMessageDTO storedMessageDTO);

    StoredMessageDTO findById(Long id);

    List<StoredMessageDTO> getAllStoredMessages();

    List<StoredMessageDTO> getLastMessages(int count);

    List<StoredMessageDTO> getMessagesAfter(Calendar date);
}
