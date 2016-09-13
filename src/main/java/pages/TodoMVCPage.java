package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static core.ConciseAPI.*;
import static core.CustomConditions.*;

public class TodoMVCPage {

    private static By tasks = byCss("#todo-list>li");
    private static By todoCount = byCss("#todo-count strong");

    public static void openApp() {
        open("http://todomvc4tasj.herokuapp.com/");
    }

    public static void addTask(String text) {
        $(byCss("#new-todo")).sendKeys(text + Keys.ENTER);
    }

    public static void delete(String text) {
        hover($(listElementWithText(tasks, text)));
        $(listElementWithText(tasks, text), ".destroy").click();
    }

    public static void toggle(String text) {
        $(listElementWithText(tasks, text), ".toggle").click();
    }

    public static void clearComplited() {
        $(byCss("#clear-completed")).click();
    }

    public static void filterAll() {
        $(byLinkText("All")).click();
    }

    public static void filterActive() {
        $(byLinkText("Active")).click();
    }

    public static void filterCompleted() {
        $(byLinkText("Completed")).click();
    }

    public static void editTask(String oldText, String newText ) {
        doubleClick($(listElementWithText(tasks, oldText), "label"));
        setAttributeValue($(listElementWithCssClass(tasks, "editing")), newText);
    }

    public static void assertTodoCount(int count) {
        assertThat(ExpectedConditions.textToBe(todoCount, Integer.toString(count)));
    }

    public static void assertInVisibleToDoCount() {
        assertThat(ExpectedConditions.invisibilityOfElementLocated(todoCount));
    }

    public static void toggleAll() {
        $(byCss("#toggle-all")).click();
    }

    public static void exactTexts(String ... expectedTexts) {
        assertThat(texts(tasks, expectedTexts));
    }

    public static void exactTextsOfVisible(String ... expectedTexts) {
        assertThat(textsOfVisible(tasks, expectedTexts));
    }

    public static void assertTasksSizeOf(int size) {
        assertThat(sizeOf(tasks, size));
    }

    public static void assertVisibleTasksSizeOf(int size) {
        assertThat(sizeVisibleOf(tasks, size));
    }

}
