package sk.smadis.parser.utils;

import org.apache.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@ApplicationScoped
public class FileUtils {
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * Gets string from text file (.txt)
     *
     * @param file File
     * @return String
     */
    public String getStringFromTxtFile(File file){
        String result;
        try {
            result = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            logger.error("Error while reading file: " + file.getName(), e);
            return null;
        }
        return result;

    }

}
