package sk.smadis.parser.parsers;

import sk.smadis.parser.utils.OcrUtils;
import sk.smadis.storage.entity.ParsedData;
import sk.smadis.storage.entity.ParsingRule;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@ApplicationScoped
public class AttachmentParser {

    @Inject
    private OcrUtils ocrUtils;

    /**
     * Parses files with given parsing rule and saves it as ParsedData.
     *
     * @param files List of files to parse
     * @param rule  ParsingRule
     * @return List of ParsedData
     */
    public List<ParsedData> parseFiles(List<File> files, ParsingRule rule) {
        if (files.isEmpty()) {
            return new ArrayList<>();
        }
        List<ParsedData> result = new ArrayList<>();
        for (File f : files) {
            result.add(parseFileWithOCR(f, rule));
        }
        return result;
    }

    /**
     * Reads file using OCR, then parsing it based on ParsingRule and saves it as ParsedData.
     *
     * @param file File to parse
     * @param rule ParsingRule
     * @return ParsedData
     */
    private ParsedData parseFileWithOCR(File file, ParsingRule rule) {
        String text = ocrUtils.getTextFromFile(file, rule.getFileLanguage().toString().toLowerCase());
        ParsedData parsedData = new ParsedData();
        parsedData.setValues(ParseUtils.parseString(rule.getRule(), text));
        parsedData.setFileName(file.getName());
        return parsedData;
    }
}
