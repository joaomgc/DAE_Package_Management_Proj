package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import java.util.LinkedList;

public class Sensor {

    private Long id;
    private String sensorId;
    private String sensorType;
    private LinkedList<SensorReading> sensorReadings;


    public Sensor(Long id, String sensorId, String sensorType, LinkedList<SensorReading> sensorReadings) {
        this.id = id;
        this.sensorId = sensorId;
        this.sensorType = sensorType;
        this.sensorReadings = sensorReadings;
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

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public LinkedList<SensorReading> getSensorReadings() {
        return new LinkedList<>(sensorReadings);
    }

    public void setSensorReadings(LinkedList<SensorReading> sensorReadings) {
        this.sensorReadings = sensorReadings;
    }
}