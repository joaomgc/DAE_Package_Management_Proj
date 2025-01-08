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

    @PostConstruct
    public void populateDB() {
        try {
            productBean.create(1L, "Samsung TV", "Technology");
            productBean.create(2L, "Apple iPhone", "Technology");
            volumeBean.create(1L, "Volume 1", null);
            packageBean.create("1", "Package 1");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error populating database", e);
            throw e; // Rethrow the exception to ensure the deployment fails
        }
    }
}