package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private SensorHistoryBean sensorHistoryBean;
    @EJB
    private UserBean userBean;
    @EJB
    private OrderBean orderBean;

    @PostConstruct
    public void populateDB() {
        try {

            // Sensors

            sensorBean.create("1", "temperatura", "Inactive");
            sensorBean.create("2", "pressao", "Inactive");
            sensorBean.create("3", "aceleracao", "Inactive");
            sensorBean.create("4", "posicionamento", "Inactive");
            sensorBean.create("5", "luminosidade", "Active");

            // Sensor History

            sensorHistoryBean.create(sensorBean.find("1"), LocalDateTime.parse("2024-12-11 20:31", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 20.0);
            sensorHistoryBean.create(sensorBean.find("1"), LocalDateTime.parse("2024-12-12 20:31", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 21.0);
            sensorHistoryBean.create(sensorBean.find("2"), LocalDateTime.parse("2025-01-03 20:31", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 1000.0);
            sensorHistoryBean.create(sensorBean.find("2"), LocalDateTime.parse("2025-01-05 20:31", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 1500.0);
            sensorHistoryBean.create(sensorBean.find("3"), LocalDateTime.parse("2024-12-11 20:31", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 10.0);
            sensorHistoryBean.create(sensorBean.find("4"), LocalDateTime.parse("2024-12-11 20:31", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 10.0);

            // Products
            productBean.create("LAPTOP-ASUS", "Laptop", "Electronics");
            productBean.create("IPHONE-14", "Smartphone", "Electronics");
            productBean.create("JBL-2X6", "Headphones", "Audio");
            productBean.create("SAMSUNG-X7", "Tablet", "Electronics");
            productBean.create("NIKE-DC7", "Running Shoes", "Footwear");
            productBean.create("EASTPAK-BL4CK", "Backpack", "Accessories");
            productBean.create("LEVIS-466", "Jeans", "Apparel");
            productBean.create("NORTH-FACE-555", "Jacket", "Apparel");
            productBean.create("JBL-123", "Bluetooth Speaker", "Audio");
            productBean.create("SONY-100MP", "Camera", "Cameras");
            productBean.create("GO-PRO-456", "Action Camera", "Cameras");
            productBean.create("ASUS-STAND-789", "Laptop Stand", "Accessories");
            productBean.create("HP-MOUSE", "Wireless Mouse", "Accessories");
            productBean.create("SAMSUNG-WATCH7", "Smartwatch", "Wearables");
            productBean.create("RUN-LIKE-HELL", "Fitness Tracker", "Wearables");
            productBean.create("META-GLASSES", "Smart Glasses", "Wearables");
            productBean.create("SMART-BULB", "Smart Light Bulb", "Smart Home");
            productBean.create("SMART-PLUG", "Smart Plug", "Smart Home");
            productBean.create("BREATHE-AIR", "Air Purifier", "Home Appliances");
            productBean.create("DYSON-500X", "Vacuum Cleaner", "Home Appliances");

// Volumes
            packageBean.create("0", "No Package");

            volumeBean.create(1L, "Standard Volume", packageBean.find("0"), "LAPTOP-ASUS", 5);
            volumeBean.create(2L, "Express Volume", null);
            volumeBean.create(3L, "Bulk Volume", null);
            volumeBean.create(4L, "Light Volume", null);
            volumeBean.create(5L, "Heavy Volume", null);
            volumeBean.create(6L, "Fragile Volume", null);
            volumeBean.create(7L, "Perishable Volume", null);
            volumeBean.create(8L, "Valuable Volume", packageBean.find("0"), "JBL-2X6", 4);
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
            userBean.create("mario", "123", "mario@mail.pt");

    // orders
            orderBean.create(123L, "ricardo", "pendente");
            orderBean.create(456L, "ricardo", "entregue");
            orderBean.addVolume(123L, 10L);
            orderBean.addVolume(123L, 8L);
            orderBean.addVolume(456L, 1L);
//
//    // add products to volumes
            volumeBean.addProduct(8L, "LAPTOP-ASUS", 5);
            volumeBean.addProduct(10L, "IPHONE-14", 3);  // Volume 10 -> Product 2 (3 units)
            volumeBean.addProduct(10L, "JBL-2X6", 7);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error populating database", e);
            throw e; // Rethrow the exception to ensure the deployment fails
        }
    }
}