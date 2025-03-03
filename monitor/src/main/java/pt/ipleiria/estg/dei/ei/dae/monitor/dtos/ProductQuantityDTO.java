package pt.ipleiria.estg.dei.ei.dae.monitor.dtos;

public class ProductQuantityDTO {
    private String productId;
    private int quantidade;

    public ProductQuantityDTO() {}

    public ProductQuantityDTO(String productId, int quantidade) {
        this.productId = productId;
        this.quantidade = quantidade;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
