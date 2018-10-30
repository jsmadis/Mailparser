package sk.smadis.email_client.exceptions;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public class ProcessingMessageException extends RuntimeException {
    public ProcessingMessageException() {
    }

    public ProcessingMessageException(String message) {
        super(message);
    }

    public ProcessingMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessingMessageException(Throwable cause) {
        super(cause);
    }

    public ProcessingMessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
