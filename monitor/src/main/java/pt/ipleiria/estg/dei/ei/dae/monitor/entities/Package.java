package pt.ipleiria.estg.dei.ei.dae.monitor.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPackages",
                query = "SELECT p FROM Package p ORDER BY p.id" // JPQL
        )
})
public class Package implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String packageId;
    @NotNull
    private String packageType;


    public Package() {
    }

    public Package(String packageId, String packageType) {

        this.packageId = packageId;
        this.packageType = packageType;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

}
