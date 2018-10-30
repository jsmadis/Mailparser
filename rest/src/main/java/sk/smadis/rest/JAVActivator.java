package sk.smadis.rest;

import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import sk.smadis.rest.controllers.mailparser.MailboxController;
import sk.smadis.rest.controllers.mailparser.ParsedDataController;
import sk.smadis.rest.controllers.mailparser.ParsingRuleController;
import sk.smadis.rest.controllers.mailparser.StoredMessageController;
import sk.smadis.rest.controllers.mailparser.UserController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@ApplicationPath("app")
public class JAVActivator extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return Stream.of(CorsFilter.class,
                MailboxController.class,
                ParsedDataController.class,
                ParsingRuleController.class,
                StoredMessageController.class,
                UserController.class,
                OpenApiResource.class,
                AcceptHeaderOpenApiResource.class).collect(Collectors.toSet());
    }
}
