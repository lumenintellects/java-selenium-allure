package demo.selenium.test.automation.base.factory;

import demo.selenium.test.automation.configuration.Configuration;
import lombok.extern.log4j.Log4j2;
import org.aeonbits.owner.ConfigFactory;

@Log4j2
public class DriverFactoryProvider {
    private static Configuration configuration = ConfigFactory.create(Configuration.class);

    public IDriverFactory getDriverFactory() {
        String driverType = configuration.driverType();
        IDriverFactory driverFactory;

        switch (driverType) {
            case "LOCAL":
                driverFactory = new LocalDriverFactory();
                break;
            case "REMOTE":
                driverFactory = new RemoteDriverFactory();
                break;
            default:
                throw new IllegalStateException("Wrong driverType, supported types: [LOCAL, REMOTE]");
        }
        return driverFactory;
    }
}