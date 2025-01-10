package pt.ipleiria.estg.dei.ei.dae.monitor.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "volume_products")
public class VolumeProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne // um volume pode aparecer em vários VolumeProduct
    @NotNull
    @JoinColumn(name = "volume_id")
    private Volume volume;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantidade;

    public VolumeProduct() {}

    public VolumeProduct(Volume volume, Product product, int quantidade) {
        this.volume = volume;
        this.product = product;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public Volume getVolume() {
        return volume;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
