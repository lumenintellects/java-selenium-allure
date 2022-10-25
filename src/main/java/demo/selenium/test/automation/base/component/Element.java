package demo.selenium.test.automation.base.component;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.SECONDS;

@Log4j2
public class Element implements WebElement, WrapsElement {

    private final WebElement element;

    public Element(final WebElement element) {
        this.element = element;
    }

    @Override
    public void click() {
        element.click();
    }

    @Override
    public void submit() {
        element.submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        element.sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        element.clear();
    }

    @Override
    public String getTagName() {
        return element.getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return element.getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return element.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return element.isEnabled();
    }

    @Override
    public String getText() {
        return element.getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return element.findElements(by);
    }

    public List<Element> findElements(Locator locator) {
        return findElements(locator.by()).stream()
                .map(Element::new)
                .collect(Collectors.toList());
    }

    @Override
    public WebElement findElement(By by) {
        return element.findElement(by);
    }

    public Element findElement(Locator locator) {
        return new Element(findElement(locator.by()));
    }

    public String getTitle() {
        return element.getAttribute("title");
    }

    public WebElement findElement(By by, int timeOutInSeconds) {
        try {
            return findElement(by);
        } catch (NotFoundException e) {
            new FluentWait<WebElement>(this)
                    .withTimeout(Duration.of(timeOutInSeconds, SECONDS))
                    .pollingEvery(Duration.of(100, MILLIS))
                    .ignoring(NoSuchElementException.class);
            return findElement(by);
        }
    }

    public boolean hasElement(final Locator locator) {
        try {
            element.findElement(locator.by());
            return true;
        } catch (WebDriverException e) {
            return false;
        }
    }

    @Override
    public boolean isDisplayed() {
        return element.isDisplayed();
    }

    @Override
    public Point getLocation() {
        return element.getLocation();
    }

    @Override
    public Dimension getSize() {
        return element.getSize();
    }

    @Override
    public Rectangle getRect() {
        return element.getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        return element.getCssValue(propertyName);
    }

    @Override
    public WebElement getWrappedElement() {
        return element;
    }

    public String attribute(String name) {
        return getAttribute(name);
    }

    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exception) {
            log.error("sleep exception", exception);
        }
    }

    public Element parent() {
        return new Element(this.element.findElement(By.xpath("..")));
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return null;
    }

    public boolean containsLink() {
        return getTagName().equals("a") || findElements(By.tagName("a")).size() > 0;
    }
}