package pt.ipleiria.estg.dei.ei.dae.monitor.dtos;

import pt.ipleiria.estg.dei.ei.dae.monitor.entities.SensorHistory;

import java.time.LocalDateTime;

public class SensorHistoryDTO {
    private Long id;
    private String sensorId;
    private LocalDateTime timestamp;
    private double valor;

    public SensorHistoryDTO() {
    }

    public SensorHistoryDTO(Long id, String sensorId, LocalDateTime timestamp, double valor) {
        this.id = id;
        this.sensorId = sensorId;
        this.timestamp = timestamp;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public static SensorHistoryDTO from(SensorHistory sensorHistory) {
        return new SensorHistoryDTO(
                sensorHistory.getId(),
                sensorHistory.getSensor().getId(),
                sensorHistory.getTimestamp(),
                sensorHistory.getValor());
    }


}

