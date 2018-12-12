package sk.smadis.rest.controllers.mailparser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import sk.smadis.api.dto.mailparser.StoredMessageDTO;
import sk.smadis.api.facade.StoredMessageFacade;
import sk.smadis.rest.annotations.DateTimeFormat;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Calendar;
import java.util.Date;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Path("stored_message")
@Tag(name = "Stored Message")
@RequestScoped
public class StoredMessageController {
    @Inject
    private StoredMessageFacade storedMessageFacade;

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    @Operation(summary = "Deletes stored message",
            description = "Deletes stored message")
    public Response delete(@Parameter(description = "Id of stored message", required = true)
                               @PathParam("id") Long id) {
        storedMessageFacade.delete(id);
        return Response.ok().build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Operation(summary = "Finds stored message by ID",
            description = "Finds stored message by ID",
            responses = {
                    @ApiResponse(description = "Stored message", content = @Content(
                            schema = @Schema(implementation = StoredMessageDTO.class)
                    ))
            })
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@Parameter(description = "Id of stored message", required = true)
                             @PathParam("id") Long id) {
        return Response.ok(storedMessageFacade.findById(id)).build();
    }

    @GET
    @Operation(summary = "Get all stored messages",
            description = "Get all stored messages")
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStoredMessages() {
        return Response.ok(storedMessageFacade.getAllStoredMessages()).build();
    }


    @GET
    @Path("/all/test/{count:[0-9][0-9]*}")
    @Operation(summary = "Get all stored messages",
            description = "Get all stored messages")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLastMessages(@Parameter(description = "Count of messages", required = true)
                                    @PathParam("count") int count) {
        return Response.ok(storedMessageFacade.getLastMessages(count)).build();
    }

    @GET
    @Path("/all/after")
    @Operation(summary = "Get all stored messages after given date",
            description = "Get all stored messages after given date")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessgesAfter(@Parameter(description = "Date", required = true, example = "05-10-2017T12:57:59")
                                    @QueryParam("date") @DateTimeFormat Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return Response.ok(storedMessageFacade.getMessagesAfter(calendar)).build();
    }
}
