package pt.ipleiria.estg.dei.ei.dae.monitor.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllAdmins",
                query = "SELECT a FROM Administrator a ORDER BY a.name" // JPQL
        )
})
public class Administrator extends User{

    public Administrator() {
    }

    public Administrator(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
}
