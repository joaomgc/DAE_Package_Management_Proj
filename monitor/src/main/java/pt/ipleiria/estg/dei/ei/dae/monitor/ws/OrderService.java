package pt.ipleiria.estg.dei.ei.dae.monitor.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.OrderHistoryDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.AdministratorBean;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.PackageBean;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.OrderHistory;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.monitor.security.Authenticated;

import java.util.List;

@Path("/encomendas")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class OrderService {
    @Context
    private SecurityContext securityContext;
    @EJB
    private OrderBean orderBean;
    @EJB
    private AdministratorBean administratorBean;

    @GET
    @Path("/")
    @RolesAllowed({"Administrator"})
    public List<OrderDTO> getAllOrders() {
        return OrderDTO.from(orderBean.findAll());
    }

    @POST
    @Path("/") // EP01
    @RolesAllowed({"Administrator"})
    public Response create(OrderDTO orderDTO) throws MyEntityNotFoundException, MyEntityExistsException {
        orderBean.create(
                orderDTO.getEncomendaId(),
                orderDTO.getClientUsername(),
                orderDTO.getEstado()
        );

        Order newOrder = orderBean.find(orderDTO.getEncomendaId());
        return Response.status(Response.Status.CREATED)
                .entity(OrderDTO.from(newOrder))
                .build();
    }

    @GET
    @Path("/{encomendaId}") // EP03
    @RolesAllowed({"Administrator", "Client"})
    public Response getOrder(@PathParam("encomendaId") Long encomendaId) throws MyEntityNotFoundException{
        var principal = securityContext.getUserPrincipal();
        var order = orderBean.find(encomendaId);

        boolean isAdmin = securityContext.isUserInRole("Administrator");
        if(!principal.getName().equals(order.getClient().getUsername()) && !isAdmin) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.ok(OrderDTO.from(order)).build();
    }

    @PATCH
    @Path("/{encomendaId}/estado") // EP10
    @RolesAllowed({"Administrator"})
    public Response changeOrderState(@PathParam("encomendaId") Long encomendaId, OrderDTO orderDTO) throws MyEntityNotFoundException {
        Order order = orderBean.find(encomendaId);
        if(order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        order.updateEstado(orderDTO.getEstado());
        orderBean.update(order);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{encomendaId}") // EP13
    @RolesAllowed({"Administrator"})
    public Response deleteOrder(@PathParam("encomendaId") Long encomendaId) throws MyEntityNotFoundException{
        Order order = orderBean.find(encomendaId);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        orderBean.removeVolume(encomendaId);
        orderBean.delete(encomendaId);
        return Response.noContent().build();
    }

    @GET
    @Path("{encomendaId}/historico") // EP11
    @RolesAllowed({"Administrator", "Client"})
    public  Response getOrderHistory(@PathParam("encomendaId") Long encomendaId) throws MyEntityNotFoundException{
        var principal = securityContext.getUserPrincipal();
        List<OrderHistory> historyList = orderBean.getOrderHistory(encomendaId);
        var order = orderBean.find(encomendaId);
        if(!principal.getName().equals(order.getClient().getUsername())) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.ok(OrderHistoryDTO.from(historyList)).build();
    }

    @GET
    @Path("historico")
    @RolesAllowed({"Administrator"})
    public Response getAllOrderHistory() {
        List<OrderHistory> historyList = orderBean.getAllOrderHistory();
        return Response.ok(OrderHistoryDTO.from(historyList)).build();
    }

}
