package demo.selenium.test.automation.base;

import demo.selenium.test.automation.base.component.Page;
import demo.selenium.test.automation.configuration.Configuration;
import demo.selenium.test.automation.pages.MainPage;
import lombok.extern.log4j.Log4j2;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;

@Log4j2
public class Application extends Page {
    private static Configuration configuration = ConfigFactory.create(Configuration.class);

    public Application(WebDriver webDriver) {
        super(webDriver);
    }

    public MainPage open(String url) {
        log.info("Opening application at: {}", url);

        driver.get(url);
        return pageFactory.create(MainPage.class);
    }

    public void quit() {
        log.info("Killing application");
        driver.quit();
    }

    public void close() {
        log.info("Closing application");
        driver.close();
    }
}