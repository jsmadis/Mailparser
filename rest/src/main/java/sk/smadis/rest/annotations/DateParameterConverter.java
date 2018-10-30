package sk.smadis.rest.annotations;

import javax.ws.rs.ext.ParamConverter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public class DateParameterConverter implements ParamConverter<Date> {

    public static final String DEFAULT_FORMAT = DateTimeFormat.DEFAULT_DATE_TIME;


    @Override
    public Date fromString(String s) {

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_FORMAT);

        try {
            return simpleDateFormat.parse(s);
        } catch (ParseException e) {
            //TODO
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString(Date date) {
        return new SimpleDateFormat(DEFAULT_FORMAT).format(date);
    }
}

