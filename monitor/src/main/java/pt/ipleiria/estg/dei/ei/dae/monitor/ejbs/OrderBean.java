package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Volume;

import java.util.List;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager em;
    @EJB
    private VolumeBean volumeBean;
    @EJB
    private UserBean userBean;

    public boolean exists(long orderId) {
        Query query = em.createQuery(
                "SELECT COUNT(o.id) FROM Order o WHERE o.id = :orderId",
                Long.class
        );
        query.setParameter("orderId", orderId);
        return (Long)query.getSingleResult() > 0L;
    }

    public void create(Long id, String customerId, String estado) {
        if (exists(id)) {
            throw new RuntimeException("Order already exists");
        }
        if (!userBean.exists(customerId)) {
            throw new RuntimeException("Customer does not exist");
        }
        var order = new Order(id, customerId, estado);
        em.persist(order);
    }

    public Order find(Long id) {
        var order = em.find(Order.class, id);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }
        return order;
    }

    public void addVolume(Long orderId, Long volumeId) {
        Order order = find(orderId);
        Volume volume = volumeBean.find(volumeId);
        if (volume.getOrder() != null && !volume.getOrder().equals(order)) {
            throw new RuntimeException("Volume is already associated with other order");
        }
        volume.setOrder(order);
        order.add(volume);
        em.merge(order);
        em.merge(volume);
    }

    public List<Order> findAll() {
        return em.createNamedQuery("getAllOrders", Order.class).getResultList();
    }
}
