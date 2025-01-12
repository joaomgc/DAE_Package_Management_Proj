package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.SensorHistory;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.SensorSimulator;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class SensorHistoryBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(SensorSimulator sensor, LocalDateTime timestamp, double valor) {
        SensorHistory history = new SensorHistory(sensor, timestamp, valor);
        entityManager.persist(history);
    }

    public List<SensorHistory> findAll() {
        return entityManager.createQuery("SELECT h FROM SensorHistory h", SensorHistory.class).getResultList();
    }

    public List<SensorHistory> findBySensorId(String sensorId) {
        return entityManager.createQuery(
                        "SELECT h FROM SensorHistory h WHERE h.sensor.id = :sensorId", SensorHistory.class)
                .setParameter("sensorId", sensorId)
                .getResultList();
    }

    public void update(SensorHistory history) {
        entityManager.merge(history);
    }

    public void delete(Long id) {
        SensorHistory history = entityManager.find(SensorHistory.class, id);
        if (history != null) {
            entityManager.remove(history);
        }
    }

    public void deleteBySensorId(String sensorId) {
        List<SensorHistory> histories = findBySensorId(sensorId);
        for (SensorHistory history : histories) {
            entityManager.remove(history);
        }
    }
}
