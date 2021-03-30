package towersim.ground;

import org.junit.Before;
import org.junit.Test;
import towersim.aircraft.AircraftCharacteristics;
import towersim.aircraft.FreightAircraft;
import towersim.aircraft.PassengerAircraft;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;
import towersim.util.NoSpaceException;
import towersim.util.NoSuitableGateException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TerminalTest {

    private AirplaneTerminal terminalA;
    private HelicopterTerminal terminalH;
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
    private FreightAircraft helicopterA;
    private Gate gateD;
    private Gate gateE;
    private Gate gateF;
    private Gate gateG;

    @Before
    public void setUp() throws Exception {
        terminalA = new AirplaneTerminal(1);
        terminalH = new HelicopterTerminal(2);
        gateA = new Gate(1);
        gateB = new Gate(2);
        gateC = new Gate(3);
        gateD = new Gate(4);
        gateE = new Gate(5);
        gateF = new Gate(6);
        gateG = new Gate(7);


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

        helicopterA = new FreightAircraft("HEL",
                AircraftCharacteristics.SIKORSKY_SKYCRANE, task2, 3000, 4500);
    }

    @Test
    public void addGateTest() {
        try {
            terminalA.addGate(gateA);
        } catch (NoSpaceException e) {
            fail();
        }
        try {
            terminalA.addGate(gateB);
        } catch (NoSpaceException e) {
            fail();
        }
        try {
            terminalA.addGate(gateC);
        } catch (NoSpaceException e) {
            fail();
        }
        try {
            terminalA.addGate(gateD);
        } catch (NoSpaceException e) {
            fail();
        }
        try {
            terminalA.addGate(gateE);
        } catch (NoSpaceException e) {
            fail();
        }
        try {
            terminalA.addGate(gateF);
        } catch (NoSpaceException e) {
            fail();
        }
        try {
            terminalA.addGate(gateG);
            fail();
        } catch (NoSpaceException e) {
            //throws exception
        }
    }

    @Test
    public void findUnoccupiedGateNoneTest() throws NoSpaceException {
        gateA.parkAircraft(aircraftA);
        terminalA.addGate(gateA);
        try {
            terminalA.findUnoccupiedGate();
            fail();
        } catch (NoSuitableGateException e) {
            //throws exception as gate is occupied
        }
    }

    @Test
    public void findUnoccupiedGateSuccessTest() throws NoSpaceException {
        terminalA.addGate(gateB);
        try {
            terminalA.findUnoccupiedGate();
        } catch (NoSuitableGateException e) {
            // Suitable gate should exist, exception shouldn't be thrown.
            fail();
        }
    }

    @Test
    public void calculateOccupancyLevel() throws NoSpaceException {
        gateA.parkAircraft(aircraftA);
        terminalA.addGate(gateA);
        terminalA.addGate(gateB);
        terminalA.addGate(gateC);
        assertEquals("",33,terminalA.calculateOccupancyLevel());


    }

    @Test
    public void testToString() {
        String expected = "AirplaneTerminal 1, 0 gates";
        assertEquals("",expected,terminalA.toString());
        terminalA.declareEmergency();
        String expected1 = "AirplaneTerminal 1, 0 gates (EMERGENCY)";
        assertEquals("",expected1,terminalA.toString());
    }
}