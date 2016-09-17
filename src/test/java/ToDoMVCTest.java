import org.junit.Before;
import org.junit.Test;
import testconfigs.BaseTest;

import static pages.TodoMVCPage.*;

public class ToDoMVCTest extends BaseTest {

    @Before
    public void openToDoMVC() {
        openApp();
    }

    @Test
    public void testAtAllFilter() {
        //create tasks
        add("task 1");
        add("task 2");
        add("task 3");
        add("task 4");

        assertTasks("task 1", "task 2", "task 3", "task 4");
        assertItemsLeft(4);

        //delete task
        delete("task 2");

        assertTasks("task 1", "task 3", "task 4");
        assertItemsLeft(3);

        //mark task and clear
        toggle("task 4");
        clearCompleted();

        assertTasks("task 1", "task 3");
        assertItemsLeft(2);

        //reopen task
        toggle("task 3");

        assertTasks("task 1", "task 3");
        assertItemsLeft(1);

        toggle("task 3");

        assertTasks("task 1", "task 3");
        assertItemsLeft(2);

        //edit task
        edit("task 3", "task 3 edited");

        assertTasks("task 1", "task 3 edited");
        assertItemsLeft(2);

        //mark all as completed and clear
        toggleAll();
        clearCompleted();

        assertNoTasks();
        assertItemsLeftIsInvisible();
    }

    @Test
    public void testAtActiveFilter() {
        //Given filtered tasks at active filter
        add("task 1");
        add("task 2");
        add("task 3");
        toggle("task 2");
        toggle("task 3");
        filterActive();

        assertVisibleTasks("task 1");

        //Create task
        add("task 4");

        assertVisibleTasks("task 1", "task 4");
        assertItemsLeft(2);

        filterAll();
        assertTasks("task 1", "task 2", "task 3", "task 4");

        //Delete task
        filterActive();
        delete("task 4");

        assertVisibleTasks("task 1");
        assertItemsLeft(1);

        filterAll();
        assertTasks("task 1", "task 2", "task 3");

        //Editing task
        filterActive();
        edit("task 1", "task 1 edited");

        assertVisibleTasks("task 1 edited");
        assertItemsLeft(1);

        filterAll();
        assertTasks("task 1 edited", "task 2", "task 3");

        //Remove task via editing to "empty" text
        filterActive();
        add("task 5");
        edit("task 1 edited", "");

        assertVisibleTasks("task 5");
        assertItemsLeft(1);

        filterAll();
        assertTasks("task 2", "task 3", "task 5");

        //Mark completed
        filterActive();
        add("task 6");
        toggle("task 6");
        assertVisibleTasks("task 5");
        filterCompleted();

        assertVisibleTasks("task 2", "task 3", "task 6");

        filterAll();
        assertTasks("task 2", "task 3", "task 5", "task 6");
        assertItemsLeft(1);
    }

    @Test
    public void testAtCompletedFilter() {
        //Given filtering task at Completed filter
        add("task 1");
        add("task 2");
        toggle("task 1");
        toggle("task 2");
        filterCompleted();

        assertVisibleTasks("task 1", "task 2");

        //Move task from Complited tab to Active tab
        toggle("task 1");

        assertVisibleTasks("task 2");
        assertItemsLeft(1);

        filterActive();
        assertVisibleTasks("task 1");

        //Clear complited tasks
        toggleAll();
        assertItemsLeft(0);
        filterCompleted();

        assertVisibleTasks("task 1", "task 2");

        clearCompleted();
        assertNoVisibleTasks();

        //Delete task
        add("task 1");
        add("task 2");
        toggleAll();
        filterCompleted();
        delete("task 2");

        assertVisibleTasks("task 1");

        filterActive();
        assertNoVisibleTasks();

        filterAll();
        assertVisibleTasks("task 1");
    }
}
