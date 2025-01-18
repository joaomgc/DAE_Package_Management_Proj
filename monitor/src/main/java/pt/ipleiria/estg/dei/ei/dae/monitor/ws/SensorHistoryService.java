package pt.ipleiria.estg.dei.ei.dae.monitor.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.SensorHistoryDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.SensorHistoryBean;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityNotFoundException;

@Path("/sensor")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorHistoryService {

    @EJB
    private SensorHistoryBean sensorHistoryBean;

    @GET
    @Path("/")
    @RolesAllowed({"Administrator"})
    public Response getAllSensorHistory() {
        return Response.ok(SensorHistoryDTO.from(sensorHistoryBean.findAll())).build();
    }

    @GET
    @Path("/{id}/history") // EP07
    public Response getSensorHistory(@PathParam("id") String id) throws MyEntityNotFoundException {
        var history = sensorHistoryBean.findBySensorId(id);
        return Response.ok(SensorHistoryDTO.from(history)).build();
    }

}
