package sk.smadis.parser.parsers;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import sk.smadis.email_client.EmailToParse;
import sk.smadis.storage.entity.ParsedData;
import sk.smadis.storage.entity.ParsingRule;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@ApplicationScoped
public class HTMLParser {

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * Parses html parsing type from email based on parsing rule and returns it as ParsedData.
     *
     * @param email Email to parse
     * @param rule  ParsingRule
     * @return list of ParsedData
     */
    public List<ParsedData> parseHTML(EmailToParse email, ParsingRule rule) {
        List<ParsedData> parsedDataList = new ArrayList<>();
        ParsedData parsedData = new ParsedData();

        switch (rule.getComponent()) {
            case HEADERS:
            case SUBJECT:
            case ATTACHMENT:
                logger.error("Unsupported operation rule: " + rule.toString());
                throw new UnsupportedOperationException();
            case BODY:
                parsedData.setValues(ParseUtils.parseHTML(rule.getRule(), email.getBodyHtml()));
                break;
        }
        if (parsedData.getValues().isEmpty()) return parsedDataList;
        parsedDataList.add(parsedData);
        return parsedDataList;
    }


}
