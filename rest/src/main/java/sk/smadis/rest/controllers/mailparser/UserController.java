package sk.smadis.rest.controllers.mailparser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import sk.smadis.api.dto.mailparser.UserDTO;
import sk.smadis.api.dto.mailparser.create.UserCreateDTO;
import sk.smadis.api.facade.UserFacade;

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
@Path("/user")
@Tag(name = "User")
@RequestScoped
public class UserController {

    @Inject
    private UserFacade userFacade;

    @POST
    @Operation(summary = "Creates user",
            description = "Creates user",
            responses = {
                    @ApiResponse(description = "Created user", content = @Content(
                            schema = @Schema(implementation = UserDTO.class)
                    ))
            })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(UserCreateDTO userCreateDTO) {
        UserDTO user = userFacade.create(userCreateDTO);
        return Response.ok(user).build();
    }

    @PUT
    @Operation(summary = "Updates user",
            description = "Updates user")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(UserDTO userDTO) {
        userFacade.update(userDTO);
        return Response.ok().build();
    }

    @DELETE
    @Operation(summary = "Deletes user",
            description = "Deletes user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(UserDTO userDTO) {
        userFacade.delete(userDTO);
        return Response.ok().build();
    }

    @GET
    @Operation(summary = "Finds user by ID",
            description = "Finds user by ID",
            responses = {
                    @ApiResponse(description = "User", content = @Content(
                            schema = @Schema(implementation = UserDTO.class)
                    ))
            })
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@Parameter(description = "Id of user", required = true)
                             @PathParam("id") Long id) {
        UserDTO userDTO = userFacade.findById(id);
        if (userDTO == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(userDTO).build();

    }


    @GET
    @Operation(summary = "Get all users",
            description = "Get all users")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAllUsers() {
        return Response.ok(userFacade.getAllUsers()).build();
    }


}
