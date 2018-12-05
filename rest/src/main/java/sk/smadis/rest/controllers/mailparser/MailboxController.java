package sk.smadis.rest.controllers.mailparser;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import sk.smadis.api.dto.mailparser.MailboxDTO;
import sk.smadis.api.dto.mailparser.create.MailboxCreateDTO;
import sk.smadis.api.facade.MailboxFacade;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */

@Path("/mailbox")
@OpenAPIDefinition(
        info = @Info(title = "Mailparser",
                description = "Mailparser",
                version = "1.0"),
        servers = {
                @Server(
                        description = "Localhost",
                        url = "http://localhost:8080/rest"
                )
        }
)
@Tag(name = "Mailbox")

@RequestScoped
public class MailboxController {
    @Inject
    private MailboxFacade mailboxFacade;

    @POST
    @Operation(summary = "Creates mailbox",
            description = "Creates mailbox",
            responses = {
                    @ApiResponse(description = "Created mailbox", content = @Content(
                            schema = @Schema(implementation = MailboxDTO.class)
                    ))
            })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(MailboxCreateDTO mailboxCreateDTO) {
        MailboxDTO mailboxDTO = mailboxFacade.create(mailboxCreateDTO);
        return Response.ok(mailboxDTO).build();
    }

    @PUT
    @Operation(summary = "Updates mailbox",
            description = "Updates mailbox")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(MailboxDTO mailboxDTO) {
        mailboxFacade.update(mailboxDTO);
        return Response.ok().build();
    }

    @DELETE
    @Operation(summary = "Deletes mailbox",
            description = "Deletes mailbox")
    @RequestBody(description = "Mailbox",
            content = @Content(schema = @Schema(implementation = MailboxDTO.class)))
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(MailboxDTO mailboxDTO) {
        mailboxFacade.delete(mailboxDTO);
        return Response.ok().build();
    }

    @GET
    @Operation(summary = "Finds mailbox by name",
            description = "Finds mailbox by name",
            responses = {
                    @ApiResponse(description = "Mailbox", content = @Content(
                            schema = @Schema(implementation = MailboxDTO.class)
                    ))
            })
    @Path("/{mailbox_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByName(@Parameter(description = "Name of mailbox", required = true)
                               @PathParam("mailbox_name") String mailboxName) {
        return Response.ok(mailboxFacade.findByName(mailboxName)).build();
    }

    @GET
    @Operation(summary = "Finds mailbox by ID",
            description = "Finds mailbox by ID",
            responses = {
                    @ApiResponse(description = "Mailbox", content = @Content(
                            schema = @Schema(implementation = MailboxDTO.class)
                    ))
            })
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@Parameter(description = "Id of mailbox", required = true)
                             @PathParam("id") Long id) {
        return Response.ok(mailboxFacade.findById(id)).build();
    }

    @GET
    @Operation(summary = "Get all mailboxes",
            description = "Get all mailboxes")
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMailboxes() {
        return Response.ok(mailboxFacade.getAllMailboxes()).build();
    }

}
