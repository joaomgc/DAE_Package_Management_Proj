package pt.ipleiria.estg.dei.ei.dae.monitor.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.monitor.security.Authenticated;

import java.util.List;

@Path("/products")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class ProductService {
    @EJB
    private ProductBean productBean;

    @GET
    @Path("/")
    @RolesAllowed({"Administrator"})
    public Response getAllProducts() {
        return Response.ok(ProductDTO.from(productBean.findAll())).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Administrator"})
    public Response getProduct(@PathParam("id") String id) throws MyEntityNotFoundException {
        Product product = productBean.find(id);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(ProductDTO.from(product)).build();
    }

    @POST
    @Path("/")
    @RolesAllowed({"Administrator"})
    public Response createNewProduct(ProductDTO productDTO) throws MyEntityExistsException {
        productBean.create(
                productDTO.getProductId(),
                productDTO.getProductName(),
                productDTO.getProductType()
        );
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Administrator"})
    public Response updateProduct(@PathParam("id") String id, ProductDTO productDTO) throws MyEntityNotFoundException {
        Product product = productBean.find(id);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        product.setProductName(productDTO.getProductName());
        product.setProductType(productDTO.getProductType());
        productBean.update(product);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Administrator"})
    public Response deleteProduct(@PathParam("id") String id) throws MyEntityNotFoundException {
        Product product = productBean.find(id);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        productBean.delete(id);
        return Response.noContent().build();
    }
}