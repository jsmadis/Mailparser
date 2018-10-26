package sk.smadis.storage.utils;

import sk.smadis.storage.entity.Mailbox;
import sk.smadis.storage.entity.ParsedData;
import sk.smadis.storage.entity.ParsingRule;
import sk.smadis.storage.entity.StoredMessage;
import sk.smadis.storage.entity.User;
import sk.smadis.storage.entity.enums.EmailComponent;
import sk.smadis.storage.entity.enums.ParsingType;

import java.util.Calendar;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public class EntityFactory {
    public static User createUser(String name) {
        User user = new User();
        user.setName(name);
        return user;
    }

    public static Mailbox createMailbox(String name) {
        Mailbox mailbox = new Mailbox();
        mailbox.setName(name);
        return mailbox;
    }

    public static ParsedData createParsedData(List<String> values) {
        ParsedData parsedData = new ParsedData();
        parsedData.setValues(values);
        return parsedData;
    }


    public static StoredMessage createMessage(String messageID) {
        StoredMessage message = new StoredMessage();
        message.setMessageID(messageID);
        message.setReceivedDate(Calendar.getInstance());
        return message;
    }

    public static class ParsingRuleBuilder {
        private ParsingRule parsingRule = new ParsingRule();

        public ParsingRuleBuilder name(String name) {
            parsingRule.setName(name);
            return this;
        }

        public ParsingRuleBuilder parsingType(ParsingType parsingType) {
            parsingRule.setParsingType(parsingType);
            return this;
        }

        public ParsingRuleBuilder emailComponent(EmailComponent emailComponent) {
            parsingRule.setComponent(emailComponent);
            return this;
        }


        public ParsingRuleBuilder rule(String rule) {
            parsingRule.setRule(rule);
            return this;
        }

        public ParsingRule build() {
            return parsingRule;
        }

    }
}
