package pt.ipleiria.estg.dei.ei.dae.monitor.ws;

import jakarta.annotation.security.RolesAllowed;
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
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.monitor.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@Path("/volumes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class VolumeService {

    @EJB
    private VolumeBean volumeBean;
    @EJB
    private ProductBean productBean;

    @GET
    @RolesAllowed({"Administrator"})
    public Response getAllVolumes() {
        List<Volume> volumes = volumeBean.findAll();
        return Response.ok(VolumeDTO.from(volumes)).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Administrator"})
    public Response getVolume(@PathParam("id") Long id) throws MyEntityNotFoundException {
        Volume volume = volumeBean.find(id);
        if (volume == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(VolumeDTO.from(volume)).build();
    }

    @POST
    @RolesAllowed({"Administrator"})
    public Response createVolume(VolumeDTO volumeDTO) throws MyEntityNotFoundException, MyEntityExistsException {
        volumeBean.create(volumeDTO.getId(), volumeDTO.getVolumeName(), null); // Assuming Package is handled elsewhere
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Administrator"})
    public Response updateVolume(@PathParam("id") Long id, VolumeDTO volumeDTO) throws MyEntityNotFoundException{
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
    @RolesAllowed({"Administrator"})
    public Response deleteVolume(@PathParam("id") Long id) throws MyEntityNotFoundException {
        Volume volume = volumeBean.find(id);
        if (volume == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        volumeBean.delete(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/{volumeId}/packages/{packageId}")
    @RolesAllowed({"Administrator"})
    public Response addPackageToVolume(@PathParam("volumeId") Long volumeId, @PathParam("packageId") Long packageId)
            throws MyEntityNotFoundException{
        volumeBean.addPackage(volumeId, packageId);
        return Response.ok().build();
    }

    @POST
    @Path("/{volumeId}/sensors/{sensorId}")
    @RolesAllowed({"Administrator"})
    public Response associateSensorToVolume(@PathParam("volumeId") Long volumeId, @PathParam("sensorId") String sensorId)
            throws MyEntityNotFoundException{
        Volume volume = volumeBean.find(volumeId);
        volumeBean.associateSensor(volumeId, sensorId);

        return Response.ok().build();
    }

    @POST
    @Path("/{volumeId}/produtos")  // EP02.1 - add product to volume
    @RolesAllowed({"Administrator"})
    public Response addProduct(@PathParam("volumeId") Long volumeId, ProductQuantityDTO dto) throws MyEntityNotFoundException{
        volumeBean.addProduct(volumeId, dto.getProductId(), dto.getQuantidade());
        Volume volume = volumeBean.find(volumeId);
        return Response.ok(VolumeDTO.from(volume)).build();
    }
}