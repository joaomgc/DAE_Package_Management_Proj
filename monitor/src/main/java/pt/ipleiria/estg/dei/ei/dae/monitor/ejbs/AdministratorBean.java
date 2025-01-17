package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Administrator;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityNotFoundException;
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

    public void create(String username, String password, String name, String email)
            throws MyEntityExistsException, MyConstraintViolationException {

        if(exists(username)) {
            throw new MyEntityExistsException("Administrator with username '" + username + "' already exists");
        }

        Administrator admin = null;

        try{
            admin = new Administrator(username, hasher.hash(password), name, email);
            admin.setRole("A");
            em.persist(admin);
            em.flush();
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
        System.out.println("Created administrator with username: " + username);
    }

    public void update(String username, String password, String name, String email) throws MyEntityNotFoundException {
        var admin = find(username);

        em.lock(admin, LockModeType.OPTIMISTIC);
        admin.setPassword(hasher.hash(password));
        admin.setName(name);
        admin.setEmail(email);
    }

    public Administrator find(String username) throws MyEntityNotFoundException {
        Administrator admin = em.find(Administrator.class, username);
        if (admin == null) {
            throw new MyEntityNotFoundException("Admin with username '"+ username +"' not found");
        }
        return admin;
    }

    public List<Administrator> findAll() {
        return em.createNamedQuery("getAllAdmins", Administrator.class).getResultList();
    }
}
