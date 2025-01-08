package pt.ipleiria.estg.dei.ei.dae.monitor.ws;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.AuthDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.monitor.security.Authenticated;
import pt.ipleiria.estg.dei.ei.dae.monitor.security.TokenIssuer;

@Path("auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthService {

    @Inject
    private TokenIssuer tokenIssuer;

    @EJB
    private UserBean userBean;

    @Context
    private SecurityContext securityContext;

    @POST
    @Path("/login")
    public Response authenticate(@Valid AuthDTO authDTO) {
        if (userBean.canLogin(authDTO.getUsername(), authDTO.getPassword())) {
            var token = tokenIssuer.issue(authDTO.getUsername());
            System.out.println("User " + authDTO.getUsername() + " logged in");
            return Response.ok(token).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Authenticated
    @Path("/user")
    public Response getAuthenticatedUser() {
        var username = securityContext.getUserPrincipal().getName();
        var user = userBean.findOrFail(username);
        return Response.ok(UserDTO.from(user)).build();
    }
}
