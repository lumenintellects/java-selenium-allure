package demo.selenium.test.automation.pages;

import demo.selenium.test.automation.base.component.Page;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
public class DropdownPage extends Page {

    @FindBy(css = "select[id='dropdown']")
    public WebElement Dropdown;

    @FindBy(css = "option[value='1']")
    public WebElement optionOne;

    @FindBy(css = "option[value='1'][selected='selected']")
    public WebElement optionOneIsSelected;

    public DropdownPage(WebDriver driver) { super(driver); }

    @Step("Verify dropdown is displayed")
    public boolean verifyDropdownIsDisplayed() {
        log.info("Verify dropdown is displayed");
        Allure.getLifecycle().addAttachment(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy_hh:mm:ss")), "image/png", "png", makeScreenShot());
        return Dropdown.isDisplayed();
    }

    @Step("Click on Dropdown")
    public DropdownPage clickDropdown() {
        log.info("Clicking on Dropdown...");
        Dropdown.click();
        return this;
    }

    @Step("Click on option one")
    public DropdownPage clickOptionOne() {
        log.info("Clicking on option one...");
        optionOne.click();
        return this;
    }

    @Step("Validate that option one is selected")
    public boolean validateOptionOneIsSelected() {
        log.info("Validating option one is selected");
        Allure.getLifecycle().addAttachment(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy_hh:mm:ss")), "image/png", "png", makeScreenShot());
        return optionOneIsSelected.isDisplayed();
    }

}