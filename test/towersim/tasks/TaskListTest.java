package towersim.tasks;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TaskListTest {

    //Field variables
    private TaskList task1;
    private TaskList task2;

    private Task wait;
    private Task load20;
    private Task load60;
    private Task land;
    private Task away;
    private Task takeoff;

    @Before
    public void setup() {

        //Instantiate Task objects
        wait = new Task(TaskType.WAIT);
        load20 = new Task(TaskType.LOAD,20);
        load60 = new Task(TaskType.LOAD, 60);
        land = new Task(TaskType.LAND);
        away = new Task(TaskType.AWAY);
        takeoff = new Task(TaskType.TAKEOFF);

        //Populate task list with tasks
        List<Task> tasks1 = new ArrayList<>();
        tasks1.add(wait);
        tasks1.add(load20);
        tasks1.add(takeoff);
        tasks1.add(away);

        List<Task> tasks2 = new ArrayList<>();
        tasks2.add(wait);
        tasks2.add(load60);
        tasks2.add(takeoff);
        tasks2.add(away);
        tasks2.add(land);

        //Instantiate TaskList objects
        task1 = new TaskList(tasks1);
        task2 = new TaskList(tasks2);
    }


    @Test
    public void getcurrentTaskTest() {
        Task current1 = task1.getCurrentTask();
        assertEquals("current task did not match", wait, current1);

        task2.moveToNextTask();
        task2.moveToNextTask();
        Task current2 = task2.getCurrentTask();
        assertEquals("current task did not match", takeoff, current2);
    }

    @Test
    public void getNextTaskTest() {
        Task nextTask1 = task1.getNextTask();
        assertEquals("next task did not match", load20, nextTask1);
        task1.moveToNextTask();
        Task nextTask1Next = task1.getNextTask();
        assertEquals("next task did not match", takeoff, nextTask1Next);
    }

    @Test
    public void getNextTaskLastTest() {
        task2.moveToNextTask();
        task2.moveToNextTask();
        task2.moveToNextTask();
        task2.moveToNextTask();
        task2.moveToNextTask();
        Task backToStart2 = task2.getCurrentTask();
        assertEquals("did not go back to start", wait, backToStart2);


        task1.moveToNextTask();
        task1.moveToNextTask();
        task1.moveToNextTask();
        Task backToStart1 = task1.getNextTask();
        assertEquals("did not go back to start", wait, backToStart1);
    }

    @Test
    public void toStringTest() {
        task2.moveToNextTask();
        task2.moveToNextTask();

        String expected = "TaskList currently on TAKEOFF [3/5]";
        assertEquals("toString() representation did not match", expected,
                task2.toString());
    }

    @Test
    public void toStringTestFirst() {
        String expected1 = "TaskList currently on WAIT [1/4]";
        assertEquals("toString() representation did not match", expected1, task1.toString());
        task1.moveToNextTask();
        String expectedNext = "TaskList currently on LOAD at percent20 [2/4]";
        assertEquals("toString() representation did not match", expectedNext,
                task1.toString());
    }
}
