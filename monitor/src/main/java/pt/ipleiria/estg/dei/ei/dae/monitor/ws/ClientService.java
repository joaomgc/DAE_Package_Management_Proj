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
import pt.ipleiria.estg.dei.ei.dae.monitor.security.Authenticated;

import java.util.List;

@Path("clientes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class ClientService {

    @EJB
    private ClientBean clientBean;

    @Context
    private SecurityContext securityContext;


    @GET
    @Path("/")
    public List<ClientDTO> getAllClients() {
        return ClientDTO.from(clientBean.findAll());
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewClient(ClientDTO clientDTO) {
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
    @RolesAllowed({"Client"})
    public Response getClient(@PathParam("username") String username) {
        var principal = securityContext.getUserPrincipal();

        if (!principal.getName().equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        var client = clientBean.find(username);
        return Response.ok(ClientDTO.from(client)).build();
    }

    // get all orders of a specific user
    @GET
    @Path("{username}/encomendas")
    @RolesAllowed({"Client"})
    public Response getClientOrders(@PathParam("username") String username) {
        Client client = clientBean.findWithOrders(username);
        return Response.ok(OrderDTO.from(client.getOrders())).build();
    }

}
