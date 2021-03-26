package towersim.aircraft;

import org.junit.Before;
import org.junit.Test;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FreightAircraftTest {
    //Field Variables
    private Task wait;
    private Task load20;
    private Task load60;
    private Task away;
    private Task takeoff;
    private FreightAircraft freight1;
    private TaskList taskList2;
    private TaskList taskList1;
    private FreightAircraft freight2;

    @Before
    public void setUp() throws Exception {


        //Instantiate Task objects
        wait = new Task(TaskType.WAIT);
        load20 = new Task(TaskType.LOAD,20);
        load60 = new Task(TaskType.LOAD, 60);
        away = new Task(TaskType.AWAY);
        takeoff = new Task(TaskType.TAKEOFF);

        //Populate task list with tasks
        List<Task> tasks1 = new ArrayList<>();
        tasks1.add(wait);
        tasks1.add(load20);
        tasks1.add(takeoff);
        tasks1.add(away);
        taskList1 = new TaskList(tasks1);

        List<Task> tasks2 = new ArrayList<>();
        tasks2.add(wait);
        tasks2.add(load60);
        tasks2.add(takeoff);
        tasks2.add(away);
        taskList2 = new TaskList(tasks2);

        //Instantiate plane object
        freight1 = new FreightAircraft("F1",
                AircraftCharacteristics.BOEING_747_8F, taskList2,200110,107750);
        freight2 = new FreightAircraft("F2",
                AircraftCharacteristics.SIKORSKY_SKYCRANE, taskList2,2000,50);

    }

    @Test
    public void getLoadingTime() {
        // >50,000
        freight1.getTaskList().moveToNextTask();
        assertEquals(3,freight1.getLoadingTime());
        // <10,000
        freight2.getTaskList().moveToNextTask();
        assertEquals(1,freight2.getLoadingTime());

    }

    @Test
    public void calculateOccupancyLevel() {
        assertEquals(78,freight1.calculateOccupancyLevel());
    }

    @Test
    public void tick() {

    }
}