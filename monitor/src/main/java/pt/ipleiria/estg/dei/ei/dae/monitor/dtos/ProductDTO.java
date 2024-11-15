package pt.ipleiria.estg.dei.ei.dae.monitor.dtos;

import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Product;

import java.io.Serializable;
import java.util.List;

public class ProductDTO implements Serializable {
    private String id;
    private String name;
    private String category;

    public ProductDTO() {
    }

    public ProductDTO(String id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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