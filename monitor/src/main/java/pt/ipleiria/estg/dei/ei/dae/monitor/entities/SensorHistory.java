package pt.ipleiria.estg.dei.ei.dae.monitor.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@NamedQueries({
        @NamedQuery(name = "SensorHistory.findAll", query = "SELECT sh FROM SensorHistory sh ORDER BY sh.timestamp DESC")
})
public class SensorHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sensor_id", nullable = false)
    private SensorSimulator sensor;

    private LocalDateTime timestamp;
    private double valor;
    private String tipo;

    public SensorHistory() {
    }

    public SensorHistory(SensorSimulator sensor, LocalDateTime timestamp, double valor) {
        this.sensor = sensor;
        this.timestamp = timestamp;
        this.valor = valor;
        this.tipo = sensor.getTipo().getNome();
    }

    public Long getId() {
        return id;
    }

    public SensorSimulator getSensor() {
        return sensor;
    }

    public void setSensor(SensorSimulator sensor) {
        this.sensor = sensor;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String type) {
        this.tipo = type;
    }

    @Override
    public String toString() {
        return "SensorHistory{" +
                "id=" + id +
                ", sensor=" + sensor +
                ", timestamp=" + timestamp +
                ", valor=" + valor +
                '}';
    }
}
