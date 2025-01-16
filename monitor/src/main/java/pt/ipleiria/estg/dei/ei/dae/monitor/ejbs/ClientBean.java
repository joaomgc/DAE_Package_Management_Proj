package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.User;
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

    public Client create(String username, String password, String name, String email) {
        if (exists(username)) {
            throw new EntityExistsException("Client already exists");
        }

        Client client = null;

        try {
            client = new Client(username, hasher.hash(password), name, email);
            System.out.println("Client created: " + username);
            em.persist(client);
            em.flush();
        }catch(EntityExistsException e){
            System.err.println("ERROR_USER_ALREADY_EXISTS: " + username);
        }

        return client;
    }

    public void update(Client updatedClient) {
        Client client = find(updatedClient.getUsername());

        em.lock(client, LockModeType.OPTIMISTIC);

        client.setName(updatedClient.getName());
        client.setPassword(hasher.hash(updatedClient.getPassword()));
        client.setEmail(updatedClient.getEmail());

        em.merge(client);
    }

    public Client find(String username) {
        Client client = em.find(Client.class, username);
        if (client == null) {
            throw new EntityNotFoundException("Client not found");
        }
        return client;
    }

    public List<Client> findAll() {
        return em.createNamedQuery("getAllClients", Client.class).getResultList();
    }

    public void delete(String username) {
        var user = em.find(User.class, username);
        if (user != null) {
            em.remove(user);
        }
    }
}
