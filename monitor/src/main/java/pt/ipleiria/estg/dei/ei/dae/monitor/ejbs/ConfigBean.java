package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {

    private static final Logger LOGGER = Logger.getLogger(ConfigBean.class.getName());

    @EJB
    private PackageBean packageBean;
    @EJB
    private ProductBean productBean;
    @EJB
    private VolumeBean volumeBean;
    @EJB
    private SensorBean sensorBean;
    @EJB
    private UserBean userBean;
    @EJB
    private OrderBean orderBean;

    @PostConstruct
    public void populateDB() {
        try {

            // Sensors

            sensorBean.create("1", "temperatura");
            sensorBean.create("2", "pressao");
            sensorBean.create("3", "aceleracao");
            sensorBean.create("4", "posicionamento");

            // Products
            productBean.create(1L, "Laptop", "Electronics");
            productBean.create(2L, "Smartphone", "Electronics");
            productBean.create(3L, "Headphones", "Audio");
            productBean.create(4L, "Tablet", "Electronics");
            productBean.create(5L, "Running Shoes", "Footwear");
            productBean.create(6L, "Backpack", "Accessories");
            productBean.create(7L, "Jeans", "Apparel");
            productBean.create(8L, "Jacket", "Apparel");
            productBean.create(9L, "Bluetooth Speaker", "Audio");
            productBean.create(10L, "Camera", "Cameras");
            productBean.create(11L, "Action Camera", "Cameras");
            productBean.create(12L, "Laptop Stand", "Accessories");
            productBean.create(13L, "Wireless Mouse", "Accessories");
            productBean.create(14L, "Smartwatch", "Wearables");
            productBean.create(15L, "Fitness Tracker", "Wearables");
            productBean.create(16L, "Smart Glasses", "Wearables");
            productBean.create(17L, "Smart Light Bulb", "Smart Home");
            productBean.create(18L, "Smart Plug", "Smart Home");
            productBean.create(19L, "Air Purifier", "Home Appliances");
            productBean.create(20L, "Vacuum Cleaner", "Home Appliances");

// Volumes
            packageBean.create("0", "No Package");

            volumeBean.create(1L, "Standard Volume", packageBean.find("0"), 1L, 5);
            volumeBean.create(2L, "Express Volume", null);
            volumeBean.create(3L, "Bulk Volume", null);
            volumeBean.create(4L, "Light Volume", null);
            volumeBean.create(5L, "Heavy Volume", null);
            volumeBean.create(6L, "Fragile Volume", null);
            volumeBean.create(7L, "Perishable Volume", null);
            volumeBean.create(8L, "Valuable Volume", packageBean.find("0"), 3L, 4);
            volumeBean.create(9L, "Overnight Volume", null);
            volumeBean.create(10L, "Two-Day Volume", packageBean.find("0"));
            volumeBean.create(11L, "Economy Volume", null);
            volumeBean.create(12L, "Same-Day Volume", null);
            volumeBean.create(13L, "Standard Shipping Volume", null);
            volumeBean.create(14L, "Expedited Shipping Volume", null);
            volumeBean.create(15L, "International Volume", null);
            volumeBean.create(16L, "Domestic Volume", null);
            volumeBean.create(17L, "Return Volume", null);
            volumeBean.create(18L, "Replacement Volume", null);
            volumeBean.create(19L, "Seasonal Volume", null);
            volumeBean.create(20L, "Promotional Volume", null);

// Packages
            packageBean.create("1", "Basic Package");
            packageBean.create("2", "Standard Package");
            packageBean.create("3", "Premium Package");
            packageBean.create("4", "Family Package");
            packageBean.create("5", "Student Package");
            packageBean.create("6", "Business Package");
            packageBean.create("7", "Enterprise Package");
            packageBean.create("8", "Travel Package");
            packageBean.create("9", "Gift Package");
            packageBean.create("10", "Subscription Package");
            packageBean.create("11", "Starter Package");
            packageBean.create("12", "Annual Package");
            packageBean.create("13", "Monthly Package");
            packageBean.create("14", "Weekly Package");
            packageBean.create("15", "Daily Package");
            packageBean.create("16", "Holiday Package");
            packageBean.create("17", "Corporate Package");
            packageBean.create("18", "Event Package");
            packageBean.create("19", "Personal Package");
            packageBean.create("20", "Exclusive Package");


            userBean.create("admin", "123", "admin@mail.pt");
            userBean.create("ricardo", "123", "riczao@mail.pt");

    // orders
            orderBean.create(123L, "ricardo", "pendente");
            orderBean.create(456L, "ricardo", "entregue");
            orderBean.addVolume(123L, 10L);
            orderBean.addVolume(123L, 8L);
//
//    // add products to volumes
            volumeBean.addProduct(8L, 1L, 5);
            volumeBean.addProduct(10L, 2L, 3);  // Volume 10 -> Product 2 (3 units)
            volumeBean.addProduct(10L, 3L, 7);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error populating database", e);
            throw e; // Rethrow the exception to ensure the deployment fails
        }
    }
}