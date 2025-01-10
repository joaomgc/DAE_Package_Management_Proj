package pt.ipleiria.estg.dei.ei.dae.monitor.dtos;

import java.io.Serializable;

public class ProductQuantityDTO implements Serializable {
    private Long productId;
    private String productName;
    private int quantidade;

    public ProductQuantityDTO() {}

    public ProductQuantityDTO(Long productId, String productName, int quantidade) {
        this.productId = productId;
        this.productName = productName;
        this.quantidade = quantidade;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
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
