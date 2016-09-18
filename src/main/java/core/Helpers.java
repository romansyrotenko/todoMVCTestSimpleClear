package core;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Helpers {

    public static boolean isWordContainedInPhrase(String phrase, String word) {
        String[] arr = phrase.split("\\s");

        for(int i = 0; i < arr.length; i++) {
            if (word.equals(arr[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean isContainedInList(List<String> elementsTexts, String... expectedTexts) {

        List<String> expectedListOfTexts = Arrays.asList(expectedTexts);

        if (!elementsTexts.equals(expectedListOfTexts)) {
            return false;
        }
        return true;
    }

    public static List<String> getTexts(List<WebElement> webElements) {
        List<String> currentTexts = new ArrayList<>();
        for (int i = 0; i<webElements.size(); i++) {
            currentTexts.add(webElements.get(i).getText());
        }
        return currentTexts;
    }

    public static List<WebElement> getVisibleElements(List<WebElement> webElements) {
        List<WebElement> currentVisibleElements = new ArrayList<>();

        for (int i = 0; i < webElements.size(); i++) {
            if(webElements.get(i).isDisplayed()) {
                currentVisibleElements.add(webElements.get(i));
            }
        }
        return currentVisibleElements;
    }
}
