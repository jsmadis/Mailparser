package sk.smadis.utils;

import sk.smadis.api.dto.mailparser.ParsedDataDTO;
import sk.smadis.api.dto.mailparser.ParsingRuleDTO;
import sk.smadis.api.dto.mailparser.StoredMessageDTO;
import sk.smadis.api.dto.mailparser.create.MailboxCreateDTO;
import sk.smadis.api.dto.mailparser.create.ParsingRuleCreateDTO;
import sk.smadis.api.dto.mailparser.create.UserCreateDTO;
import sk.smadis.api.dto.mailparser.enums.EmailComponent;
import sk.smadis.api.dto.mailparser.enums.ParsingType;

import java.util.Calendar;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public class DTOFactory {
    public static UserCreateDTO createUserDTO(String name){
        UserCreateDTO user = new UserCreateDTO();
        user.setName(name);
        return user;
    }

    public static MailboxCreateDTO createMailboxDTO(String name){
        MailboxCreateDTO mailboxCreateDTO = new MailboxCreateDTO();
        mailboxCreateDTO.setName(name);
        return mailboxCreateDTO;
    }

    public static ParsingRuleCreateDTO createParsingRuleDTO(String name, ParsingType parsingType,
                                                            EmailComponent emailComponents, String rule){
        ParsingRuleCreateDTO parsingRuleCreateDTO = new ParsingRuleCreateDTO();
        parsingRuleCreateDTO.setName(name);
        parsingRuleCreateDTO.setParsingType(parsingType);
        parsingRuleCreateDTO.setComponent(emailComponents);
        parsingRuleCreateDTO.setRule(rule);
        return parsingRuleCreateDTO;
    }

    public static StoredMessageDTO createStoredMessageDTO(String messageID){
        StoredMessageDTO messageDTO = new StoredMessageDTO();
        messageDTO.setMessageID(messageID);
        messageDTO.setReceivedDate(Calendar.getInstance());
        return messageDTO;
    }

    public static ParsedDataDTO createParsedDataDTO(List<String> values,
                                                    StoredMessageDTO storedMessageDTO, ParsingRuleDTO parsingRuleDTO){
        ParsedDataDTO parsedDataDTO = new ParsedDataDTO();
        parsedDataDTO.setValues(values);

        parsedDataDTO.setStoredMessage(storedMessageDTO);
        parsedDataDTO.setParsingRule(parsingRuleDTO);
        return parsedDataDTO;
    }
}
