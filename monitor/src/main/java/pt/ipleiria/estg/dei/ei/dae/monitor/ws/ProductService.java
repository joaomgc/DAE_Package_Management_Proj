package pt.ipleiria.estg.dei.ei.dae.monitor.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Product;

import java.util.List;

@Path("/products")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProductService {
    @EJB
    private ProductBean productBean;

    @GET
    @Path("/")
    public List<ProductDTO> getAllProducts() {
        return ProductDTO.from(productBean.findAll());
    }

    @GET
    @Path("/{id}")
    public Response getProduct(@PathParam("id") String id) {
        Product product = productBean.find(id);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(ProductDTO.from(product)).build();
    }

    @POST
    @Path("/")
    public Response createNewProduct(ProductDTO productDTO) {
        productBean.create(
                productDTO.getProductId(),
                productDTO.getProductName(),
                productDTO.getProductType()
        );
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") String id, ProductDTO productDTO) {
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
    public Response deleteProduct(@PathParam("id") String id) {
        Product product = productBean.find(id);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        productBean.delete(id);
        return Response.noContent().build();
    }
}