package pt.ipleiria.estg.dei.ei.dae.monitor.dtos;

import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Product;

import java.io.Serializable;
import java.util.List;

public class ProductDTO implements Serializable {
    private Long id;
    private String name;
    private String type;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ProductDTO from(Product product) {
        return new ProductDTO(
                product.getProductId(),
                product.getProductName(),
                product.getProductType()
        );
    }
    public static List<ProductDTO> from(List<Product> products){
        return products.stream().map(ProductDTO::from).toList();
    }
}