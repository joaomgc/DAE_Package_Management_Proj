package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.*;
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


    public boolean exists(String username) {
        Query query = em.createQuery(
                "SELECT COUNT(u.username) FROM User u WHERE u.username = :username",
                Long.class
        );
        query.setParameter("username", username);
        return (Long)query.getSingleResult() > 0L;
    }

    public User create(String username, String password, String email) {

        if (exists(username)) {
            throw new EntityExistsException("User already exists");
        }

        User user = null;

        try {
            user = new User(username, hasher.hash(password), email);
            System.out.println("User created: " + username);
            em.persist(user);
            em.flush();
        }catch(EntityExistsException e){
            System.err.println("ERROR_USER_ALREADY_EXISTS: " + username);

        }

        return user;
    }

    public void update(User user) {
        User existingUser = em.find(User.class, user.getUsername());

        if (existingUser == null) {
            System.err.println("ERROR_USER_NOT_FOUND: " + user.getUsername());
            return;
        }

        em.lock(existingUser, LockModeType.OPTIMISTIC);

        if (!existingUser.getPassword().equals(user.getPassword())) {
            existingUser.setPassword(hasher.hash(user.getPassword()));
        }

        existingUser.setEmail(user.getEmail());
        em.merge(existingUser);
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
        var user = findOrFail(username);
        return user != null && user.getPassword().equals(hasher.hash(password));
    }
}