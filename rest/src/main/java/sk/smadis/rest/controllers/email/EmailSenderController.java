package sk.smadis.rest.controllers.email;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import sk.smadis.api.dto.email_client.MessageDTO;
import sk.smadis.email_client.exceptions.ProcessingMessageException;
import sk.smadis.email_client.sender.MailSender;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Path("/email")
@Tag(name = "Email sender")
@RequestScoped
public class EmailSenderController {

    @Inject
    private MailSender mailSender;

    @POST
    @Path("/send")
    @Operation(summary = "Sends email",
            description = "Sends email")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendEmail(final MessageDTO messageDTO) throws ProcessingMessageException {
        try {
            mailSender.send(messageDTO);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(e.getLocalizedMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
        return Response.status(Response.Status.ACCEPTED).build();
    }
}
