package pt.ipleiria.estg.dei.ei.dae.monitor.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_history")
@NamedQuery(
        name = "getOrderHistory",
        query = "SELECT h FROM OrderHistory h WHERE h.order.id = :orderId ORDER BY h.timestamp"
)

public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @NotNull
    private String estado;

    @NotNull
    private LocalDateTime timestamp;

    public OrderHistory() {}

    public OrderHistory(Order order, String estado, LocalDateTime timestamp) {
        this.order = order;
        this.estado = estado;
        this.timestamp = timestamp;
    }


    /**
     *
     */
    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
