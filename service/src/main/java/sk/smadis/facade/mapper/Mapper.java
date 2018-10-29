package sk.smadis.facade.mapper;

import org.mapstruct.Context;
import org.mapstruct.factory.Mappers;
import sk.smadis.api.dto.mailparser.MailboxDTO;
import sk.smadis.api.dto.mailparser.ParsedDataDTO;
import sk.smadis.api.dto.mailparser.ParsingRuleDTO;
import sk.smadis.api.dto.mailparser.StoredMessageDTO;
import sk.smadis.api.dto.mailparser.UserDTO;
import sk.smadis.api.dto.mailparser.create.MailboxCreateDTO;
import sk.smadis.api.dto.mailparser.create.ParsingRuleCreateDTO;
import sk.smadis.api.dto.mailparser.create.UserCreateDTO;
import sk.smadis.storage.entity.Mailbox;
import sk.smadis.storage.entity.ParsedData;
import sk.smadis.storage.entity.ParsingRule;
import sk.smadis.storage.entity.StoredMessage;
import sk.smadis.storage.entity.User;

import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@org.mapstruct.Mapper()
public interface Mapper {
    Mapper INSTANCE = Mappers.getMapper(Mapper.class);

    UserDTO userToDTOUser(User user,
                          @Context AvoidCycleMappingContext context);

    List<UserDTO> listUsersToDTOUsers(List<User> users,
                                      @Context AvoidCycleMappingContext context);

    User userDTOToUser(UserDTO userDTO,
                       @Context AvoidCycleMappingContext context);

    User createDTOToUser(UserCreateDTO userDTO,
                         @Context AvoidCycleMappingContext context);

    MailboxDTO mailboxToDTOMailbox(Mailbox mailbox,
                                   @Context AvoidCycleMappingContext context);

    List<MailboxDTO> listMailboxesToDto(List<Mailbox> mailboxes,
                                        @Context AvoidCycleMappingContext context);

    Mailbox mailboxDTOToMailbox(MailboxDTO mailboxDTO,
                                @Context AvoidCycleMappingContext context);

    Mailbox createDtoToMailbox(MailboxCreateDTO mailboxCreateDTO,
                               @Context AvoidCycleMappingContext context);

    ParsingRuleDTO parsingRuleToDto(@Context AvoidCycleMappingContext context,
                                    ParsingRule parsingRule);

    List<ParsingRuleDTO> listParsingRulesToDto(@Context AvoidCycleMappingContext context,
                                               List<ParsingRule> parsingRules);

    ParsingRule dtoToParsingRule(@Context AvoidCycleMappingContext context,
                                 ParsingRuleDTO parsingRuleDTO);

    ParsingRule createDtoToParsingRule(ParsingRuleCreateDTO parsingRuleCreateDTO,
                                       @Context AvoidCycleMappingContext context);

    ParsedDataDTO parsedDataToDto(ParsedData parsedData,
                                  @Context AvoidCycleMappingContext context);

    List<ParsedDataDTO> listParsedDataToDto(List<ParsedData> parsedData,
                                            @Context AvoidCycleMappingContext context);

    ParsedData dtoToParsedData(ParsedDataDTO parsedDataDTO,
                               @Context AvoidCycleMappingContext context);

    StoredMessageDTO messageToDTOMessage(StoredMessage storedMessage,
                                         @Context AvoidCycleMappingContext context);

    StoredMessage messageDTOToMessage(StoredMessageDTO messageDTO,
                                      @Context AvoidCycleMappingContext context);

    List<StoredMessageDTO> listOfMessagesToDTOMessages(List<StoredMessage> storedMessages,
                                                       @Context AvoidCycleMappingContext context);

}
