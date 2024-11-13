package pt.ipleiria.estg.dei.ei.dae.backend.entities;

public class Product {

    private Long id;
    private String productId;
    private String productName;
    private String productType;


    public Product(Long id, String productId, String productName, String productType) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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