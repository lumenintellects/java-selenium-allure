package demo.selenium.test.automation.base.component;

import demo.selenium.test.automation.base.factory.PageFactory;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log4j2
public abstract class Page {

    @Getter
    protected WebDriver driver;
    @Getter
    protected WebDriverWait webDriverWait;
    protected PageFactory pageFactory;

    public Page(WebDriver driver) {
        this.driver = driver;
        this.webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        pageFactory = new PageFactory(driver);
    }

    public List<Element> findElements(final Locator locator) {
        return driver.findElements(locator.by())
                .stream()
                .map(Element::new)
                .collect(Collectors.toList());
    }

    public Element findElement(final Locator locator) {
        return new Element(driver.findElement(locator.by()));
    }

    public void refresh() {
        driver.navigate().refresh();
    }

    @Step("Get {value} from cookie {name}")
    public void getUuidValueFromCookieByName(String name, String value) throws Exception {
        log.info("Getting Cookie: " + name);
        Cookie cookie = driver.manage().getCookieNamed(name);
        String cookieString = cookie.toString();
        log.info("Cookie obtained: " + cookieString);
        attachInfoToAllure("Cookie obtained: ", cookieString);
        log.info("Regex: " + "\\b" + value +"=.*?\\&\\b");
        Pattern findChunkValue = Pattern.compile("\\b" + value +"=.*?\\&\\b");
        Matcher matcherChunk = findChunkValue.matcher(cookieString);
        if (matcherChunk.find()) {
            String chunkString = matcherChunk.group();
            log.info("ChunkValue obtained: " + chunkString);
            Pattern finUuidValue = Pattern.compile("[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}");
            Matcher matcherUuid = finUuidValue.matcher(chunkString);
            if (matcherUuid.find()) {
                String valueString = matcherUuid.group();
                log.info("UUID Value of obtained: " + valueString);
                attachInfoToAllure("UUID Value obtained: ", valueString);
            }
            else
            {
                throw new Exception("No uuid found in " + value);
            }
        }
        else
        {
            throw new Exception("Value " + value + " not found");
        }
    }

    @Step("Get and validate cookie {name}")
    public boolean getCookieByNameAndValidate(String name, String expectedEntry) {
        log.info("Getting Cookie: " + name);
        Cookie cookie = driver.manage().getCookieNamed(name);
        String cookieString = cookie.toString();
        log.info("Cookie obtained: " + cookieString);
        attachInfoToAllure("Cookie obtained: ", cookieString);
        return cookieString.contains(expectedEntry);
    }

    @Step("Execute command and expect error")
    public String executeCommandAndExpectError(String command) {
        log.info("Send a command to browser");
        attachInfoToAllure("Send command to browser: ", command);
        String message;
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(command);
            message = "no error, test will fail";
            attachInfoToAllure("No error message obtained: ", message);
        }
        catch(Exception e) {
            message = e.getMessage();
            log.info(message);
            attachInfoToAllure("Error message obtained: ", message);
        }
        return message;
    }

    @Step("Execute command and get result")
    public String executeCommandAndGetResult(String command) {
        log.info("Send a command to browser");
        attachInfoToAllure("Send command to browser: ", command);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String result = js.executeScript(command).toString();
        log.info("Result of command: " + result);
        attachInfoToAllure("Result of command: ", result);
        return result;
    }

    public void attachInfoToAllure(String name, String content) {
        Allure.addAttachment(name, content);
    }

    public byte[] makeScreenShot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

}
