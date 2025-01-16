package pt.ipleiria.estg.dei.ei.dae.monitor.dtos;

import java.io.Serializable;

public class ProductNameQuantityDTO implements Serializable {
    private String productId;
    private String productName;
    private int quantidade;

    public ProductNameQuantityDTO() {}

    public ProductNameQuantityDTO(String productId, String productName, int quantidade) {
        this.productId = productId;
        this.productName = productName;
        this.quantidade = quantidade;
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

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
