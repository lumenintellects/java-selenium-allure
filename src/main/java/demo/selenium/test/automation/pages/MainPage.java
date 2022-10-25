package demo.selenium.test.automation.pages;

import demo.selenium.test.automation.base.component.Page;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
public class MainPage extends Page {

    @FindBy(css = "h1[class='heading']")
    public WebElement headerText;

    @FindBy(css = "a[href='/dropdown']")
    public WebElement buttonDropdown;

    public MainPage(WebDriver driver) { super(driver); }

    @Step("Wait for page header to appear")
    public MainPage waitForHeader() {
        log.info("Waiting for header to appear...");
        webDriverWait.until(webDriver -> headerText.isDisplayed());
        return this;
    }

    @Step("Validate text from header")
    public boolean readTextFromHeaderAndValidate() {
        log.info("Reading text on the header");
        String actualText = headerText.getText();
        log.info(String.format("Actual text: %s", actualText));
        attachInfoToAllure("Actual text on the banner: ", actualText);
        String expectedText = "Welcome to the-internet";
        attachInfoToAllure("Expected text: ", expectedText);
        log.info("Validating text from header");
        Allure.getLifecycle().addAttachment(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy_hh:mm:ss")), "image/png", "png", makeScreenShot());
        return expectedText.equals(actualText);
    }

    @Step("Click 'Dropdown' button and navidate to 'Dropdown' page")
    public DropdownPage goToDropdownPage() {
        webDriverWait.until(webDriver -> ExpectedConditions.elementToBeClickable(buttonDropdown));
        log.info("Click 'Dropdown' button");
        buttonDropdown.click();
        return pageFactory.create(DropdownPage.class);
    }

    @Step("Verify dropdown button is displayed")
    public boolean verifyDropdownIsDisplayed() {
        log.info("Verify dropdown button is displayed");
        Allure.getLifecycle().addAttachment(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy_hh:mm:ss")), "image/png", "png", makeScreenShot());
        return buttonDropdown.isDisplayed();
    }

}