package pt.ipleiria.estg.dei.ei.dae.monitor.ws;


import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.User;
import pt.ipleiria.estg.dei.ei.dae.monitor.security.Authenticated;

import java.util.List;

@Path("user")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class UserService {
    @EJB
    private UserBean userBean;

    @Context
    private SecurityContext securityContext;

    /*@GET
    @Path("/")
    public List<UserDTO> getAllUsers() {
        return UserDTO.from(userBean.findAll());
    }*/

    // METER POST '/'

    @GET
    @Path("/{username}")
    @RolesAllowed({"User"})
    public Response getUser(@PathParam("username") String username) {
        var principal = securityContext.getUserPrincipal();

        if (!principal.getName().equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        try {
            var user = userBean.findOrFail(username);
            return Response.ok(UserDTO.from(user)).build();
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }

    @POST
    @Path("/{username}/update")
    public Response updateUser(@PathParam("username") String username, UserDTO userDTO) {
        var principal = securityContext.getUserPrincipal();

        if (!principal.getName().equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        try {
            User user = userBean.findOrFail(username);
            user.setName(userDTO.getEmail());
            userBean.update(user);
            return Response.ok(UserDTO.from(user)).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }
}
