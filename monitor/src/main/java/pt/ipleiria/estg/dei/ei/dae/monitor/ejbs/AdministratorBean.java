package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Administrator;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.monitor.security.Hasher;

import java.util.List;

@Stateless
public class AdministratorBean {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private Hasher hasher;

    public boolean exists(String username) {
        Query query = em.createQuery(
                "SELECT COUNT(a.username) FROM Administrator a WHERE a.username = :username",
                Long.class
        );
        query.setParameter("username", username);
        return (Long)query.getSingleResult() > 0L;
    }

    public void create(String username, String password, String name, String email) {

        if(exists(username)) {
            throw new EntityExistsException("Administrator with username '" + username + "' already exists");
        }

        Administrator admin = null;

        try{
            admin = new Administrator(username, hasher.hash(password), name, email);
            em.persist(admin);
            em.flush();
        } catch (PersistenceException e) {
            throw new EntityExistsException("Administrator with username '" + username + "' already exists");
        }
        System.out.println("Created administrator with username: " + username);
    }

    public void update(String username, String password, String name, String email) {
        var admin = em.find(Administrator.class, username);

        if(admin == null) {
            System.err.println("ERROR_ADMIN_NOT_FOUND: " + username);
            return;
        }

        em.lock(admin, LockModeType.OPTIMISTIC);
        admin.setPassword(hasher.hash(password));
        admin.setName(name);
        admin.setEmail(email);
    }

    public Administrator find(String username) {
        Administrator admin = em.find(Administrator.class, username);
        if (admin == null) {
            throw new EntityNotFoundException("Admin not found");
        }
        return admin;
    }

    public List<Administrator> findAll() {
        return em.createNamedQuery("getAllAdmins", Administrator.class).getResultList();
    }
}
