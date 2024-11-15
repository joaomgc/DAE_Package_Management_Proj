package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Package;

@Stateless
public class PackageBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create( String packageId, String packageType) {
        var pck = new Package(packageId, packageType);
        entityManager.persist(pck);
    }

}
