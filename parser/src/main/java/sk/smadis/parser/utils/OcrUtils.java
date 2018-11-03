package sk.smadis.parser.utils;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@ApplicationScoped
public class OcrUtils {
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * Gets text from file using Tesseract OCR tool.
     *
     * @param pathToFile path to file
     * @param language   Language of file
     * @return String representation of file contents.
     */
    public String getTextFromFile(String pathToFile, String language) {
        return getTextFromFile(new File(pathToFile), language);
    }

    /**
     * Gets text from file using Tesseract OCR tool.
     *
     * @param file     file
     * @param language Language of file
     * @return String representation of file contents.
     */
    public String getTextFromFile(File file, String language) {
        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage(language);
        logger.info("Getting text from file via Tesseract.");
        try {
            return tesseract.doOCR(file);
        } catch (TesseractException e) {
            logger.error("Error while getting text from file with Tesseract ocr.", e);
        }
        return "";
    }
}
