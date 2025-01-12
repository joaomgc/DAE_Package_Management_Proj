package pt.ipleiria.estg.dei.ei.dae.monitor.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.SensorHistoryBean;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.SensorSimulator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorService {

    @EJB
    private SensorBean sensorBean;

    @EJB
    private SensorHistoryBean sensorHistoryBean;

    @GET
    public List<SensorDTO> getAllSensors() {
        List<SensorSimulator> sensors = sensorBean.findAll();
        return sensors.stream().map(SensorDTO::from).collect(Collectors.toList());
    }

    @GET
    @Path("/{id}") // EP06
    public Response getSensor(@PathParam("id") String id) {
        SensorSimulator sensor = sensorBean.find(id);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(SensorDTO.from(sensor)).build();
    }

    /*@POST
    public Response createSensor(SensorDTO sensorDTO) {
        sensorBean.create(sensorDTO.getId(), sensorDTO.getTipo());
        return Response.status(Response.Status.CREATED).build();
    }*/

    @PUT
    @Path("/{id}")
    public Response updateSensor(@PathParam("id") String id, SensorDTO sensorDTO) {
        SensorSimulator sensor = sensorBean.find(id);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        sensor.setTipo(sensorDTO.getTipo());
        sensorBean.update(sensor);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSensor(@PathParam("id") String id) {
        SensorSimulator sensor = sensorBean.find(id);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        sensorBean.delete(id);
        return Response.noContent().build();
    }

    // endpoint for a sensor to send a reading
    @POST
    @Path("/") // EP04
    public Response setValue(SensorDTO sensorDTO) {
        SensorSimulator sensor = sensorBean.find(sensorDTO.getId());
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        sensor.setValor(sensorDTO.getValor());
        sensor.setTimestamp(LocalDateTime.now());
        sensorBean.update(sensor);

        sensorHistoryBean.create(sensor, sensor.getTimestamp(), sensor.getValor());

        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}/estado")
    public Response changeStatus(@PathParam("id") String id, SensorDTO sensorDTO) {
        SensorSimulator sensor = sensorBean.find(id);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        sensor.setStatus(sensorDTO.getStatus());
        sensorBean.update(sensor);
        return Response.ok().build();
    }

}