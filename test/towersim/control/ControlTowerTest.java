package towersim.control;

import org.junit.Before;
import org.junit.Test;
import towersim.aircraft.Aircraft;
import towersim.aircraft.AircraftCharacteristics;
import towersim.aircraft.FreightAircraft;
import towersim.aircraft.PassengerAircraft;
import towersim.ground.AirplaneTerminal;
import towersim.ground.Gate;
import towersim.ground.HelicopterTerminal;
import towersim.ground.Terminal;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;
import towersim.util.NoSpaceException;
import towersim.util.NoSuitableGateException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ControlTowerTest {

    private ControlTower control1;
    private HelicopterTerminal terminalH1;
    private AirplaneTerminal terminalA1;
    private AirplaneTerminal terminalA2;
    private Task wait;
    private Task load20;
    private Task load60;
    private Task away;
    private Task takeoff;
    private PassengerAircraft planeP1;
    private FreightAircraft planeF1;
    private PassengerAircraft planeP2;
    private Gate gateA;
    private Gate gateB;
    private Gate gateC;
    private AirplaneTerminal terminalA3;

    @Before
    public void setUp() {

        //Terminal Objects
        terminalH1 = new HelicopterTerminal(1);
        terminalA1 = new AirplaneTerminal(2);
        terminalA2 = new AirplaneTerminal(3);
        terminalA3 = new AirplaneTerminal(4);

        //Gates
        gateA = new Gate(1);
        gateB = new Gate(2);
        gateC = new Gate(3);


        //Task Objects
        wait = new Task(TaskType.WAIT);
        load20 = new Task(TaskType.LOAD,20);
        load60 = new Task(TaskType.LOAD, 60);
        away = new Task(TaskType.AWAY);
        takeoff = new Task(TaskType.TAKEOFF);

        //Populate task list with tasks
        List<Task> tasks1 = new ArrayList<>();
        tasks1.add(wait);
        tasks1.add(load20);
        TaskList taskList1 = new TaskList(tasks1);

        List<Task> tasks2 = new ArrayList<>();
        tasks2.add(wait);
        tasks2.add(load60);
        TaskList taskList2 = new TaskList(tasks2);

        //Plane objects
        planeP1 = new PassengerAircraft("Passenger123",
                AircraftCharacteristics.AIRBUS_A320,taskList1,27000,50);
        planeF1 = new FreightAircraft("Cargo899",
                AircraftCharacteristics.BOEING_747_8F,taskList2,222110,103000);
        planeP2 = new PassengerAircraft("PAS124",
                AircraftCharacteristics.BOEING_787,taskList2, 102206, 120);

        //Control Tower
        control1 = new ControlTower();
    }

    @Test
    public void getTerminalsNull() {
        List<Terminal> compareList = new ArrayList();
        assertEquals(control1.getTerminals(),compareList);
    }

    @Test
    public void getTerminalsModifyTest() {
        List<Terminal> modifyList = control1.getTerminals();
        modifyList.add(terminalA1);
        assertNotEquals(control1.getTerminals(),modifyList);
    }

    @Test
    public void addTerminalsTest() {
        control1.addTerminal(terminalA1);
        List<Terminal> compareList = new ArrayList();
        compareList.add(terminalA1);
        assertEquals(control1.getTerminals(),compareList);
    }


    @Test
    public void addAircraftTest() {
        try {
            control1.addAircraft(planeP1);
        } catch (NoSuitableGateException e) {
            fail();
        }
    }

    @Test
    public void addAircraftFail() throws NoSpaceException {
        terminalA1.addGate(gateA);
        control1.addTerminal(terminalA1);
        control1.getTerminals();
        try {
            control1.addAircraft(planeP1);
            control1.addAircraft(planeP2);
            fail();
        } catch (NoSuitableGateException e) {
            //thorows exception as gate is full
        }
    }

    @Test
    public void getAircraftTest() {
        List<Terminal> compareList = new ArrayList();
        assertEquals(control1.getAircraft(),compareList);
    }

    @Test
    public void getAircraftModifyTest() {
        List<Aircraft> modifyList = control1.getAircraft();
        modifyList.add(planeP1);
        assertNotEquals(control1.getAircraft(),modifyList);
    }

    @Test
    public void findUnoccupiedGateTest() {

    }

    @Test
    public void findGateOfAircraftTest() {

    }
}