package pt.ipleiria.estg.dei.ei.dae.monitor.dtos;

import pt.ipleiria.estg.dei.ei.dae.monitor.entities.OrderHistory;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class OrderHistoryDTO implements Serializable {
    private Long encomendaId;
    private List<OrderEstadoDTO> historicoEstados;

    public OrderHistoryDTO() {}

    public OrderHistoryDTO(Long encomendaId, List<OrderEstadoDTO> historicoEstados) {
        this.encomendaId = encomendaId;
        this.historicoEstados = historicoEstados;
    }

    public static OrderHistoryDTO from(Long orderId, List<OrderHistory> history) {
        List<OrderEstadoDTO> estados = history.stream()
                .map(OrderEstadoDTO::from)
                .collect(Collectors.toList());

        return new OrderHistoryDTO(orderId, estados);
    }

    public Long getEncomendaId() {
        return encomendaId;
    }

    public void setEncomendaId(Long encomendaId) {
        this.encomendaId = encomendaId;
    }

    public List<OrderEstadoDTO> getHistoricoEstados() {
        return historicoEstados;
    }

    public void setHistoricoEstados(List<OrderEstadoDTO> historicoEstados) {
        this.historicoEstados = historicoEstados;
    }

}
