package sk.smadis.parser.parsers;

import sk.smadis.email_client.EmailToParse;
import sk.smadis.storage.entity.ParsedData;
import sk.smadis.storage.entity.ParsingRule;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@ApplicationScoped
public class PlainTextParser {

    @Inject
    private AttachmentParser attachmentParser;

    /**
     * Parses plain text parsing type from email based on parsing rule and returns it as ParsedData.
     *
     * @param email email to parse
     * @param rule  ParsingRule
     * @return list of ParsedData
     */
    public List<ParsedData> parsePlainText(EmailToParse email, ParsingRule rule) {
        List<ParsedData> parsedDataList = new ArrayList<>();
        ParsedData parsedData = new ParsedData();
        switch (rule.getComponent()) {
            case HEADERS:
                parsedData.setValues(ParseUtils.parseListOfString(rule.getRule(), email.getHeaders()));
                break;
            case SUBJECT:
                parsedData.setValues(ParseUtils.parseString(rule.getRule(), email.getSubject()));
                break;
            case BODY:
                parsedData.setValues(ParseUtils.parseString(rule.getRule(), email.getBodyPlainText()));
                break;
            case ATTACHMENT:
                parsedDataList = attachmentParser.parseFiles(email.getFiles(), rule);
                break;
        }
        if (parsedData.getValues() == null || parsedData.getValues().isEmpty()) return parsedDataList;
        parsedDataList.add(parsedData);
        return parsedDataList;
    }
}
