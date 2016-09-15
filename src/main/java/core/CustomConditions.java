package core;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static core.Helpers.contains;
import static core.Helpers.getTexts;
import static core.Helpers.getVisibleElements;

public class CustomConditions {

    public static ExpectedCondition<WebElement> listElementWithCssClass(final By elementsLocator, final String expectedCss) {
        return elementExceptionsCatcher(new ExpectedCondition<WebElement>() {
            List<WebElement> actualElements;
            String currentCssClass;

            public WebElement apply(WebDriver driver) {
                WebElement currentElement;
                actualElements = driver.findElements(elementsLocator);

                for(int i=0; i < actualElements.size(); i++) {
                    currentElement = actualElements.get(i);
                    currentCssClass = currentElement.getAttribute("class");
                    if(contains(currentCssClass, expectedCss)) {
                      return currentElement;
                    }
                }
                return null;
            }

            public String toString() {
                return String.format("elements ['%s'] should have CSS class ['%s'], while actual classes are ('%s')  "
                        , actualElements.toArray().toString(), expectedCss, currentCssClass);
            }
        });
    }

    public static ExpectedCondition<WebElement> listElementWithExactText(final By elementsLocator, final String expectedText) {
        return elementExceptionsCatcher(new ExpectedCondition<WebElement>() {
            List<WebElement> actualElements;
            List<String> actualTexts;

            public WebElement apply(WebDriver driver) {
                actualElements = driver.findElements(elementsLocator);
                actualTexts = getTexts(actualElements);
                for (int i = 0; i < actualTexts.size(); i++) {
                    if (actualTexts.get(i).equals(expectedText)) {
                        return actualElements.get(i);
                    }
                }
                return null;
            }
            public String toString() {
                return String.format("elements of list located ['%s'] should have text ['%s']. " +
                        "Actual texts are: ['%s']",elementsLocator, expectedText, Arrays.toString(actualTexts.toArray()));
            }
        });
    }

    public static ExpectedCondition<List<WebElement>> exactTextOf(final By elementsLocator, final String... expectedTexts) {
        return elementExceptionsCatcher(new ExpectedCondition<List<WebElement>>() {
            private List<String> elementsTexts;

            public List<WebElement> apply(WebDriver driver) {
                elementsTexts = new ArrayList<>();
                List<WebElement> innerElements = driver.findElements(elementsLocator);

                for (int i = 0; i < innerElements.size(); i++) {
                    elementsTexts.add(innerElements.get(i).getText());
                }

                if (innerElements.size() != expectedTexts.length) {
                    return null;
                }

                for (int i = 0; i < innerElements.size(); i++) {
                    if (!elementsTexts.get(i).equals(expectedTexts[i])) {
                        return null;
                    }
                }
                return innerElements;
            }

            public String toString() {
                return String.format("elements of list located ['%s'] should have ('%s') texts, " +
                                "while actual texts are ('%s')", elementsLocator, Arrays.toString(elementsTexts.toArray()),
                        Arrays.toString(expectedTexts));
            }
        });
    }

    public static ExpectedCondition<List<WebElement>> exactTextsOfVisible(final By elementsLocator, final String... expectedTexts) {
        return elementExceptionsCatcher(new ExpectedCondition<List<WebElement>>() {
            private List<String> elementsTexts;

            public List<WebElement> apply(WebDriver driver) {
                List<WebElement> innerElements = driver.findElements(elementsLocator);
                elementsTexts = getTexts(getVisibleElements(innerElements));

                for (int i = 0; i < elementsTexts.size(); i++) {
                    if (!elementsTexts.get(i). equals(expectedTexts[i])) {
                        return null;
                    }
                }
                return innerElements;
            }

            public String toString() {
                return String.format("elements of list located ['%s'] should have ('%s') visible ('%s') texts, " +
                        "while actual visible texts are ('%s')",
                        elementsLocator,expectedTexts.length ,elementsTexts.toArray().toString(), Arrays.toString(expectedTexts));
            }
        });
    }

    public static  ExpectedCondition<Boolean> sizeOf(final By elementsLocator, final int size) {
        return elementExceptionsCatcher(new ExpectedCondition<Boolean>() {
            List<WebElement> actualElements;
            int actualElementsSize = 0;

            public Boolean apply(WebDriver driver) {
                actualElements = driver.findElements(elementsLocator);
                actualElementsSize = actualElements.size();
                if (actualElementsSize == size) {
                    return true;
                }
                return false;
            }

            public String toString() {
                return String.format("elements of list located ['%s'] should have size ['%s']. " +
                        "Actual size is ['%s']", elementsLocator, size, actualElementsSize);
            }
        });
    }

    public static  ExpectedCondition<Boolean> sizeOfVisible(final By elementsLocator, final int size) {
        return elementExceptionsCatcher(new ExpectedCondition<Boolean>() {
            List<WebElement> actualElements;
            int elementVisibleCount = 0;

            public Boolean apply(WebDriver driver) {
                actualElements = driver.findElements(elementsLocator);

                List<WebElement> currentVisibleElements = getVisibleElements(actualElements);
                elementVisibleCount = currentVisibleElements.size();
                if (elementVisibleCount == size) {
                    return true;
                }
                return false;
            }

            public String toString() {
                return String.format("Visible elements of list located ['%s'] should have size ['%s']. " +
                        "Actual size is ['%s']",elementsLocator,size , elementVisibleCount);
            }
        });
    }

    private static <V> ExpectedCondition<V> elementExceptionsCatcher(final ExpectedCondition<V> condition) {
        return new ExpectedCondition<V>() {
            public V apply(WebDriver input) {
                try {
                    return condition.apply(input);
                } catch (StaleElementReferenceException | ElementNotVisibleException | IndexOutOfBoundsException e) {
                    return null;
                }
            }

            public String toString() {
                return condition.toString();
            }
        };
    }

}
