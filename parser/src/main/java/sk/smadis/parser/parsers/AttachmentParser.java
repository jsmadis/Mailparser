package sk.smadis.parser.parsers;

import org.apache.log4j.Logger;
import sk.smadis.parser.utils.FileUtils;
import sk.smadis.parser.utils.OcrUtils;
import sk.smadis.parser.utils.DocumentUtils;
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

    private Logger logger = Logger.getLogger(this.getClass());

    @Inject
    private OcrUtils ocrUtils;

    @Inject
    private FileUtils fileUtils;

    @Inject
    private DocumentUtils documentUtils;

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
            ParsedData parsedData = parseFile(f, rule);
            if (parsedData != null && parsedData.getValues() != null && !parsedData.getValues().isEmpty()) {
                result.add(parsedData);
            }
        }
        return result;
    }


    /**
     * Parses file with given parsing rule and saves it as ParsedData
     *
     * @param file file
     * @param rule ParsingRule
     * @return ParsedData
     */
    private ParsedData parseFile(File file, ParsingRule rule) {
        String text = readFile(file, rule.getFileLanguage().toString().toLowerCase());
        if (text == null) {
            logger.info("File: " + file.getName() + " couldn't be parsed by rule:" + rule);
            return null;
        }

        ParsedData parsedData = new ParsedData();
        parsedData.setValues(ParseUtils.parseString(rule.getRule(), text));
        parsedData.setFileName(file.getName());
        return parsedData;
    }

    /**
     * Reads file and return String format
     *
     * @param file File to read
     * @param language Language to use in OCR
     * @return String representation of File contents
     */
    private String readFile(File file, String language){
        String fileExtension = getFileExtension(file).toLowerCase();
        String text = null;
        if (isFileSupportedByOCR(fileExtension)) {
            text = ocrUtils.getTextFromFile(file, language);
        } else if (fileExtension.equals(".txt")) {
            text = fileUtils.getStringFromTxtFile(file);
        } else if (fileExtension.equals(".docx")) {
            text = documentUtils.readDocxFile(file);
        } else if (fileExtension.equals(".odt")) {
            text = documentUtils.readOdtFile(file);
        } else {
            logger.info("Not supported type of file extension: " + file.getName());
        }
        return text;
    }

    /**
     * Gets file extension of file
     *
     * @param file File
     * @return file extension
     */
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }

    /**
     * Checks if file extension is supported by OCR
     *
     * @param fileExtension file extension
     * @return true if supported by OCR, false otherwise
     */
    private boolean isFileSupportedByOCR(String fileExtension) {
        return fileExtension.equals(".pdf") ||
                fileExtension.equals(".tiff") ||
                fileExtension.equals(".jpeg") ||
                fileExtension.equals(".gif") ||
                fileExtension.equals(".bmp") ||
                fileExtension.equals(".pdf");
    }
}
