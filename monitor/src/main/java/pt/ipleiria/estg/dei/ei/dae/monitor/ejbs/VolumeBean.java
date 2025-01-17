package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.*;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityNotFoundException;


import java.util.List;

@Stateless
public class VolumeBean {

    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private ProductBean productBean;
    @EJB
    private PackageBean packageBean;
    @EJB
    private SensorBean sensorBean;

    public boolean exists(Long id){
        Volume volume = entityManager.find(Volume.class, id);
        return volume != null;
    }

    public void create(Long id, String volumeName, Long packageId) throws MyEntityNotFoundException, MyEntityExistsException {
        Package pck = packageBean.find(packageId);
        if (exists(id)) {
            throw new MyEntityExistsException("Volume with id "+id+" already exists");
        }
        try {
            Volume volume = new Volume(id, volumeName, pck, null);
            entityManager.persist(volume);
            entityManager.flush();
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void create(Long id, String volumeName, Long packageId, String productId, int quantity)
            throws MyEntityNotFoundException, MyEntityExistsException {
        Package pck = packageBean.find(packageId);
        if (exists(id)) {
            throw new MyEntityExistsException("Volume with id "+id+" already exists");
        }
        try {
            Volume volume = new Volume(id, volumeName, pck, null);
            if (quantity < 0) {
                throw new IllegalArgumentException("Quantity must be a positive number");
            }
            Product product = productBean.find(productId);
            volume.addVolumeProduct(product, quantity);
            entityManager.persist(volume);
            entityManager.flush();
        } catch (ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Volume> findAll() {
        return entityManager.createNamedQuery("getAllVolumes", Volume.class).getResultList();
    }

    public Volume find(Long id) throws MyEntityNotFoundException {
        var volume = entityManager.find(Volume.class, id);
        if (volume == null) {
            throw new MyEntityNotFoundException("Volume not found");
        }
        return volume;
    }

    public void update(Volume volume) {
        entityManager.merge(volume);
    }

    public void addPackage(Long id, Long packageId) throws MyEntityNotFoundException{
        Volume volume = find(id);
        Package pack = packageBean.find(packageId);
        volume.setPack(pack);
        entityManager.merge(volume);
    }

    public void addProduct(Long volumeId, String productId, int quantidade) throws MyEntityNotFoundException{
        Volume volume = find(volumeId);
        String prodId = productId.toUpperCase();
        Product product = productBean.find(prodId);
        VolumeProduct vp = new VolumeProduct(volume, product, quantidade);
        entityManager.persist(vp);
    }

    public void associateSensor(Long volumeId, String sensorId) throws MyEntityNotFoundException{
        Volume volume = find(volumeId);
        SensorSimulator sensor = sensorBean.find(sensorId);

        volume.setSensor(sensor);
        sensor.setVolume(volume);
        sensor.setStatus("Active");
        entityManager.merge(volume);
        entityManager.merge(sensor);
    }

    public void delete(Long id) throws MyEntityNotFoundException{
        Volume volume = find(id);
        entityManager.remove(volume);
    }
}