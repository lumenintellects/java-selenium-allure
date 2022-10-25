package demo.selenium.base;

import io.qameta.allure.Allure;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import demo.selenium.test.automation.base.Application;
import demo.selenium.test.automation.base.factory.DriverFactoryProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseTest {
    private WebDriver driver;
    protected Application application;
    protected SoftAssertions softAssertions = new SoftAssertions();

    @BeforeEach
    public void setUpBeforeEach() {
        this.driver = new DriverFactoryProvider().getDriverFactory().getDriver();
        application = new Application(driver);
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    @AfterEach
    public void cleanUpAfterEach() throws FileNotFoundException {
        Allure.getLifecycle().addAttachment(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy_hh:mm:ss")), "image/png", "png", makeScreenShot());
        FileInputStream LogFileStream = new FileInputStream("log.log");
        Allure.addAttachment("Log file: ", LogFileStream);
        application.quit();
    }

    private byte[] makeScreenShot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
