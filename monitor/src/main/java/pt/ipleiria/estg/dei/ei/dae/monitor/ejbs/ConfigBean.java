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
    @EJB
    private TipoSensorBean tipoSensorBean;

    @PostConstruct
    public void populateDB() {
        try {
            tipoSensorBean.create("temperatura");
            tipoSensorBean.create("pressao");
            tipoSensorBean.create("aceleracao");
            tipoSensorBean.create("posicionamento");
            tipoSensorBean.create("luminosidade");
            // Sensors
            sensorBean.create("TEMP-01", "temperatura", "Active");
            sensorBean.create("PRESS-01", "pressao", "Active");
            sensorBean.create("AC-01", "aceleracao", "Active");
            sensorBean.create("POS-01", "posicionamento", "Active");
            sensorBean.create("LUM-01", "luminosidade", "Active");
            sensorBean.create("TEMP-02", "temperatura", "Active");
            sensorBean.create("PRESS-02", "pressao", "Active");
            sensorBean.create("AC-02", "aceleracao", "Active");
            sensorBean.create("POS-02", "posicionamento", "Active");
            sensorBean.create("LUM-02", "luminosidade", "Active");

            // Sensor History

            sensorHistoryBean.create(sensorBean.find("TEMP-01"), LocalDateTime.parse("2024-12-11 20:31", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 20.0);
            sensorHistoryBean.create(sensorBean.find("TEMP-01"), LocalDateTime.parse("2024-12-12 20:31", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 21.0);
            sensorHistoryBean.create(sensorBean.find("PRESS-01"), LocalDateTime.parse("2025-01-03 20:31", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 1000.0);
            sensorHistoryBean.create(sensorBean.find("PRESS-01"), LocalDateTime.parse("2025-01-05 20:31", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 1500.0);
            sensorHistoryBean.create(sensorBean.find("AC-01"), LocalDateTime.parse("2024-12-11 20:31", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 10.0);
            sensorHistoryBean.create(sensorBean.find("POS-01"), LocalDateTime.parse("2024-12-11 20:31", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 10.0);

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
            packageBean.create(1L, "Ice Package");
            packageBean.create(2L, "Plastic Package");
            packageBean.create(3L, "Paper Package");
            packageBean.create(4L, "Wood Package");
    // VOLUMES
            volumeBean.create(1L, "Standard Volume", 0L, "LAPTOP-ASUS", 5);
            volumeBean.associateSensor(1L, "TEMP-01");
            volumeBean.create(2L, "Express Volume", 1L);
            volumeBean.associateSensor(2L, "PRESS-01");
            volumeBean.create(3L, "Bulk Volume", 2L);
            volumeBean.associateSensor(3L, "AC-01");
            volumeBean.create(4L, "Light Volume", 3L);
            volumeBean.associateSensor(4L, "POS-01");
            volumeBean.create(5L, "Heavy Volume", 4L);
            volumeBean.associateSensor(5L, "LUM-01");
            volumeBean.create(6L, "Fragile Volume", 0L);
            volumeBean.associateSensor(6L, "TEMP-02");
            volumeBean.create(7L, "Perishable Volume", 0L);
            volumeBean.associateSensor(7L, "PRESS-02");
            volumeBean.create(8L, "Valuable Volume", 1L, "JBL-2X6", 4);
            volumeBean.associateSensor(8L, "AC-02");
            volumeBean.create(9L, "Overnight Volume", 1L);
            volumeBean.associateSensor(9L, "POS-02");
            volumeBean.create(10L, "Two-Day Volume", 2L);
            volumeBean.associateSensor(10L, "LUM-02");

            adminBean.create("admin", "123", "Alberto", "admin@mail.pt");
            adminBean.create("admin2", "123", "Nuno", "admin2@mail.pt");
            clientBean.create("ricardo", "123", "Ricardo Fazeres", "riczao@mail.pt");
            clientBean.create("mario", "123", "Mario Hilario", "mario@mail.pt");
            clientBean.create("jose", "123", "Jose Carlos", "jose@mail.pt");
    // orders
            orderBean.create(123L, "ricardo", "pendente");
            orderBean.create(456L, "ricardo", "entregue");
            orderBean.create(789L, "mario", "pendente");
            orderBean.create(1234L, "mario", "entregue");
            orderBean.create(3456L, "jose", "pendente");
            orderBean.addVolume(123L, 10L);
            orderBean.addVolume(123L, 8L);
            orderBean.addVolume(456L, 1L);
            orderBean.addVolume(789L, 2L);
            orderBean.addVolume(1234L, 3L);
            orderBean.addVolume(3456L, 4L);
            orderBean.addVolume(3456L, 5L);
//
//    // add products to volumes
            volumeBean.addProduct(8L, "LAPTOP-ASUS", 5);
            volumeBean.addProduct(10L, "IPHONE-14", 3);  // Volume 10 -> Product 2 (3 units)
            volumeBean.addProduct(10L, "JBL-2X6", 7);
            volumeBean.addProduct(1L, "META-GLASSES", 1);

        } catch (MyEntityExistsException | MyEntityNotFoundException | MyConstraintViolationException e){
            logger.severe("Error populating the database: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error populating database", e);
            throw e; // Rethrow the exception to ensure the deployment fails
        }
    }

}