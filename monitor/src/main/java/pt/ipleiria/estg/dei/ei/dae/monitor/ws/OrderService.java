package pt.ipleiria.estg.dei.ei.dae.monitor.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.OrderHistoryDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.PackageBean;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.OrderHistory;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityNotFoundException;

import java.util.List;

@Path("/encomendas")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class OrderService {
    @EJB
    private OrderBean orderBean;

    @GET
    @Path("/")
    public List<OrderDTO> getAllOrders() {
        return OrderDTO.from(orderBean.findAll());
    }

    @POST
    @Path("/") // EP01
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
    public OrderDTO getOrder(@PathParam("encomendaId") Long encomendaId) throws MyEntityNotFoundException{
        return OrderDTO.from(orderBean.find(encomendaId));
    }

    // change an order state
    @PATCH
    @Path("/{encomendaId}/estado") // EP10
    public Response changeOrderState(@PathParam("encomendaId") Long encomendaId, OrderDTO orderDTO) throws MyEntityNotFoundException {
        Order order = orderBean.find(encomendaId);
        if(order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        order.updateEstado(orderDTO.getEstado());
        orderBean.update(order);

        return Response.ok().build(); // todo: talvez criar um DTO para mostrar o novo estado da encomenda ?? o_O
    }

    @DELETE
    @Path("/{encomendaId}") // EP13
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
    public OrderHistoryDTO getOrderHistory(@PathParam("encomendaId") Long encomendaId) {
        List<OrderHistory> historyList = orderBean.getOrderHistory(encomendaId);
        return OrderHistoryDTO.from(encomendaId, historyList);
    }

}
