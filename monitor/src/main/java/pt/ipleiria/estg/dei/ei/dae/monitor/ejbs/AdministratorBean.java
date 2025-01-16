package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
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

    public void create(String username, String password, String name, String email) {
        var admin = new Administrator(username, hasher.hash(password), name, email);
        em.persist(admin);
    }

    public void update(String username, String password, String name, String email) {
        var admin = em.find(Administrator.class, username);

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
