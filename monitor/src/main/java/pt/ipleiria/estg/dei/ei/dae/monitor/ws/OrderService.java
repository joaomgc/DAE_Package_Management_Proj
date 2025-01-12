package pt.ipleiria.estg.dei.ei.dae.monitor.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.PackageBean;

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

}
