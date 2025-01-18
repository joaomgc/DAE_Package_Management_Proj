package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.SensorHistory;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.SensorSimulator;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class SensorHistoryBean {

    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private SensorBean sensorBean;


    public void create(SensorSimulator sensor, LocalDateTime timestamp, double valor) {
        try {
            SensorHistory history = new SensorHistory(sensor, timestamp, valor);
            entityManager.persist(history);
            entityManager.flush();
        } catch (ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }
    }

    public List<SensorHistory> findAll() {
        return entityManager.createNamedQuery("SensorHistory.findAll", SensorHistory.class).getResultList();
    }

    public List<SensorHistory> findBySensorId(String sensorId) throws MyEntityNotFoundException {
        sensorBean.find(sensorId); // se sensor nao existe, lança erro
        return entityManager.createQuery(
                        "SELECT h FROM SensorHistory h WHERE h.sensor.id = :sensorId", SensorHistory.class)
                .setParameter("sensorId", sensorId)
                .getResultList();
    }

    public void update(SensorHistory history) {
        entityManager.merge(history);
    }

    public void delete(Long id) throws MyEntityNotFoundException {
        SensorHistory history = find(id);
        entityManager.remove(history);
    }

    public SensorHistory find(Long id) throws MyEntityNotFoundException {
        SensorHistory history = entityManager.find(SensorHistory.class, id);
        if (history == null) {
            throw new MyEntityNotFoundException("History for sensor not found");
        }
        return history;
    }

    public void deleteBySensorId(String sensorId) throws MyEntityNotFoundException {
        List<SensorHistory> histories = findBySensorId(sensorId);
        for (SensorHistory history : histories) {
            entityManager.remove(history);
        }
    }
}
