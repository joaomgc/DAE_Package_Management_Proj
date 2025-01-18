package pt.ipleiria.estg.dei.ei.dae.monitor.dtos;

import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Volume;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class VolumeDTO implements Serializable {
    private Long id;
    private String volumeName;
    private Long packageId;
    private String sensorId;
    private double ultimoValorSensor;
    private List<ProductNameQuantityDTO> produtos;

    public VolumeDTO() {
    }

    public VolumeDTO(Long id, String volumeName, Long packageId, String sensorId, List<ProductNameQuantityDTO> produtos, double valor) {
        this.id = id;
        this.volumeName = volumeName;
        this.packageId = packageId;
        this.sensorId = sensorId;
        this.produtos = produtos;
        this.ultimoValorSensor = valor;
        // this.produtos = new ArrayList<>();
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

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public List<ProductNameQuantityDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProductNameQuantityDTO> produtos) {
        this.produtos = produtos;
    }

    public double getValor() {
        return ultimoValorSensor;
    }

    public void setValor(double valor) {
        this.ultimoValorSensor = valor;
    }

    public static VolumeDTO from(Volume volume) {
        List<ProductNameQuantityDTO> produtosDTO = volume.getVolumeProducts().stream()
                .map(vp -> new ProductNameQuantityDTO(
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
                produtosDTO,
                volume.getSensor() == null ? 0.0 : volume.getSensor().getValor()
        );
    }

    public static List<VolumeDTO> from(List<Volume> volumes){
        return volumes.stream().map(VolumeDTO::from).toList();
    }
}