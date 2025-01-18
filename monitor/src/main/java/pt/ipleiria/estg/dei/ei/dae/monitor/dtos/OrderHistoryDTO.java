package pt.ipleiria.estg.dei.ei.dae.monitor.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.OrderHistory;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderHistoryDTO implements Serializable {
    private Long encomendaId;
    private String estado;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public OrderHistoryDTO() {}

    public OrderHistoryDTO(Long encomendaId, String estado, LocalDateTime timestamp) {
        this.encomendaId = encomendaId;
        this.estado = estado;
        this.timestamp = timestamp;
    }

    public Long getEncomendaId() {
        return encomendaId;
    }

    public void setEncomendaId(Long encomendaId) {
        this.encomendaId = encomendaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public static OrderHistoryDTO from(OrderHistory history) {
        return new OrderHistoryDTO(
                history.getOrder().getId(),
                history.getEstado(),
                history.getTimestamp()
        );
    }

    public static List<OrderHistoryDTO> from(List<OrderHistory> histories) {
        return histories.stream().map(OrderHistoryDTO::from).collect(Collectors.toList());
    }
}
