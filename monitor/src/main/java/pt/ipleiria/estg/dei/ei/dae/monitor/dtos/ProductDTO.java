package pt.ipleiria.estg.dei.ei.dae.monitor.dtos;

import jakarta.persistence.Id;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Product;

import java.io.Serializable;
import java.util.List;

public class ProductDTO implements Serializable {
    @Id
    private String productId;
    private String productName;
    private String productType;

    public ProductDTO() {
    }

    public ProductDTO(String id, String name, String type) {
        this.productId = id;
        this.productName = name;
        this.productType = type;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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