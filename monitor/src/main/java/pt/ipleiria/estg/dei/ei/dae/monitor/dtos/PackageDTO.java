package pt.ipleiria.estg.dei.ei.dae.monitor.dtos;

import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Product;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class PackageDTO implements Serializable {
    private String packageId;
    private String packageType;

    public PackageDTO() {
    }

    public PackageDTO(String packageId, String packageType) {
        this.packageId = packageId;
        this.packageType = packageType;
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

    public static PackageDTO from(Package pck) {
        return new PackageDTO(
                pck.getPackageId(),
                pck.getPackageType()
        );
    }

    public static List<PackageDTO> from(List<Package> packages){
        return packages.stream().map(PackageDTO::from).collect(Collectors.toList());
    }
}