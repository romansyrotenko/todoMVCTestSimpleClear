package core;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static core.Helpers.*;

public class CustomConditions {

    public static ExpectedCondition<WebElement> listElementWithCssClass(final By elementsLocator, final String expectedCssClass) {
        return elementExceptionsCatcher(new ExpectedCondition<WebElement>() {
            List<String> actualClasses = new ArrayList<>();

            public WebElement apply(WebDriver driver) {
                List<WebElement> actualElements = driver.findElements(elementsLocator);

                for(int i = 0; i < actualElements.size(); i++) {
                    actualClasses.add(actualElements.get(i).getAttribute("class"));
                }

                for(int i=0; i < actualClasses.size(); i++) {
                    String actualCssClasses = actualClasses.get(i);
                    if(isWordContainedInPhrase(actualCssClasses, expectedCssClass)) {
                      return actualElements.get(i);
                    }
                }
                return null;
            }

            public String toString() {
                return String.format("element of list located ['%s'] should have CSS class ['%s'], while actual classes are ('%s')  "
                        , elementsLocator.toString(), expectedCssClass, actualClasses.toString());
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
                return String.format("element of list located ['%s'] should have text ['%s']. " +
                        "Actual texts are: ['%s']",elementsLocator, expectedText, actualTexts.toString());
            }
        });
    }

    public static ExpectedCondition<List<WebElement>> exactTextOf(final By elementsLocator, final String... expectedTexts) {
        return elementExceptionsCatcher(new ExpectedCondition<List<WebElement>>() {
            private List<String> actualTexts;

            public List<WebElement> apply(WebDriver driver) {
                actualTexts = new ArrayList<>();
                List<WebElement> actualElements = driver.findElements(elementsLocator);
                actualTexts = getTexts(actualElements);

                if(!isContainedInList(actualTexts, expectedTexts)) {
                    return null;
                }
                return actualElements;
            }

            public String toString() {
                return String.format("elements of list located ['%s'] should have ('%s') texts, " +
                                "while actual texts are ('%s')", elementsLocator, Arrays.toString(actualTexts.toArray()),
                        Arrays.toString(expectedTexts));
            }
        });
    }

    public static ExpectedCondition<List<WebElement>> exactTextsOfVisible(final By elementsLocator, final String... expectedTexts) {
        return elementExceptionsCatcher(new ExpectedCondition<List<WebElement>>() {
            private List<String> actualTexts;

            public List<WebElement> apply(WebDriver driver) {
                List<WebElement> innerElements = driver.findElements(elementsLocator);
                actualTexts = getTexts(getVisibleElements(innerElements));

                if(!isContainedInList(actualTexts, expectedTexts)) {
                    return null;
                }
                return innerElements;
            }

            public String toString() {
                return String.format("visible elements of list located ['%s'] should have texts ('%s') " +
                        "while actual texts are ('%s')",
                        elementsLocator, Arrays.toString(actualTexts.toArray()), Arrays.toString(expectedTexts));
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
                return (actualElementsSize == size);
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

                List<WebElement> actualVisibleElements = getVisibleElements(actualElements);
                elementVisibleCount = actualVisibleElements.size();
                return (elementVisibleCount == size);
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
