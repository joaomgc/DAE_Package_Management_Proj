package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Product;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class PackageBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private ProductBean productBean;

    public void create( String packageId, String packageType) {
        var pck = new Package(packageId, packageType);

        entityManager.persist(pck);
    }
    public List<Package> findAll() {
        return entityManager.createNamedQuery("getAllPackages", Package.class).getResultList();
    }

    public Package find(String packageId) {
        return entityManager.find(Package.class, packageId);
    }

    public void update(Package pck) {
        entityManager.merge(pck);
    }

    public void delete(String id) {
        Package pck = find(id);
        if (pck != null) {
            entityManager.remove(pck);
        }
    }

}
