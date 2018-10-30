package sk.smadis.rest.controllers.mailparser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import sk.smadis.api.dto.mailparser.ParsingRuleDTO;
import sk.smadis.api.dto.mailparser.create.ParsingRuleCreateDTO;
import sk.smadis.api.facade.ParsingRuleFacade;

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
@Path("parsing_rule")
@Tag(name = "Parsing Rule")
@RequestScoped
public class ParsingRuleController {

    @Inject
    private ParsingRuleFacade parsingRuleFacade;

    @POST
    @Operation(summary = "Creates parsing rule",
            description = "Creates parsing rule",
            responses = {
                    @ApiResponse(description = "Created parsing rule", content = @Content(
                            schema = @Schema(implementation = ParsingRuleDTO.class)
                    ))
            })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(ParsingRuleCreateDTO parsingRuleCreateDTO) {
        ParsingRuleDTO parsingRuleDTO = parsingRuleFacade.create(parsingRuleCreateDTO);
        return Response.ok(parsingRuleDTO).build();
    }

    @PUT
    @Operation(summary = "Updates parsing rule",
            description = "Updates parsing rule")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(ParsingRuleDTO parsingRuleDTO) {
        parsingRuleFacade.update(parsingRuleDTO);
        return Response.ok().build();
    }

    @DELETE
    @Operation(summary = "Deletes parsing rule",
            description = "Deletes parsing rule")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(ParsingRuleDTO parsingRuleDTO) {
        parsingRuleFacade.delete(parsingRuleDTO);
        return Response.ok().build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Operation(summary = "Finds parsing rule by ID",
            description = "Finds parsing rule by ID",
            responses = {
                    @ApiResponse(description = "Parsing rule", content = @Content(
                            schema = @Schema(implementation = ParsingRuleDTO.class)
                    ))
            })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@Parameter(description = "Id of parsing rule", required = true)
                             @PathParam("id") Long id) {
        ParsingRuleDTO parsingRuleDTO = parsingRuleFacade.findById(id);
        return Response.ok(parsingRuleDTO).build();
    }

    @GET
    @Path("/all")
    @Operation(summary = "Get all parsing rules",
            description = "Get all parsing rules")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        return Response.ok(parsingRuleFacade.getAllParsingRules()).build();
    }

}
