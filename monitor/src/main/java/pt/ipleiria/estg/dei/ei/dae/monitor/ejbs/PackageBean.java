// src/main/java/pt/ipleiria/estg/dei/ei/dae/monitor/ejbs/PackageBean.java
package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Product;

import java.util.List;

@Stateless
public class PackageBean {
    @PersistenceContext
    private EntityManager em;

    public void create(Long packageId, String packageType) {
        Package pck = new Package(packageId, packageType);
        em.persist(pck);
    }

    public Package find(Long packageId) {
        Package pck = em.find(Package.class, packageId);
        if (pck == null) {
            throw new IllegalArgumentException("Package with id " + packageId + " not found");
        }
        return pck;
    }

    public void update(Package pck) {
        em.merge(pck);
    }

    public void delete(Long packageId) {
        Package pck = find(packageId);
        if (pck != null) {
            em.remove(pck);
        }
    }

    public List<Package> findAll() {
        return em.createNamedQuery("getAllPackages", Package.class).getResultList();
    }
}