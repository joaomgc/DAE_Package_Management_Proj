package pt.ipleiria.estg.dei.ei.dae.monitor.dtos;

import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Volume;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class VolumeDTO implements Serializable {
    private Long id;
    private String volumeName;
    private String packageId;
    private String sensorId;
    private List<ProductQuantityDTO> produtos;

    public VolumeDTO() {
    }

    public VolumeDTO(Long id, String volumeName, String packageId, String sensorId, List<ProductQuantityDTO> produtos) {
        this.id = id;
        this.volumeName = volumeName;
        this.packageId = packageId;
        this.sensorId = sensorId;
        this.produtos = produtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
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
        List<ProductQuantityDTO> produtosDTO = volume.getVolumeProducts().stream()
                .map(vp -> new ProductQuantityDTO(
                        vp.getProduct().getProductId(),
                        vp.getProduct().getProductName(),
                        vp.getQuantidade()
                ))
                .collect(Collectors.toList());
        return new VolumeDTO(
                volume.getId(),
                volume.getVolumeName(),
                volume.getPack().getPackageId(),
                volume.getSensor() != null ? volume.getSensor().getId() : null,
                produtosDTO
        );
    }

    public static List<VolumeDTO> from(List<Volume> volumes){
        return volumes.stream().map(VolumeDTO::from).toList();
    }
}