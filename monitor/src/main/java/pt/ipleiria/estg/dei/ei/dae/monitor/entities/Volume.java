package pt.ipleiria.estg.dei.ei.dae.monitor.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "volumes")
@NamedQueries({
        @NamedQuery(
                name = "getAllVolumes",
                query = "SELECT v FROM Volume v ORDER BY v.id" // JPQL
        )
})
public class Volume implements Serializable {
    @Id
    private Long id;

    @NotNull
    private String volumeName;

    @OneToOne
    @JoinColumn(name = "package_id")
    private Package pack;

    public Volume() {
    }

    public Volume(Long id, String volumeName, Package pack) {
        this.id = id;
        this.volumeName = volumeName;
        this.pack = pack;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }

    public Package getPack() {
        return pack;
    }

    public void setPack(Package pack) {
        this.pack = pack;
    }
}