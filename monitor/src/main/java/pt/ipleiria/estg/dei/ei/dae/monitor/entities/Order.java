package pt.ipleiria.estg.dei.ei.dae.monitor.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Order {
    @Id
    private Long id;

    private String customerId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Volume> volumes;

    private String estado; // "Pending", "Shipped", etc.

    public Order() {}
    public Order(Long id, String customerId, List<Volume> volumes, String estado) {
        this.id = id;
        this.customerId = customerId;
        this.volumes = volumes;
        this.estado = estado;
    }

}
