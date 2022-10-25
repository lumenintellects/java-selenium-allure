package demo.selenium.test.automation.base.factory;

import demo.selenium.test.automation.configuration.Configuration;
import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j2;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

import java.util.Arrays;
import java.util.stream.Stream;

@Log4j2
public class BrowserOptionsFactory {

    public static AbstractDriverOptions getOptions() {
        Configuration configuration = ConfigFactory.create(Configuration.class);
        DriverManagerType driverType;
        AbstractDriverOptions options;
        try {
            driverType = DriverManagerType.valueOf(configuration.browserName().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Wrong browserName, supported browsers:\n" +
                    Arrays.toString(
                            Stream.of(DriverManagerType.values())
                                    .map(DriverManagerType::name)
                                    .toArray(String[]::new)));
        }
        switch (driverType) {
            case CHROME:
                options = new ChromeOptions();
                ((ChromeOptions) options).addArguments("start-maximized");
                break;
            case FIREFOX:
                options = new FirefoxOptions();
                ((FirefoxOptions) options).setHeadless(false);
                break;
            case EDGE:
                options = new EdgeOptions();
                ((EdgeOptions) options).setHeadless(false);
                break;
            default:
                log.warn("Browser not provided, using default one");
                WebDriverManager.chromedriver().setup();
                options = new ChromeOptions();
                break;
        }
        return options;
    }
}