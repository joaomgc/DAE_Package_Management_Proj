package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.*;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Package;


import java.util.List;

@Stateless
public class VolumeBean {

    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private ProductBean productBean;

    public void create(Long id, String volumeName, Package pack) {
        Volume volume = new Volume(id, volumeName, pack, null);
        entityManager.persist(volume);
    }

    public void create(Long id, String volumeName, Package pack, Long productId, int quantity) {
        Volume volume = new Volume(id, volumeName, pack, null);
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be a positive number");
        }
        Product product = productBean.find(productId);
        volume.addVolumeProduct(product, quantity);
        entityManager.persist(volume);
    }

    public List<Volume> findAll() {
        return entityManager.createNamedQuery("getAllVolumes", Volume.class).getResultList();
    }

    public Volume find(Long id) {
        var volume = entityManager.find(Volume.class, id);
        if (volume == null) {
            throw new IllegalArgumentException("Volume not found");
        }
        return volume;
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

    public void addProduct(Long volumeId, Long productId, int quantidade) {
        Volume volume = find(volumeId);
        Product product = productBean.find(productId);
        VolumeProduct vp = new VolumeProduct(volume, product, quantidade);
        entityManager.persist(vp);
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