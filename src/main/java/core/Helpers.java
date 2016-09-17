package core;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Helpers {

    public static boolean isWordContainedInPhrase(String phrase, String word) {
        String[] arr = phrase.split("\\s");

//        List<String> arr2 = new ArrayList<>();
//        for (int i = 0; i < arr.length; i++) {
//            arr2.add(arr[i]);
//        }

        for(int i = 0; i < arr.length; i++) {
            if (word.equals(arr[i])) {
                return true;
            }
        }
        return false;

 //       return isContainedInList(arr2, word);

    }

    public static boolean isContainedInList(List<String> list, String... texts) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(texts[i])) {
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
