package sk.smadis.parser.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public class ParseUtils {

    public static List<String> parseString(String rule, String stringToParse) {
        Pattern pattern = Pattern.compile(rule);
        List<String> values = new ArrayList<>();
        Matcher matcher = pattern.matcher(stringToParse);
        while (matcher.find()) {
            values.add(matcher.group());
        }
        return values;
    }

    public static List<String> parseListOfString(String rule, List<String> stringList) {
        Pattern pattern = Pattern.compile(rule);
        List<String> values = new ArrayList<>();
        for (String string : stringList) {
            Matcher matcher = pattern.matcher(string);
            while (matcher.find()) {
                values.add(matcher.group());
            }
        }
        return values;
    }

    public static List<String> parseHTML(String rule, String htmlText){
        List<String> values = new ArrayList<>();
        Document doc = Jsoup.parse(htmlText);
        Elements elements = doc.select(rule);
        elements.forEach(element -> values.add(element.text()));
        return values;
    }
}
