package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.User;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.monitor.security.Hasher;
//import pt.ipleiria.estg.dei.ei.dae.monitor.security.Hasher;

import java.util.List;


@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private Hasher hasher;

    public void update(User user) throws MyEntityNotFoundException {
        User existingUser = find(user.getUsername());

        em.lock(existingUser, LockModeType.OPTIMISTIC);

        if (!existingUser.getPassword().equals(user.getPassword())) {
            existingUser.setPassword(hasher.hash(user.getPassword()));
        }

        existingUser.setName(user.getName());
        em.merge(existingUser);
    }


    public void delete(String username) {
        var user = em.find(User.class, username);
        if (user != null) {
            em.remove(user);
        }
    }

    /*public List<User> findAll() {
        return em.createNamedQuery("getAllUsers", User.class).getResultList();
    }*/
    public User find(String username) throws MyEntityNotFoundException {
        User user = em.find(User.class, username);
        if (user == null) {
            throw new MyEntityNotFoundException("User with username '"+username+"' not found");
        }
        return user;
    }


    public User findOrFail(String username) {
        var user = em.getReference(User.class, username);
        System.out.println("Getting user data:" + user.getUsername() + user.getName() + user.getEmail() + user.getRole());
        Hibernate.initialize(user);
        return user;
    }

    public boolean canLogin(String username, String password) {
        var user = findOrFail(username);
        return user != null && user.getPassword().equals(hasher.hash(password));
    }
}