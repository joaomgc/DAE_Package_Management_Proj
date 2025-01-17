package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProductBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create( String productId, String productName, String productType) throws MyEntityExistsException{
        String id = productId.toUpperCase();
        if (exists(id)) {
            throw new MyEntityExistsException("Id for product already exists");
        }
        try {
            var product = new Product(id, productName, productType);
            entityManager.persist(product);
            entityManager.flush();
        } catch (ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Product> findAll() {
        return entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    public Product find(String productId) throws MyEntityNotFoundException {
        var product = entityManager.find(Product.class, productId.toUpperCase());
        if (product == null) {
            throw new MyEntityNotFoundException("Product with id "+productId+" not found");
        }
        return product;
    }

    public boolean exists(String productId) {
        var product = entityManager.find(Product.class, productId.toUpperCase());
        return product != null;
    }

    public void update(Product product) {
        entityManager.merge(product);
    }

    public void delete(String id) throws MyEntityNotFoundException {
        Product product = find(id);
        entityManager.remove(product);

    }

}
