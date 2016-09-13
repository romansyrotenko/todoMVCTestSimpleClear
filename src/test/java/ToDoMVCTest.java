import org.junit.Before;
import org.junit.Test;
import pages.TodoMVCPage;
import testconfigs.BaseTest;

import static pages.TodoMVCPage.openApp;

public class ToDoMVCTest extends BaseTest {

    @Before
    public void openToDoMVC() {
        openApp();
    }

    @Test
    public void testAtAllFilter() {

        //create tasks
        TodoMVCPage.addTask("task 1");
        TodoMVCPage.addTask("task 2");
        TodoMVCPage.addTask("task 3");
        TodoMVCPage.addTask("task 4");
        TodoMVCPage.exactTexts("task 1", "task 2", "task 3", "task 4");
        TodoMVCPage.assertTodoCount(4);

        //delete task
        TodoMVCPage.delete("task 2");
        TodoMVCPage.exactTexts("task 1", "task 3", "task 4");
        TodoMVCPage.assertTodoCount(3);

        //mark task and clear
        TodoMVCPage.toggle("task 4");
        TodoMVCPage.clearComplited();
        TodoMVCPage.exactTexts("task 1", "task 3");
        TodoMVCPage.assertTodoCount(2);

        //reopen task
        TodoMVCPage.toggle("task 3");
        TodoMVCPage.assertTodoCount(1);
        TodoMVCPage.exactTexts("task 1", "task 3");
        TodoMVCPage.toggle("task 3");
        TodoMVCPage.assertTodoCount(2);
        TodoMVCPage.exactTexts("task 1", "task 3");

        //edit task
        TodoMVCPage.editTask("task 1", "task 1 edited");
        TodoMVCPage.assertTodoCount(2);
        TodoMVCPage.exactTexts("task 1 edited", "task 3");

        //mark all as completed and clear
        TodoMVCPage.toggleAll();
        TodoMVCPage.clearComplited();
        TodoMVCPage.assertTasksSizeOf(0);
        TodoMVCPage.assertInVisibleToDoCount();
    }

    @Test
    public void testAtActiveFilter() {

        //Given filtered tasks at active filter
        TodoMVCPage.addTask("task 1");
        TodoMVCPage.addTask("task 2");
        TodoMVCPage.addTask("task 3");
        TodoMVCPage.toggle("task 2");
        TodoMVCPage.toggle("task 3");
        TodoMVCPage.filterActive();
        TodoMVCPage.exactTextsOfVisible("task 1");

        //Create task
        TodoMVCPage.addTask("task 4");
        TodoMVCPage.assertTodoCount(2);
        TodoMVCPage.exactTextsOfVisible("task 1", "task 4");
        TodoMVCPage.filterAll();
        TodoMVCPage.exactTexts("task 1", "task 2", "task 3", "task 4");

        //Delete task
        TodoMVCPage.filterActive();
        TodoMVCPage.delete("task 4");
        TodoMVCPage.assertTodoCount(1);
        TodoMVCPage.exactTextsOfVisible("task 1");
        TodoMVCPage.filterAll();
        TodoMVCPage.exactTexts("task 1", "task 2", "task 3");

        //Editing task
        TodoMVCPage.filterActive();
        TodoMVCPage.editTask("task 1", "task 1 edited");
        TodoMVCPage.assertTodoCount(1);
        TodoMVCPage.exactTextsOfVisible("task 1 edited");
        TodoMVCPage.filterAll();
        TodoMVCPage.exactTexts("task 1 edited", "task 2", "task 3");

        //Remove task via editing to "empty" text
        TodoMVCPage.filterActive();
        TodoMVCPage.addTask("task 5");
        TodoMVCPage.editTask("task 1 edited", "");
        TodoMVCPage.assertTodoCount(1);
        TodoMVCPage.exactTextsOfVisible("task 5");
        TodoMVCPage.filterAll();
        TodoMVCPage.exactTexts("task 2", "task 3", "task 5");

        //Mark completed
        TodoMVCPage.filterActive();
        TodoMVCPage.addTask("task 6");
        TodoMVCPage.toggle("task 6");
        TodoMVCPage.exactTextsOfVisible("task 5");
        TodoMVCPage.filterCompleted();
        TodoMVCPage.exactTextsOfVisible("task 2", "task 3", "task 6");
        TodoMVCPage.filterAll();
        TodoMVCPage.assertTodoCount(1);
        TodoMVCPage.exactTexts("task 2", "task 3", "task 5", "task 6");
    }

    @Test
    public void testAtCompletedFilter() {

        //Given filtering task at Completed filter
        TodoMVCPage.addTask("task 1");
        TodoMVCPage.addTask("task 2");
        TodoMVCPage.toggle("task 1");
        TodoMVCPage.toggle("task 2");
        TodoMVCPage.filterCompleted();
        TodoMVCPage.exactTextsOfVisible("task 1", "task 2");

        //Move task from Complited tab to Active tab
        TodoMVCPage.toggle("task 1");
        TodoMVCPage.exactTextsOfVisible("task 2");
        TodoMVCPage.assertTodoCount(1);
        TodoMVCPage.filterActive();
        TodoMVCPage.exactTextsOfVisible("task 1");

        //Clear complited tasks
        TodoMVCPage.toggleAll();
        TodoMVCPage.assertTodoCount(0);
        TodoMVCPage.filterCompleted();
        TodoMVCPage.exactTextsOfVisible("task 1", "task 2");
        TodoMVCPage.clearComplited();
        TodoMVCPage.assertVisibleTasksSizeOf(0);

        //Delete task
        TodoMVCPage.addTask("task 1");
        TodoMVCPage.addTask("task 2");
        TodoMVCPage.toggleAll();
        TodoMVCPage.filterCompleted();
        TodoMVCPage.delete("task 2");
        TodoMVCPage.exactTextsOfVisible("task 1");
        TodoMVCPage.filterActive();
        TodoMVCPage.assertVisibleTasksSizeOf(0);
        TodoMVCPage.filterAll();
        TodoMVCPage.exactTextsOfVisible("task 1");
    }
}
