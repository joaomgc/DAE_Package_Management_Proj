package pt.ipleiria.estg.dei.ei.dae.monitor.dtos;

import jakarta.persistence.Id;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.Package;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PackageDTO implements Serializable {
    @Id
    private String packageId;
    private String packageType;
    private List<ProductDTO> products;

    public PackageDTO() {
    }

    public PackageDTO(String packageId, String packageType) {
        this.packageId = packageId;
        this.packageType = packageType;
        this.products = new ArrayList<>();
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
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
        //List<ProductDTO> productsDTOs = pck.getProducts().stream().map(ProductDTO::from).toList();
        return new PackageDTO(
                pck.getPackageId(),
                pck.getPackageType()

        );
    }

    public static List<PackageDTO> from(List<Package> packages){
        return packages.stream().map(PackageDTO::from).collect(Collectors.toList());
    }
}
