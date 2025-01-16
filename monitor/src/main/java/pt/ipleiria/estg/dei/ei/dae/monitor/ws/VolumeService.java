package pt.ipleiria.estg.dei.ei.dae.monitor.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.ProductQuantityDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.VolumeDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.VolumeBean;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Volume;

import java.util.List;
import java.util.stream.Collectors;

@Path("/volumes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VolumeService {

    @EJB
    private VolumeBean volumeBean;
    @EJB
    private ProductBean productBean;

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

    @POST
    @Path("/{volumeId}/packages/{packageId}")
    public Response addPackageToVolume(@PathParam("volumeId") Long volumeId, @PathParam("packageId") Long packageId) {
        volumeBean.addPackage(volumeId, packageId);
        return Response.ok().build();
    }

    @POST
    @Path("/{volumeId}/sensors/{sensorId}")
    public Response associateSensorToVolume(@PathParam("volumeId") Long volumeId, @PathParam("sensorId") String sensorId) {
        Volume volume = volumeBean.find(volumeId);
        volumeBean.associateSensor(volumeId, sensorId);

        return Response.ok().build();
    }

    @POST
    @Path("/{volumeId}/produtos")  // EP02.1 - add product to volume
    public Response addProduct(@PathParam("volumeId") Long volumeId, ProductQuantityDTO dto) {
        volumeBean.addProduct(volumeId, dto.getProductId(), dto.getQuantidade());
        Volume volume = volumeBean.find(volumeId);
        return Response.ok(VolumeDTO.from(volume)).build();
    }


    private VolumeDTO toDTO(Volume volume) {
        return new VolumeDTO(volume.getId(), volume.getVolumeName(), volume.getPack() != null ? volume.getPack().getPackageId() : null, volume.getSensor() != null ? volume.getSensor().getId() : null,
                volume.getProductQuantityDTOs());
    }
}