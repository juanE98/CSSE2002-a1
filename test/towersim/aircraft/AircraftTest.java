package towersim.aircraft;

import org.junit.Before;
import org.junit.Test;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AircraftTest {

    private PassengerAircraft aircraftA;
    private Task wait;
    private Task load20;
    private Task takeoff;
    private Task away;
    private Task load60;
    private Task land;
    private TaskList task2;
    private FreightAircraft aircraftB;
    private TaskList TaskList1;

    @Before
    public void setUp() throws Exception {


        //Instantiate Task objects
        wait = new Task(TaskType.WAIT);
        load20 = new Task(TaskType.LOAD,20);
        load60 = new Task(TaskType.LOAD, 60);
        land = new Task(TaskType.LAND);
        away = new Task(TaskType.AWAY);
        takeoff = new Task(TaskType.TAKEOFF);

        List<Task> tasks1 = new LinkedList<>();
        tasks1.add(wait);
        tasks1.add(load20);
        tasks1.add(takeoff);
        tasks1.add(away);

        List<Task> tasks2 = new LinkedList<>();
        tasks2.add(wait);
        tasks2.add(load60);
        tasks2.add(takeoff);
        tasks2.add(away);
        tasks2.add(land);

        //Instantiate TaskList objects
        TaskList1 = new TaskList(tasks1);
        task2 = new TaskList(tasks2);


        aircraftA = new PassengerAircraft("ARC",
                AircraftCharacteristics.AIRBUS_A320,TaskList1,22000,50);
        aircraftB = new FreightAircraft("FRE",
                AircraftCharacteristics.SIKORSKY_SKYCRANE,task2,2500,5000);
    }

    @Test
    public void getFuelAmount() {
        double fuel = 22000.0;
        assertTrue(Math.abs(fuel - aircraftA.getFuelAmount()) < 0.001);
    }

    @Test
    public void getFuelPercentRemaining() {
        double fuelGone = 81.0;
        assertTrue(Math.abs(fuelGone - aircraftA.getFuelPercentRemaining()) < 0.001);
    }

    @Test
    public void getTotalWeight() {
    }

    @Test
    public void getTaskList() {
    }

    @Test
    public void getLoadingTime() {
    }

    @Test
    public void tick() {
        double fuelBurn = 19280.0;
        aircraftA.tasks.moveToNextTask();
        aircraftA.tasks.moveToNextTask();
        aircraftA.tasks.moveToNextTask();
        aircraftA.tick();
        assertTrue(Math.abs(fuelBurn - aircraftA.fuelAmount) < 0.001);

        aircraftA.tasks.moveToNextTask();
        aircraftA.tasks.moveToNextTask();
        aircraftA.tick();
//        double reFuel = 12312312.0;
//        assertTrue(Math.abs(reFuel - aircraftA.fuelAmount) < 0.001);
    }

    @Test
    public void testToString() {
        String aircraftASTR = "AIRPLANE ARC AIRBUS_A320 WAIT";
        assertEquals("",aircraftASTR, aircraftA.toString());

        String airCraftEmer = "HELICOPTER FRE SIKORSKY_SKYCRANE WAIT " +
                "(EMERGENCY)";
        aircraftB.declareEmergency();
        assertEquals("",airCraftEmer, aircraftB.toString());

    }
}