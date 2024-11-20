package pt.ipleiria.estg.dei.ei.dae.monitor.dtos;

import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Volume;

import java.io.Serializable;
import java.util.List;

public class VolumeDTO implements Serializable {
    private Long id;
    private String volumeName;
    private String packageId;

    public VolumeDTO() {
    }

    public VolumeDTO(Long id, String volumeName, String packageId) {
        this.id = id;
        this.volumeName = volumeName;
        this.packageId = packageId;
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

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public static VolumeDTO from(Volume volume) {
        return new VolumeDTO(
                volume.getId(),
                volume.getVolumeName(),
                volume.getPack().getPackageId()
        );
    }

    public static List<VolumeDTO> from(List<Volume> volumes){
        return volumes.stream().map(VolumeDTO::from).toList();
    }
}