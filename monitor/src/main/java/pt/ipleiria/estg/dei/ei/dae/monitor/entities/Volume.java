package pt.ipleiria.estg.dei.ei.dae.monitor.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.monitor.dtos.ProductQuantityDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "volumes")
@NamedQueries({
        @NamedQuery(
                name = "getAllVolumes",
                query = "SELECT v FROM Volume v ORDER BY v.id" // JPQL
        )
})
public class Volume implements Serializable {
    @Id
    private Long id;

    @NotNull
    private String volumeName;

    @OneToOne(mappedBy = "volume")
    private SensorSimulator sensor;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private Package pack;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToMany(mappedBy = "volume", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<VolumeProduct> volumeProducts;

    public Volume() {
        this.volumeProducts = new ArrayList<>();
    }

    public Volume(Long id, String volumeName, Package pack, SensorSimulator sensor) {
        this.id = id;
        this.volumeName = volumeName;
        this.pack = pack;
        this.sensor = sensor;
        this.volumeProducts = new ArrayList<>();
    }

    public List<VolumeProduct> getVolumeProducts() {
        return volumeProducts;
    }

    public List<ProductQuantityDTO> getProductQuantityDTOs() {
        return volumeProducts.stream()
                .map(vp -> new ProductQuantityDTO(
                        vp.getProduct().getProductId(),
                        vp.getProduct().getProductName(),
                        vp.getQuantidade()
                ))
                .toList();
    }


    public void addVolumeProduct(Product product, int quantidade) {
        VolumeProduct volumeProduct = new VolumeProduct(this, product, quantidade);
        volumeProducts.add(volumeProduct);
    }

    public void removeVolumeProduct(Product product) {
        volumeProducts.removeIf(vp -> vp.getProduct().equals(product));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }

    public SensorSimulator getSensor() {
        return sensor;
    }

    public void setSensor(SensorSimulator sensor) {
        this.sensor = sensor;
    }

    public Package getPack() {
        return pack;
    }

    public void setPack(Package pack) {
        this.pack = pack;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}