package pt.ipleiria.estg.dei.ei.dae.monitor.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.ClientDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.ClientBean;

import jakarta.ejb.EJB;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.monitor.security.Authenticated;

import java.util.List;

@Path("clientes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ClientService {

    @EJB
    private ClientBean clientBean;

    @Context
    private SecurityContext securityContext;


    @GET
    @Path("/")
    @RolesAllowed({"Administrator"})
    @Authenticated
    public Response getAllClients() {
        return Response.ok(ClientDTO.from(clientBean.findAll())).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewClient(ClientDTO clientDTO) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        clientBean.create(
                clientDTO.getUsername(),
                clientDTO.getPassword(),
                clientDTO.getName(),
                clientDTO.getEmail()
        );
        Client newClient = clientBean.find(clientDTO.getUsername());
        return Response.status(Response.Status.CREATED).entity(ClientDTO.from(newClient)).build();
    }

    @GET
    @Path("{username}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @Authenticated
    @RolesAllowed({"Administrator","Client"})
    public Response getClient(@PathParam("username") String username) throws MyEntityNotFoundException {
        var principal = securityContext.getUserPrincipal();
        var isAdmin = securityContext.isUserInRole("Administrator");
        if (!principal.getName().equals(username) && !isAdmin) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        var client = clientBean.find(username);
        return Response.ok(ClientDTO.from(client)).build();
    }

    @GET
    @Path("{username}/encomendas")
    @Authenticated
    @RolesAllowed({"Administrator", "Client"})
    public Response getClientOrders(@PathParam("username") String username) throws MyEntityNotFoundException {
        var principal = securityContext.getUserPrincipal();
        var isAdmin = securityContext.isUserInRole("Administrator");
        if (!principal.getName().equals(username) && !isAdmin) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Client client = clientBean.findWithOrders(username);
        return Response.ok(OrderDTO.from(client.getOrders())).build();
    }

}
