package pt.ipleiria.estg.dei.ei.dae.monitor.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(
                name = "getAllOrders",
                query = "SELECT o FROM Order o ORDER BY o.id"
        )
})
public class Order implements Serializable {
    @Id
    private Long id;

    private String customerId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Volume> volumes;

    private String estado;
    // todo: Adicionar package ????

    public Order() {}
    public Order(Long id, String customerId, String estado) {
        this.id = id;
        this.customerId = customerId;
        this.volumes = new ArrayList<>();
        this.estado = estado;
    }

    public void add(Volume volume) {
        volumes.add(volume);
    }


    /**
     *
     * GETTERS & SETTERS
     */

    public Long getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public String getEstado() {
        return estado;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
