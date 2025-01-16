package pt.ipleiria.estg.dei.ei.dae.monitor.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "packages")
@NamedQueries({
        @NamedQuery(
                name = "getAllPackages",
                query = "SELECT p FROM Package p ORDER BY p.id"
        )
})
public class Package implements Serializable {
    @Id
    private Long packageId;
    @NotNull
    private String packageType;

    @OneToMany(mappedBy = "pack", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Volume> volume;

    public Package() {
    }

    public Package(Long packageId, String packageType) {
        this.packageId = packageId;
        this.packageType = packageType;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public List<Volume> getVolumes() {
        return volume;
    }

    public void addVolume(Volume volume) {
        this.volume.add(volume);
    }
}