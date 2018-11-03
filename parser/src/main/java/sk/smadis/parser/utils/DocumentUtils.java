package sk.smadis.parser.utils;

import javax.enterprise.context.ApplicationScoped;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@ApplicationScoped
public class DocumentUtils {

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * Reads docx file to string;
     *
     * @param file File
     * @return Contents of file in String.
     */
    public String readDocxFile(File file) {
        logger.info("Reading word document.");
        String result;

        try {
            FileInputStream fis = new FileInputStream(file);

            XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
            XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
            result = extractor.getText();

        } catch (FileNotFoundException e) {
            logger.error("File not found while reading word document: " + file.getName(), e);
            return null;
        } catch (InvalidFormatException e) {
            logger.error("Invalid format for word document: " + file.getName(), e);
            return null;
        } catch (IOException e) {
            logger.error("IO error while reading word document: ", e);
            return null;
        }
        return result;
    }

    /**
     * Reads odt file to string.
     *
     * @param file odt file
     * @return String format of file content
     */
    public String readOdtFile(File file) {
        logger.info("Reading odt document.");

        String result;

        try {
            OdfTextDocument textDocument = OdfTextDocument.loadDocument(file);
            result = textDocument.getContentRoot().getTextContent();
        } catch (Exception e) {
            logger.error("Error while reading odt file.", e);
            return null;
        }
        return result;

    }

}
