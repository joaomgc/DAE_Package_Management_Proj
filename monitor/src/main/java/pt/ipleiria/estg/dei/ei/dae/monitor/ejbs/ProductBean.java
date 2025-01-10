package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Product;

import java.util.List;

@Stateless
public class ProductBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create( Long productId, String productName, String productType) {
        var product = new Product(productId, productName, productType);
        entityManager.persist(product);
    }

    public List<Product> findAll() {
        return entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    public Product find(Long productId) {
        var product = entityManager.find(Product.class, productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        return entityManager.find(Product.class, productId);
    }

    public void update(Product product) {
        entityManager.merge(product);
    }

    public void delete(Long id) {
        Product product = find(id);
        if (product != null) {
            entityManager.remove(product);
        }
    }

}
