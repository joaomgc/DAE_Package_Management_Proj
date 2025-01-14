package pt.ipleiria.estg.dei.ei.dae.monitor.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.SensorHistoryBean;

@Path("/sensor")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorHistoryService {

    @EJB
    private SensorHistoryBean sensorHistoryBean;

    @GET
    @Path("/")
    public Response getAllSensorHistory() {
        return Response.ok(sensorHistoryBean.findAll()).build();
    }

    // get the history of a specific sensor
    @GET
    @Path("/{id}/history") // EP07
    public Response getSensorHistory(@PathParam("id") String id) {
        return Response.ok(sensorHistoryBean.findBySensorId(id)).build();
    }

}
