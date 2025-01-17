package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.User;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.monitor.security.Hasher;

import java.util.List;

@Stateless
public class ClientBean {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private Hasher hasher;


    public boolean exists(String username) {
        Query query = em.createQuery(
                "SELECT COUNT(c.username) FROM Client c WHERE c.username = :username",
                Long.class
        );
        query.setParameter("username", username);
        return (Long)query.getSingleResult() > 0L;
    }

    public Client create(String username, String password, String name, String email)
            throws MyEntityExistsException, MyConstraintViolationException {
        if (exists(username)) {
            throw new MyEntityExistsException("Client with username'"+ username +"' already exists");
        }

        Client client = null;

        try {
            client = new Client(username, hasher.hash(password), name, email);
            client.setRole("C");
            System.out.println("Client created: " + username);
            em.persist(client);
            em.flush();
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }

        return client;
    }

    public void update(Client updatedClient) throws MyEntityNotFoundException {
        Client client = find(updatedClient.getUsername());

        em.lock(client, LockModeType.OPTIMISTIC);

        client.setName(updatedClient.getName());
        client.setPassword(hasher.hash(updatedClient.getPassword()));
        client.setEmail(updatedClient.getEmail());

        em.merge(client);
    }

    public Client find(String username) throws MyEntityNotFoundException{
        Client client = em.find(Client.class, username);
        if (client == null) {
            throw new MyEntityNotFoundException("Client with username '"+ username +"' not found");
        }
        return client;
    }

    public List<Client> findAll() {
        return em.createNamedQuery("getAllClients", Client.class).getResultList();
    }

    public void delete(String username) throws MyEntityNotFoundException{
        var user = find(username);
        em.remove(user);
    }

    public Client findWithOrders(String username) throws MyEntityNotFoundException {
        var client = find(username);
        Hibernate.initialize(client.getOrders());
        return client;
    }
}
