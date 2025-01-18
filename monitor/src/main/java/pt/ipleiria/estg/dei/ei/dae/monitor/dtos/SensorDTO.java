package pt.ipleiria.estg.dei.ei.dae.monitor.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.SensorSimulator;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SensorDTO implements Serializable {
    private String id;
    private String tipo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private double valor;
    private String status;
    private Long volumeId;

    public SensorDTO() {
    }

    public SensorDTO(String id, String tipo, LocalDateTime timestamp, double valor, String status, Long volumeId) {
        this.id = id;
        this.tipo = tipo;
        this.timestamp = timestamp;
        this.valor = valor;
        this.status = status;
        this.volumeId = volumeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Long getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(Long volumeId) {
        this.volumeId = volumeId;
    }

    public static SensorDTO from(SensorSimulator sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getTipo().getNome(),
                sensor.getTimestamp(),
                sensor.getValor(),
                sensor.getStatus(),
                sensor.getVolume() != null ? sensor.getVolume().getId() : null
        );
    }
}

