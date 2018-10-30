package sk.smadis.email_client.exceptions;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public class ParsingMessageException extends Exception {
    public ParsingMessageException() {
    }

    public ParsingMessageException(String s) {
        super(s);
    }

    public ParsingMessageException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ParsingMessageException(Throwable throwable) {
        super(throwable);
    }

    public ParsingMessageException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
