package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static core.ConciseAPI.*;
import static core.CustomConditions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;

public class TodoMVCPage {

    private static By tasks = byCss("#todo-list>li");
    private static By todoCount = byCss("#todo-count strong");

    public static void openApp() {
        open("http://todomvc4tasj.herokuapp.com/");
    }

    public static void add(String taskText) {
        $(byCss("#new-todo")).sendKeys(taskText + Keys.ENTER);
    }

    public static void delete(String taskText) {
        hover($(listElementWithExactText(tasks, taskText)));
        $(listElementWithExactText(tasks, taskText), ".destroy").click();
    }

    public static void toggle(String taskText) {
        $(listElementWithExactText(tasks, taskText), ".toggle").click();
    }

    public static void clearCompleted() {
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

    public static void edit(String oldTaskText, String newTaskText) {
        doubleClick($(listElementWithExactText(tasks, oldTaskText), "label"));
        setValue($(listElementWithCssClass(tasks, "editing"), ".edit"), newTaskText + Keys.ENTER);
    }

    public static void assertItemsLeft(int count) {
        assertThat(textToBe(todoCount, Integer.toString(count)));
    }

    public static void assertItemsLeftIsInvisible() {
        assertThat(ExpectedConditions.invisibilityOfElementLocated(todoCount));
    }

    public static void toggleAll() {
        $(byCss("#toggle-all")).click();
    }

    public static void assertTasks(String... taskTexts) {
        assertThat(exactTextOf(tasks, taskTexts));
    }

    public static void assertVisibleTasks(String... taskTexts) {
        assertThat(exactTextsOfVisible(tasks, taskTexts));
    }

    public static void assertSizeOfTasks(int size) {
        assertThat(sizeOf(tasks, size));
    }

    public static void assertNoTasks() {
        assertSizeOfTasks(0);
    }

    public static void assertSizeOfVisibleTasks(int size) {
        assertThat(sizeOfVisible(tasks, size));
    }

    public static void assertNoVisibleTasks() {
        assertSizeOfVisibleTasks(0);
    }

}
