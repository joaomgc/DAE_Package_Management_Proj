package pt.ipleiria.estg.dei.ei.dae.monitor.dtos;

import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO implements Serializable {
    private Long encomendaId;
    private List<VolumeDTO> volumes;
    private String clientUsername;
    private String estado;

    public OrderDTO() {}
    public OrderDTO(Long encomendaId, String clientUsername, String estado, List<VolumeDTO> volumes) {
        this.encomendaId = encomendaId;
        this.clientUsername = clientUsername;
        this.volumes = volumes;
        this.estado = estado;
    }

    public static OrderDTO from(Order order) {
        List<VolumeDTO> volumeDTOs = order.getVolumes()
                .stream()
                .map(VolumeDTO::from) // Assuming a `from(Volume)` method exists in VolumeDTO
                .collect(Collectors.toList());

        return new OrderDTO(
                order.getId(),
                order.getClient().getUsername(),
                order.getEstado(),
                volumeDTOs
        );
    }

    public static List<OrderDTO> from(List<Order> orders) {
        return orders.stream().map(OrderDTO::from).collect(Collectors.toList());
    }

    public Long getEncomendaId() {
        return encomendaId;
    }

    public List<VolumeDTO> getVolumes() {
        return volumes;
    }

    public String getClientUsername() {
        return clientUsername;
    }
    public String getEstado() {
        return estado;
    }

    public void setEncomendaId(Long encomendaId) {
        this.encomendaId = encomendaId;
    }

    public void setVolumes(List<VolumeDTO> volumes) {
        this.volumes = volumes;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
