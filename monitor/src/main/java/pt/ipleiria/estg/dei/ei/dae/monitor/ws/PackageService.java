package pt.ipleiria.estg.dei.ei.dae.monitor.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.PackageBean;

import java.util.List;

@Path("/packages")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PackageService {
    @EJB
    private PackageBean packageBean;

    @GET
    @Path("/all")
    public List<PackageDTO> getAllPackages() {
        return PackageDTO.from(packageBean.findAll());
    }

    @POST
    @Path("/")
    public void createNewPackage(PackageDTO packageDTO) {
        packageBean.create(
                packageDTO.getPackageId(),
                packageDTO.getPackageType()
        );
    }

    @PUT
    @Path("/{id}")
    public void updatePackage(@PathParam("id") String id, PackageDTO packageDTO) {
        var pck = packageBean.find(id);
        pck.setPackageType(packageDTO.getPackageType());
        packageBean.update(pck);
    }

    @DELETE
    @Path("/{id}")
    public void deletePackage(@PathParam("id") String id) {
        packageBean.delete(id);
    }

    @GET
    @Path("/{id}")
    public PackageDTO getPackage(@PathParam("id") String id) {
        return PackageDTO.from(packageBean.find(id));
    }

    @POST
    @Path("/{packageId}/products/{productId}")
    public void addProductToPackage(@PathParam("packageId") String packageId, @PathParam("productId") Long productId) {
        packageBean.addProduct(packageId, productId);
    }
}