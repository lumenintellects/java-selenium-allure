package demo.selenium.test.automation.configuration;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:configuration.properties")
public interface Configuration extends Config {

    String gridHubUrl();

    String browserName();

    String driverType();
}
