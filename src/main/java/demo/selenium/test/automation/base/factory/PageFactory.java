package demo.selenium.test.automation.base.factory;

import demo.selenium.test.automation.base.component.Page;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;

@Log4j2
public class PageFactory {
    private WebDriver driver;

    public PageFactory(WebDriver driver) {
        this.driver = driver;
    }

    public <T extends Page> T create(Class<T> classToProxy) {
        log.debug("Creating page object: {}", classToProxy.getSimpleName());
        return org.openqa.selenium.support.PageFactory.initElements(driver, classToProxy);
    }
}