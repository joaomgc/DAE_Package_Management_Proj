package pt.ipleiria.estg.dei.ei.dae.monitor.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(
                name = "getAllUsers",
                query = "SELECT u FROM User u ORDER BY u.id" // JPQL
        )
})
public class User {
    @Id
    private String username;
    private String password;
    private String email;

    public User() {
    }
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}