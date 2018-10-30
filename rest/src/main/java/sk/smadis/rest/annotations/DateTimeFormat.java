package sk.smadis.rest.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface DateTimeFormat {
    public static final String DEFAULT_DATE_TIME = "dd-MM-yyyy'T'HH:mm:ss";

    String value() default DEFAULT_DATE_TIME;
}
