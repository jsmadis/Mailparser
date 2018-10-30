package sk.smadis.rest.controllers.mailparser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import sk.smadis.api.dto.mailparser.ParsedDataDTO;
import sk.smadis.api.facade.ParsedDataFacade;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */

@Path("/parsed_data")
@Tag(name = "Parsed Data")
@RequestScoped
public class ParsedDataController {

    @Inject
    private ParsedDataFacade parsedDataFacade;

    @DELETE
    @Operation(summary = "Deletes parsed data",
            description = "Deletes parsed data")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(ParsedDataDTO parsedDataDTO) {
        parsedDataFacade.delete(parsedDataDTO);
        return Response.ok().build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Operation(summary = "Finds parsed data by ID",
            description = "Finds parsed data by ID",
            responses = {
                    @ApiResponse(description = "Created user", content = @Content(
                            schema = @Schema(implementation = ParsedDataDTO.class)
                    ))
            })
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@Parameter(description = "Id of parsed data", required = true)
                             @PathParam("id") Long id) {
        return Response.ok(parsedDataFacade.findById(id)).build();
    }

    @GET
    @Path("/all")
    @Operation(summary = "Get all parsed data",
            description = "Get all parsed data")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllParsedData() {
        return Response.ok(parsedDataFacade.getAllParsedData()).build();
    }

}
