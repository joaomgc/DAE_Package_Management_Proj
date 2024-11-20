package pt.ipleiria.estg.dei.ei.dae.monitor.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "packages")
@NamedQueries({
        @NamedQuery(
                name = "getAllPackages",
                query = "SELECT p FROM Package p ORDER BY p.id" // JPQL
        )
})
public class Package implements Serializable {
    @Id
    private String packageId;
    @NotNull
    private String packageType;
    @OneToMany(fetch = FetchType.EAGER) @NotNull
    private List<Product> products;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Volume> volumes;

    public Package() {
        this.products = new LinkedList<>();
        this.volumes = new LinkedList<>();
    }

    public Package(String packageId, String packageType) {

        this.packageId = packageId;
        this.packageType = packageType;
        this.products = new LinkedList<>();
        this.volumes = new LinkedList<>();
    }

    public List<Product> getProducts() {
        return new LinkedList<>(products);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Volume> getVolumes() {
        return new LinkedList<>(volumes);
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
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
