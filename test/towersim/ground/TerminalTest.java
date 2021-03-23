package towersim.ground;

import org.junit.Before;
import org.junit.Test;
import towersim.aircraft.AircraftCharacteristics;
import towersim.aircraft.PassengerAircraft;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;
import towersim.util.NoSpaceException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TerminalTest {

    private AirplaneTerminal terminal1;
    private Gate gateA;
    private Gate gateB;
    private Gate gateC;
    private PassengerAircraft aircraftA;
    private Task load20;
    private Task wait;
    private Task load60;
    private Task land;
    private Task away;
    private Task takeoff;
    private TaskList task1;
    private TaskList task2;

    @Before
    public void setUp() throws Exception {
        terminal1 = new AirplaneTerminal(1);
        gateA = new Gate(1);
        gateB = new Gate(2);
        gateC = new Gate(3);


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

        aircraftA = new PassengerAircraft("ARC",
                AircraftCharacteristics.AIRBUS_A320,task1,22000,50);


    }

    @Test
    public void addGate() {
    }

    @Test
    public void getGates() {
    }

    @Test
    public void findUnoccupiedGate() {
    }

    @Test
    public void declareEmergency() {
    }

    @Test
    public void clearEmergency() {
    }

    @Test
    public void calculateOccupancyLevel() throws NoSpaceException {
        gateA.parkAircraft(aircraftA);
        terminal1.addGate(gateA);
        terminal1.addGate(gateB);
        terminal1.addGate(gateC);
        assertEquals("",33,terminal1.calculateOccupancyLevel());


    }

    @Test
    public void testToString() {
        String expected = "AirplaneTerminal 1, 0 gates";
        assertEquals("",expected,terminal1.toString());
        terminal1.declareEmergency();
        String expected1 = "AirplaneTerminal 1, 0 gates (EMERGENCY)";
        assertEquals("",expected1,terminal1.toString());
    }
}