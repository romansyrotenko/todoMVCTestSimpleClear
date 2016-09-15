package core;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Helpers {

    public static boolean contains(String currentString, String expectedCss) {
        String[] arr = currentString.split("\\s");
        for(int i = 0; i < arr.length; i++) {
            if (expectedCss.equals(arr[i])) {
                return true;
            }
        }
        return false;
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
