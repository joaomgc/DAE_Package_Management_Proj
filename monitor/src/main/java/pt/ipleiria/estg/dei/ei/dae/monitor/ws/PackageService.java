package pt.ipleiria.estg.dei.ei.dae.monitor.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
}
