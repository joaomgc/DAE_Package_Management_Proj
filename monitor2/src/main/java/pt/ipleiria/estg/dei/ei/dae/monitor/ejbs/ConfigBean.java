package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Startup
@Singleton
public class ConfigBean {

    @EJB
    private PackageBean packageBean;

    @PostConstruct
    public void populateDB() {
        packageBean.create("1", "Package 1");
    }
}