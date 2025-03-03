package pt.ipleiria.estg.dei.ei.dae.monitor.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
@NamedQueries({
        @NamedQuery(
                name = "getAllProducts",
                query = "SELECT p FROM Product p ORDER BY p.productName"
        )
})

public class Product {
    @Id
    private String productId;
    private String productName;
    private String productType;


    public Product() {
    }

    public Product(String productId, String productName, String productType) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
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
}