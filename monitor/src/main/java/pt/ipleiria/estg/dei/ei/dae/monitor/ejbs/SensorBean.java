package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.SensorSimulator;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Volume;

import java.util.List;

@Stateless
public class SensorBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(String id, String tipo) {
        SensorSimulator sensor = new SensorSimulator(id, tipo);
        entityManager.persist(sensor);
    }

    public List<SensorSimulator> findAll() {
        return entityManager.createNamedQuery("getAllSensors", SensorSimulator.class).getResultList();
    }

    public SensorSimulator find(String id) {
        return entityManager.find(SensorSimulator.class, id);
    }

    public void update(SensorSimulator sensor) {
        entityManager.merge(sensor);
    }

    public void delete(String id) {
        SensorSimulator sensor = find(id);
        if (sensor != null) {
            entityManager.remove(sensor);
        }
    }

    public void associateVolume(String sensorId, Long volumeId) {
        SensorSimulator sensor = find(sensorId);
        Volume volume = entityManager.find(Volume.class, volumeId);
        if (sensor != null && volume != null) {
            sensor.setVolume(volume);
            volume.setSensor(sensor);
            entityManager.merge(sensor);
            entityManager.merge(volume);
        }
    }

    @Schedule(hour = "*", minute = "*", second = "*/30", persistent = false)
    public void generateSensorData() {
        List<SensorSimulator> sensors = findAll();
        for (SensorSimulator sensor : sensors) {
            if ("Active".equals(sensor.getStatus())) {
                sensor.gerarDados();
                update(sensor);
            }
        }
    }
}