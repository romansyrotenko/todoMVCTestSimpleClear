package core;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomConditions {

    public static ExpectedCondition<WebElement> listElementWithCssClass(final By elementsLocator, final String expectedCss) {
        return elementExceptionsCatcher(new ExpectedCondition<WebElement>() {
            List<WebElement> actualElement;
            WebElement currentElement;

            public WebElement apply(WebDriver driver) {
                actualElement = driver.findElements(elementsLocator);

                for(int i=0; i < actualElement.size(); i++) {
                    currentElement = actualElement.get(i);
                    if (currentElement.getAttribute("class").contains(expectedCss)) {
                        return currentElement;
                    }
                }
                return null;
            }

            public String toString() {
                return String.format("elements ['%s'] doesn't have element with expected inner CSS selector ['%s'] "
                        , actualElement.toArray().toString(), expectedCss);
            }
        });
    }

    public static ExpectedCondition<WebElement> listElementWithText(final By elementsLocator, final String expectedText) {
        return elementExceptionsCatcher(new ExpectedCondition<WebElement>() {
            List<WebElement> actualElements;

            public WebElement apply(WebDriver driver) {
                actualElements = driver.findElements(elementsLocator);
                for (int i = 0; i < actualElements.size(); i++) {
                    WebElement currentElement = actualElements.get(i);
                    if (currentElement.getText().equals(expectedText)) {
                        return currentElement;
                    }
                }
                return null;
            }
            public String toString() {
                return String.format("elements of list located ['%s'] doesn't contain element with expected text ['%s']. " +
                        "Actual texts are: ['%s']",elementsLocator, expectedText, actualElements.toArray().toString());
            }
        });
    }

    public static ExpectedCondition<List<WebElement>> texts(final By elementsLocator, final String... expectedTexts) {
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
                        "while actual texts are ('%s')",elementsLocator, elementsTexts.toArray().toString(),
                        Arrays.toString(expectedTexts));
            }
        });
    }

    public static ExpectedCondition<List<WebElement>> textsOfVisible(final By elementsLocator, final String... expectedTexts) {
        return elementExceptionsCatcher(new ExpectedCondition<List<WebElement>>() {
            private List<String> elementsTexts;

            public List<WebElement> apply(WebDriver driver) {
                elementsTexts = new ArrayList<>();
                List<WebElement> innerElements = driver.findElements(elementsLocator);

                for (int i = 0; i < innerElements.size(); i++) {
                    if(innerElements.get(i).isDisplayed()) {
                        elementsTexts.add(innerElements.get(i).getText());
                    }
                }

                for (int i = 0; i < elementsTexts.size(); i++) {
                    if (!elementsTexts.get(i).equals(expectedTexts[i])) {
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
            List<WebElement> actualElement;

            public Boolean apply(WebDriver driver) {
                actualElement = driver.findElements(elementsLocator);
                if (actualElement.size() == size) {
                    return true;
                }
                return false;
            }

            public String toString() {
                return String.format("elements of list located ['%s'] should have size ['%s']. " +
                        "Actual size is ['%s']",elementsLocator,size , actualElement.size());
            }
        });
    }

    public static  ExpectedCondition<Boolean> sizeVisibleOf(final By elementsLocator, final int size) {
        return elementExceptionsCatcher(new ExpectedCondition<Boolean>() {
            List<WebElement> actualElement;
            int elementVisibleCount = 0;

            public Boolean apply(WebDriver driver) {
                actualElement = driver.findElements(elementsLocator);

                for (int i = 0; i < actualElement.size(); i++) {
                    if(actualElement.get(i).isDisplayed()) {
                        elementVisibleCount++;
                    }
                }

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
