package demo.selenium.test.automation.base.factory;

import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import demo.selenium.test.automation.configuration.Configuration;

public interface IDriverFactory {

    Configuration configuration = ConfigFactory.create(Configuration.class);

    WebDriver getDriver();
}