package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.SensorSimulator;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Package;


import java.util.List;

@Stateless
public class VolumeBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Long id, String volumeName, Package pack) {
        Volume volume = new Volume(id, volumeName, pack, null);
        entityManager.persist(volume);
    }

    public List<Volume> findAll() {
        return entityManager.createNamedQuery("getAllVolumes", Volume.class).getResultList();
    }

    public Volume find(Long id) {
        return entityManager.find(Volume.class, id);
    }

    public void update(Volume volume) {
        entityManager.merge(volume);
    }

    public void addPackage(Long id, String packageId) {
        Volume volume = find(id);
        Package pack = entityManager.find(Package.class, packageId);
        if (volume != null && pack != null) {
            volume.setPack(pack);
            entityManager.merge(volume);
        }
    }

    public void associateSensor(Long volumeId, String sensorId) {
        Volume volume = find(volumeId);
        SensorSimulator sensor = entityManager.find(SensorSimulator.class, sensorId);
        if (volume != null && sensor != null) {
            volume.setSensor(sensor);
            sensor.setVolume(volume);
            sensor.setStatus("Active");
            entityManager.merge(volume);
            entityManager.merge(sensor);
        }
    }

    public void delete(Long id) {
        Volume volume = find(id);
        if (volume != null) {
            entityManager.remove(volume);
        }
    }
}