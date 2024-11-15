package pt.ipleiria.estg.dei.ei.dae.monitor.ws;


import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.monitor.ejbs.ProductBean;

import java.util.List;

@Path("/products")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})

public class ProductService {
    @EJB
    private ProductBean productBean;

    @GET
    @Path("/all")
    public List<ProductDTO> getAllPackages() {
        return ProductDTO.from(productBean.findAll());
    }

    @POST
    @Path("/")
    public void createNewProduct(ProductDTO productDTO) {
        productBean.create(
                productDTO.getProductId(),
                productDTO.getProductName(),
                productDTO.getProductType()
        );
    }
}
