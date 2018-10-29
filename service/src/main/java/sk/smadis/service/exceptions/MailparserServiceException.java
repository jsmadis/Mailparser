package sk.smadis.service.exceptions;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public class MailparserServiceException extends RuntimeException {
    public MailparserServiceException(String s) {
        super(s);
    }

    public MailparserServiceException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
