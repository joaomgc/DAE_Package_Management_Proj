package pt.ipleiria.estg.dei.ei.dae.monitor.entities;

import java.time.LocalDateTime;

public class SensorReading {

    private Long id;
    private Sensor sensor;
    private LocalDateTime timestamp;
    private double value;

    public SensorReading(Long id, Sensor sensor, LocalDateTime timestamp, double value) {
        this.id = id;
        this.sensor = sensor;
        this.timestamp = timestamp;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
