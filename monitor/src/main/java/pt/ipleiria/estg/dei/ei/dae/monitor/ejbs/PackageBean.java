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

    public void create( String packageId, String packageType, List<String> productIds) {
        var pck = new Package(packageId, packageType);
        List<Product> products = productIds.stream().map(productBean::find).toList();
        pck.setProducts(products);
        entityManager.persist(pck);
    }
    public List<Package> findAll() {
        return entityManager.createNamedQuery("getAllPackages", Package.class).getResultList();
    }

}
