package pt.ipleiria.estg.dei.ei.dae.monitor.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.OrderHistory;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class OrderEstadoDTO implements Serializable {
    private String estado;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String timestamp;

    public OrderEstadoDTO() {}

    public OrderEstadoDTO(String estado, String timestamp) {
        this.estado = estado;
        this.timestamp = timestamp;
    }

    public static OrderEstadoDTO from(OrderHistory history) {
        return new OrderEstadoDTO(
                history.getEstado(),
                history.getTimestamp().toString()
        );
    }

    /**
     *
     * Getters and setters
     */


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
