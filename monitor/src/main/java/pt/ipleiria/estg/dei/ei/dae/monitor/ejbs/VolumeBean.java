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
    @EJB
    private PackageBean packageBean;

    public void create(Long id, String volumeName, Long packageId) {
        Package pck = packageBean.find(packageId);
        Volume volume = new Volume(id, volumeName, pck, null);
        entityManager.persist(volume);
    }

    public void create(Long id, String volumeName, Long packageId, String productId, int quantity) {
        Package pck = packageBean.find(packageId);
        Volume volume = new Volume(id, volumeName, pck, null);
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

    public void addPackage(Long id, Long packageId) {
        Volume volume = find(id);
        Package pack = packageBean.find(packageId);
        volume.setPack(pack);
        entityManager.merge(volume);
    }

    public void addProduct(Long volumeId, String productId, int quantidade) {
        Volume volume = find(volumeId);
        String prodId = productId.toUpperCase();
        Product product = productBean.find(prodId);
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