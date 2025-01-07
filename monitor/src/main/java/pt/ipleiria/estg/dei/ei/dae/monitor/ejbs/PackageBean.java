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

    public void create(String packageId, String packageType) {
        Package pck = new Package(packageId, packageType);
        em.persist(pck);
    }

    public Package find(String packageId) {
        return em.find(Package.class, packageId);
    }

    public void update(Package pck) {
        em.merge(pck);
    }

    public void delete(String packageId) {
        Package pck = find(packageId);
        if (pck != null) {
            em.remove(pck);
        }
    }

    public void addProduct(String packageId, Long productId) {
        Package pck = find(packageId);
        Product product = em.find(Product.class, productId);
        if (pck != null && product != null) {
            pck.addProduct(product);
            em.merge(pck);
        }
    }

    public List<Package> findAll() {
        return em.createNamedQuery("getAllPackages", Package.class).getResultList();
    }
}