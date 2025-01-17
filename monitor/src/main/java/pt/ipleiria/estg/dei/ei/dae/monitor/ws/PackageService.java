package pt.ipleiria.estg.dei.ei.dae.monitor.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.PackageBean;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityNotFoundException;

import java.util.List;

@Path("/embalagens")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PackageService {
    @EJB
    private PackageBean packageBean;

    @GET
    @Path("/")
    public List<PackageDTO> getAllPackages() {
        return PackageDTO.from(packageBean.findAll());
    }

    @POST
    @Path("/")
    public Response createNewPackage(PackageDTO packageDTO) throws MyEntityExistsException, MyEntityNotFoundException {
        packageBean.create(
                packageDTO.getPackageId(),
                packageDTO.getPackageType()
        );
        Package newPackage = packageBean.find(packageDTO.getPackageId());
        return Response.status(Response.Status.CREATED).entity(PackageDTO.from(newPackage)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updatePackage(@PathParam("id") Long id, PackageDTO packageDTO) throws MyEntityNotFoundException{
        var pck = packageBean.find(id);
        pck.setPackageType(packageDTO.getPackageType());
        packageBean.update(pck);

        return Response.ok(PackageDTO.from(pck)).build();
    }

    @DELETE
    @Path("/{id}")
    public void deletePackage(@PathParam("id") Long id) throws MyEntityNotFoundException {
        packageBean.delete(id);
    }

    @GET
    @Path("/{id}")
    public PackageDTO getPackage(@PathParam("id") Long id) throws MyEntityNotFoundException {
        return PackageDTO.from(packageBean.find(id));
    }

}