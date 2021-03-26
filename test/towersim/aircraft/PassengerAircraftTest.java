package towersim.aircraft;

import org.junit.Before;
import org.junit.Test;
import towersim.ground.Gate;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PassengerAircraftTest {

    //Field Variables
    private Gate gate1;
    private Gate gate785;
    private Task wait;
    private Task load20;
    private Task load60;
    private Task away;
    private Task takeoff;
    private PassengerAircraft plane1;
    private PassengerAircraft plane2;
    private TaskList taskList2;
    private TaskList taskList1;


    @Before
    public void setup() {
        //Instantiate Gate objects
        gate1 = new Gate(1);
        gate785 = new Gate(785);

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
        plane1 = new PassengerAircraft("P1",
                AircraftCharacteristics.AIRBUS_A320,taskList1,27000,50);
        plane2 = new PassengerAircraft("P2",
                AircraftCharacteristics.BOEING_787,taskList2,100100,150);
    }

    @Test
    public void constructorInvalidNegativePassengerTest() {
        try {
            PassengerAircraft plane3 = new PassengerAircraft("P3",
                    AircraftCharacteristics.FOKKER_100, taskList2, 10000, -3);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test public void constructorInvalidMaxPassengerTest() {
        try {
            PassengerAircraft plane3 = new PassengerAircraft("P3",
                    AircraftCharacteristics.FOKKER_100, taskList2, 10000, 98);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void getTotalWeightTest() {
        double calculated = 68700;
        assertTrue(Math.abs(calculated - plane1.getTotalWeight()) < 0.001);
    }


    @Test
    public void getLoadingTime() {
        assertEquals(1,plane1.getLoadingTime());
        plane2.tasks.moveToNextTask();
        assertEquals(2,plane2.getLoadingTime());
    }

    @Test
    public void getLoadingTimeBounded() {
        PassengerAircraft planeB = new PassengerAircraft("P3",
                AircraftCharacteristics.FOKKER_100, taskList2, 10000, 0);
        assertEquals(1,planeB.getLoadingTime());
    }

    @Test
    public void calculateOccupancyLevel() {
        assertEquals(33,plane1.calculateOccupancyLevel());
        assertEquals(62,plane2.calculateOccupancyLevel());
    }

    @Test
    public void tick() {

    }
}
