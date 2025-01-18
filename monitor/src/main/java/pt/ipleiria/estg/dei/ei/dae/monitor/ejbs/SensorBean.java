package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.SensorSimulator;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.TipoSensor;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class SensorBean {

    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private VolumeBean volumeBean;
    @EJB
    private TipoSensorBean tipoSensorBean;

    public boolean exists(String id) {
        SensorSimulator sensor = entityManager.find(SensorSimulator.class, id);
        return sensor != null;
    }

    public void create(String id, String tipo, String status) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        if (exists(id)) {
            throw new MyEntityExistsException("Sensor already exists");
        }
        var tipoSensor = tipoSensorBean.findByName(tipo);
        try {
            SensorSimulator sensor = new SensorSimulator(id, tipoSensor, status);
            entityManager.persist(sensor);
            entityManager.flush();
        } catch (ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }
    }

    public void create(String id, Long tipoSensorId, String status)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        // create com id do tipo de sensor, em vez de nome
        if (exists(id)) {
            throw new MyEntityExistsException("Sensor already exists");
        }

        TipoSensor tipoSensor = tipoSensorBean.find(tipoSensorId);
        try {
            SensorSimulator sensor = new SensorSimulator(id, tipoSensor, status);
            entityManager.persist(sensor);
            entityManager.flush();
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }


    public List<SensorSimulator> findAll() {
        return entityManager.createNamedQuery("getAllSensors", SensorSimulator.class).getResultList();
    }

    public SensorSimulator find(String id) throws MyEntityNotFoundException {
        SensorSimulator sensor = entityManager.find(SensorSimulator.class, id);
        if (sensor == null) {
            throw new MyEntityNotFoundException("Sensor does not exist");
        }
        return sensor;
    }

    public void update(SensorSimulator sensor) {
        entityManager.merge(sensor);
    }

    public void delete(String id) throws MyEntityNotFoundException {
        SensorSimulator sensor = find(id);
        entityManager.remove(sensor);

    }

    public void associateVolume(String sensorId, Long volumeId) throws MyEntityNotFoundException {
        SensorSimulator sensor = find(sensorId);
        Volume volume = volumeBean.find(volumeId);
        sensor.setVolume(volume);
        volume.setSensor(sensor);
        entityManager.merge(sensor);
        entityManager.merge(volume);
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