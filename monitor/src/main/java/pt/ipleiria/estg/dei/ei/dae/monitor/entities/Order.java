package pt.ipleiria.estg.dei.ei.dae.monitor.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
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

    @ManyToOne
    @JoinColumn(name = "client_username")
    private Client client;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Volume> volumes;

    private String estado;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderHistory> historicoEstados = new ArrayList<>();

    public Order() {}
    public Order(Long id, Client client, String estado) {
        this.id = id;
        this.client = client;
        this.volumes = new ArrayList<>();
        this.estado = estado;
        this.historicoEstados = new ArrayList<>();
        this.historicoEstados.add(new OrderHistory(this, estado, LocalDateTime.now()));
    }

    public void add(Volume volume) {
        volumes.add(volume);
    }

    public void updateEstado(String novoEstado) {
        if (!this.estado.equals(novoEstado)) {
            this.estado = novoEstado;
            OrderHistory novoHistorico = new OrderHistory(this, novoEstado, LocalDateTime.now());
            this.historicoEstados.add(novoHistorico);
        }
    }
    /**
     *
     * GETTERS & SETTERS
     */

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
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

    public void setClient(Client client) {
        this.client = client;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<OrderHistory> getHistoricoEstados() {
        return historicoEstados;
    }
}
