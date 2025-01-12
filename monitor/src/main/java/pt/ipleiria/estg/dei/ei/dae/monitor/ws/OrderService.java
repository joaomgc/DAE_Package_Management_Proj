package pt.ipleiria.estg.dei.ei.dae.monitor.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.PackageBean;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Volume;

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
    public void create(OrderDTO orderDTO) {
        orderBean.create(
                orderDTO.getEncomendaId(),
                orderDTO.getCustomerId(),
                orderDTO.getEstado()
        );
    }

    @GET
    @Path("/{encomendaId}") // EP03
    public OrderDTO getOrder(@PathParam("encomendaId") Long encomendaId) {
        return OrderDTO.from(orderBean.find(encomendaId));
    }

    // change an order state
    @PATCH
    @Path("/{encomendaId}/estado") // EP10
    public Response changeOrderState(@PathParam("encomendaId") Long encomendaId, OrderDTO orderDTO) {
        Order order = orderBean.find(encomendaId);
        if(order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        order.setEstado(orderDTO.getEstado());
        orderBean.update(order);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{encomendaId}") // EP13
    public Response deleteOrder(@PathParam("encomendaId") Long encomendaId) {
        Order order = orderBean.find(encomendaId);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        orderBean.removeVolume(encomendaId);
        orderBean.delete(encomendaId);
        return Response.noContent().build();
    }

}
