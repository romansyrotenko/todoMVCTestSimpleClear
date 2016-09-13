package core;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ConciseAPI {

    private static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    public static void setDriver(WebDriver driver) {
        ConciseAPI.driver = driver;
    }

    public static void open(String url) {
        getDriver().get(url);
    }

    public static <V> V assertThat(ExpectedCondition<V> condition, long timeout) {
        return new WebDriverWait(driver, timeout).until(condition);
    }

    public static <V> V assertThat(ExpectedCondition<V> condition) {
        return assertThat(condition, Configuration.timeout);
    }

    public static WebElement $(By elementLocator) {
        return assertThat(visibilityOfElementLocated(elementLocator));
    }

    public static WebElement $(ExpectedCondition<WebElement> webElementExpectedCondition) {
        return assertThat(webElementExpectedCondition);
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

    public static void doubleClick(WebElement element) {
        Actions action = new Actions(getDriver());
        action.doubleClick(element).perform();
    }

    public static void hover(WebElement element) {
        Actions action = new Actions(getDriver());
        action.moveToElement(element).build().perform();
    }

    public static void setAttributeValue(WebElement element, String value)  {
        element.findElement(byCss(".edit")).clear();
        element.findElement(byCss(".edit")).sendKeys(value + Keys.ENTER);
    }
}
