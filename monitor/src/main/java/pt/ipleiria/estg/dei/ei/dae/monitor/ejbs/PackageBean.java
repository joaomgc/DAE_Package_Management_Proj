// src/main/java/pt/ipleiria/estg/dei/ei/dae/monitor/ejbs/PackageBean.java
package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class PackageBean {
    @PersistenceContext
    private EntityManager em;

    public boolean exists(Long id) {
        Package pck = em.find(Package.class, id);
        return pck != null;
    }

    public void create(Long packageId, String packageType) throws MyEntityExistsException {
        if (exists(packageId)) {
            throw new MyEntityExistsException("Package with id "+packageId+" already exists!");
        }
        try {
            Package pck = new Package(packageId, packageType);
            em.persist(pck);
            em.flush();
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public Package find(Long packageId) throws MyEntityNotFoundException {
        Package pck = em.find(Package.class, packageId);
        if (pck == null) {
            throw new MyEntityNotFoundException("Package with id " + packageId + " not found");
        }
        return pck;
    }

    public void update(Package pck) {
        em.merge(pck);
    }

    public void delete(Long packageId) throws MyEntityNotFoundException {
        Package pck = find(packageId);
        em.remove(pck);
    }

    public List<Package> findAll() {
        return em.createNamedQuery("getAllPackages", Package.class).getResultList();
    }
}