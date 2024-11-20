package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.User;
import pt.ipleiria.estg.dei.ei.dae.monitor.security.Hasher;
//import pt.ipleiria.estg.dei.ei.dae.monitor.security.Hasher;

import java.util.List;

import static java.nio.file.Files.find;


@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private Hasher hasher;

    public void create(String username, String password, String email) {

        var user = new User(username, hasher.hash(password), email);
        em.persist(user);
    }

    public void update(User user) {
        em.merge(user);
    }

    public void delete(String username) {
        var user = em.find(User.class, username);
        if (user != null) {
            em.remove(user);
        }
    }

    public List<User> findAll() {
        return em.createNamedQuery("getAllUsers", User.class).getResultList();
    }


    public User findOrFail(String username) {
        var user = em.getReference(User.class, username);
        Hibernate.initialize(user);
        return user;
    }

    public boolean canLogin(String username, String password) {
        var user = em.find(User.class, username);
        return user != null && user.getPassword().equals(password);
    }
}