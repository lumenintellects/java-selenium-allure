package demo.selenium.test.automation.base.factory;

import io.github.bonigarcia.wdm.DriverManagerType;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.Stream;

@Log4j2
public class RemoteDriverFactory implements IDriverFactory {

    @Override
    public WebDriver getDriver() {
        DriverManagerType driverType;
        WebDriver driver;
        URL gridHubUrl = null;
        DesiredCapabilities desiredCapabilities;
        try {
            driverType = DriverManagerType.valueOf(configuration.browserName().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Wrong browserName, supported browsers:\n" +
                    Arrays.toString(
                            Stream.of(DriverManagerType.values())
                                    .map(DriverManagerType::name)
                                    .toArray(String[]::new)));
        }

        try {
            gridHubUrl = new URL(configuration.gridHubUrl());
        } catch (MalformedURLException e) {
            log.error("Invalid gridHubUrl", e);
        }
        switch (driverType) {
            case CHROME:
                desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, BrowserOptionsFactory.getOptions());
                driver = new RemoteWebDriver(gridHubUrl, desiredCapabilities);
                break;
            case FIREFOX:
                desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, BrowserOptionsFactory.getOptions());
                driver = new RemoteWebDriver(gridHubUrl, desiredCapabilities);
                break;
            default:
                log.warn("Browser not provided, using default one");
                desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, BrowserOptionsFactory.getOptions());
                driver = new RemoteWebDriver(gridHubUrl, desiredCapabilities);
                break;
        }
        return driver;
    }
}