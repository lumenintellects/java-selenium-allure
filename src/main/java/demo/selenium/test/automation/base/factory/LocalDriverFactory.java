package demo.selenium.test.automation.base.factory;

import demo.selenium.test.automation.base.factory.loader.ChromeDriverLoader;
import demo.selenium.test.automation.base.factory.loader.EdgeDriverLoader;
import demo.selenium.test.automation.base.factory.loader.FirefoxDriverLoader;
import io.github.bonigarcia.wdm.DriverManagerType;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Arrays;
import java.util.stream.Stream;

@Log4j2
public class LocalDriverFactory implements IDriverFactory {

    @Override
    public WebDriver getDriver() {
        DriverManagerType driverType;
        WebDriver driver;
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
                ChromeDriverLoader.load();
                driver = new ChromeDriver((ChromeOptions) BrowserOptionsFactory.getOptions());
                break;
            case FIREFOX:
                FirefoxDriverLoader.load();
                driver = new FirefoxDriver((FirefoxOptions) BrowserOptionsFactory.getOptions());
                break;
            case EDGE:
                EdgeDriverLoader.load();
                driver = new EdgeDriver((EdgeOptions) BrowserOptionsFactory.getOptions());
                break;
            default:
                log.warn("Browser not provided, using default one");
                ChromeDriverLoader.load();
                driver = new ChromeDriver((ChromeOptions) BrowserOptionsFactory.getOptions());
                break;
        }
        return driver;
    }
}