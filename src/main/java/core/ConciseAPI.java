package core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Quotes;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ConciseAPI {

    private static Map<Thread, WebDriver> webDriverMap = new HashMap<>();

    public static WebDriver getDriver() {
        return webDriverMap.get(Thread.currentThread());
    }

    public static void setDriver(WebDriver driver) {
        webDriverMap.put(Thread.currentThread(), driver);
    }

    public static void open(String url) {
        getDriver().get(url);
    }

    public static <V> V assertThat(ExpectedCondition<V> condition, long timeout) {
        return new WebDriverWait(getDriver(), timeout).until(condition);
    }

    public static <V> V assertThat(ExpectedCondition<V> condition) {
        return assertThat(condition, Configuration.timeout);
    }

    public static WebElement $(ExpectedCondition<WebElement> conditionToWaitElement) {
        return assertThat(conditionToWaitElement);
    }

    public static WebElement $(By elementLocator) {
        return $(visibilityOfElementLocated(elementLocator));
    }

    public static WebElement $(ExpectedCondition<WebElement> conditionToWaitParentElement, By innerElementLocator) {
        return assertThat(conditionToWaitParentElement).findElement(innerElementLocator);
    }

    public static WebElement $(ExpectedCondition<WebElement> conditionToWaitParentElement, String innerElementCssSelector) {
        return $(conditionToWaitParentElement, byCss(innerElementCssSelector));
    }

    public static By byCss(String cssSelector) {
        return By.cssSelector(cssSelector);
    }

    public static By byLinkText(String text) {
        return By.linkText(text);
    }

    public static By byText(String text) {
        return By.xpath(".//*/text()[normalize-space(.) = '" + text + "']/parent::*");
    }

    public static By byTitle(String text) {
        return By.xpath(".//*[contains(@title, " + Quotes.escape(text) + ")]");
    }

    public static By byExactTitle(String text) {
        return By.xpath(".//*[@" + "title" + '=' + Quotes.escape(text) + ']');
    }

    public static void doubleClick(WebElement element) {
        actions().doubleClick(element).perform();
    }

    public static void hover(WebElement element) {
        actions().moveToElement(element).perform();
    }

    public static WebElement setValue(WebElement element, String value)  {
        element.clear();
        element.sendKeys(value);
        return element;
    }

    public static Actions actions() {
        return new Actions(getDriver());
    }
}
