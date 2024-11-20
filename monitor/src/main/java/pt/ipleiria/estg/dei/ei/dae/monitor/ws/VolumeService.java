package pt.ipleiria.estg.dei.ei.dae.monitor.services;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.VolumeDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.VolumeBean;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Volume;

import java.util.List;
import java.util.stream.Collectors;

@Path("/volumes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VolumeService {

    @EJB
    private VolumeBean volumeBean;

    @GET
    public List<VolumeDTO> getAllVolumes() {
        List<Volume> volumes = volumeBean.findAll();
        return volumes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public Response getVolume(@PathParam("id") Long id) {
        Volume volume = volumeBean.find(id);
        if (volume == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(toDTO(volume)).build();
    }

    @POST
    public Response createVolume(VolumeDTO volumeDTO) {
        volumeBean.create(volumeDTO.getId(), volumeDTO.getVolumeName(), null); // Assuming Package is handled elsewhere
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateVolume(@PathParam("id") Long id, VolumeDTO volumeDTO) {
        Volume volume = volumeBean.find(id);
        if (volume == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        volume.setVolumeName(volumeDTO.getVolumeName());
        volumeBean.update(volume);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteVolume(@PathParam("id") Long id) {
        Volume volume = volumeBean.find(id);
        if (volume == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        volumeBean.delete(id);
        return Response.noContent().build();
    }

    private VolumeDTO toDTO(Volume volume) {
        return new VolumeDTO(volume.getId(), volume.getVolumeName(), volume.getPack() != null ? volume.getPack().getPackageId() : null);
    }
}