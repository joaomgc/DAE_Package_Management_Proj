package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {

    private static final Logger logger = Logger.getLogger(ConfigBean.class.getName());

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
    @EJB
    private ClientBean clientBean;
    @EJB
    private AdministratorBean adminBean;

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
// Packages
            packageBean.create(0L, "No Package");
            packageBean.create(1L, "Basic Package");
            packageBean.create(2L, "Standard Package");
            packageBean.create(3L, "Premium Package");
            packageBean.create(4L, "Family Package");
            packageBean.create(5L, "Student Package");
            packageBean.create(6L, "Business Package");
            packageBean.create(7L, "Enterprise Package");
            packageBean.create(8L, "Travel Package");
            packageBean.create(9L, "Gift Package");
            packageBean.create(10L, "Subscription Package");
            packageBean.create(11L, "Starter Package");
            packageBean.create(12L, "Annual Package");
            packageBean.create(13L, "Monthly Package");
            packageBean.create(14L, "Weekly Package");
            packageBean.create(15L, "Daily Package");
            packageBean.create(16L, "Holiday Package");
            packageBean.create(17L, "Corporate Package");
            packageBean.create(18L, "Event Package");
            packageBean.create(19L, "Personal Package");
            packageBean.create(20L, "Exclusive Package");
    // VOLUMES
            volumeBean.create(1L, "Standard Volume", 0L, "LAPTOP-ASUS", 5);
            volumeBean.create(2L, "Express Volume", 0L);
            volumeBean.create(3L, "Bulk Volume", 0L);
            volumeBean.create(4L, "Light Volume", 0L);
            volumeBean.create(5L, "Heavy Volume", 0L);
            volumeBean.create(6L, "Fragile Volume", 0L);
            volumeBean.create(7L, "Perishable Volume", 0L);
            volumeBean.create(8L, "Valuable Volume", 0L, "JBL-2X6", 4);
            volumeBean.create(9L, "Overnight Volume", 0L);
            volumeBean.create(10L, "Two-Day Volume", 0L);
            volumeBean.create(11L, "Economy Volume", 0L);
            volumeBean.create(12L, "Same-Day Volume", 0L);
            volumeBean.create(13L, "Standard Shipping Volume", 0L);
            volumeBean.create(14L, "Expedited Shipping Volume", 0L);
            volumeBean.create(15L, "International Volume", 0L);
            volumeBean.create(16L, "Domestic Volume", 0L);
            volumeBean.create(17L, "Return Volume", 0L);
            volumeBean.create(18L, "Replacement Volume", 0L);
            volumeBean.create(19L, "Seasonal Volume", 0L);
            volumeBean.create(20L, "Promotional Volume", 0L);

            adminBean.create("admin", "123", "Alberto", "admin@mail.pt");
            clientBean.create("ricardo", "123", "Ricardo Fazeres", "riczao@mail.pt");
            clientBean.create("mario", "123", "Mario Hilario", "mario@mail.pt");
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

        } catch (MyEntityExistsException | MyEntityNotFoundException | MyConstraintViolationException e){
            logger.severe("Error populating the database: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error populating database", e);
            throw e; // Rethrow the exception to ensure the deployment fails
        }
    }
}