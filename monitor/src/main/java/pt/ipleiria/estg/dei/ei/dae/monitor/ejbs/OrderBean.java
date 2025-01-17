package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.ForbiddenException;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.*;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager em;
    @EJB
    private VolumeBean volumeBean;
    @EJB
    private ClientBean clientBean;

    public boolean exists(long orderId) {
        Query query = em.createQuery(
                "SELECT COUNT(o.id) FROM Order o WHERE o.id = :orderId",
                Long.class
        );
        query.setParameter("orderId", orderId);
        return (Long)query.getSingleResult() > 0L;
    }

    public void create(Long id, String customerUsername, String estado) throws MyEntityExistsException, MyEntityNotFoundException {
        if (exists(id)) {
            throw new MyEntityExistsException("Order with id "+ id +" already exists");
        }
        Client client = clientBean.find(customerUsername);
        try {
            var order = new Order(id, client, estado);
            em.persist(order);
            em.flush();
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void update(Order order) {
        em.merge(order);
    }

    public void delete(Long id) throws MyEntityNotFoundException {
        Order order = find(id);
        em.remove(order);

    }

    public Order find(Long id) throws MyEntityNotFoundException {
        var order = em.find(Order.class, id);
        if (order == null) {
            throw new MyEntityNotFoundException("Order with id "+id+" not found");
        }
        return order;
    }

    public void addVolume(Long orderId, Long volumeId) throws MyEntityNotFoundException{
        Order order = find(orderId);
        Volume volume = volumeBean.find(volumeId);
        if (volume.getOrder() != null && !volume.getOrder().equals(order)) {
            throw new ForbiddenException("Volume is already associated with other order");
        }
        volume.setOrder(order);
        order.add(volume);
        em.merge(order);
        em.merge(volume);
    }

    public void removeVolume(Long orderId) {
        List<Volume> volumes = em.createQuery("SELECT v FROM Volume v WHERE v.order.id = :orderId", Volume.class)
                .setParameter("orderId", orderId)
                .getResultList();
        for (Volume volume : volumes) {
            volume.setOrder(null);
            em.merge(volume);
        }
    }

    public List<OrderHistory> getOrderHistory(Long orderId) {
        return em.createNamedQuery("getOrderHistory", OrderHistory.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    public List<Order> findAll() {
        return em.createNamedQuery("getAllOrders", Order.class).getResultList();
    }
}
