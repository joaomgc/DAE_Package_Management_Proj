package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import java.util.LinkedList;

public class Package {

    private Long id;
    private String packageId;
    private String packageType;
    private LinkedList<Product> products;
    private LinkedList<Sensor> sensors;

    // Constructor
    public Package(Long id, String packageId, String packageType, LinkedList<Product> products, LinkedList<Sensor> sensors) {
        this.id = id;
        this.packageId = packageId;
        this.packageType = packageType;
        this.products = products;
        this.sensors = sensors;
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

    public LinkedList<Product> getProducts() {
        return new LinkedList<>(products);
    }

    public void setProducts(LinkedList<Product> products) {
        this.products = products;
    }

    public LinkedList<Sensor> getSensors() {
        return new LinkedList<>(sensors);
    }

    public void setSensors(LinkedList<Sensor> sensors) {
        this.sensors = sensors;
    }
}
