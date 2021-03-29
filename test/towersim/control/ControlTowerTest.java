package towersim.control;

import org.junit.Before;
import org.junit.Test;
import towersim.aircraft.AircraftCharacteristics;
import towersim.aircraft.FreightAircraft;
import towersim.aircraft.PassengerAircraft;
import towersim.ground.AirplaneTerminal;
import towersim.ground.HelicopterTerminal;
import towersim.ground.Terminal;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;
import towersim.util.NoSuitableGateException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ControlTowerTest {

    private ControlTower control1;
    private HelicopterTerminal terminal1;
    private AirplaneTerminal terminal2;
    private AirplaneTerminal terminal3;
    private PassengerAircraft plane1;
    private FreightAircraft plane2;
    private Task wait;
    private Task load20;
    private Task load60;
    private Task away;
    private Task takeoff;

    @Before
    public void setUp() {
        HelicopterTerminal terminal1 = new HelicopterTerminal(1);
        AirplaneTerminal terminal2 = new AirplaneTerminal(2);
        AirplaneTerminal terminal3 = new AirplaneTerminal(3);

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
        TaskList taskList1 = new TaskList(tasks1);


        List<Task> tasks2 = new ArrayList<>();
        tasks2.add(wait);
        tasks2.add(load60);
        tasks2.add(takeoff);
        tasks2.add(away);
        TaskList taskList2 = new TaskList(tasks2);

        plane1 = new PassengerAircraft("Passenger123",
                AircraftCharacteristics.AIRBUS_A320,taskList1,27000,50);
        plane2 = new FreightAircraft("Cargo899",
                AircraftCharacteristics.BOEING_747_8F,taskList2,222110,103000);

    }

    @Test
    public void getTerminalsNull() {
        ControlTower control1 = new ControlTower();
        List<Terminal> compareList = new ArrayList();
        assertEquals(control1.getTerminals(),compareList);
    }

    @Test
    public void addTerminalsTest() {
        ControlTower control1 = new ControlTower();
        control1.addTerminal(terminal1);
        List<Terminal> compareList = new ArrayList();
        compareList.add(terminal1);
        assertEquals(control1.getTerminals(), compareList);
    }

    @Test
    public void addAircraftTest() {
        ControlTower control1 = new ControlTower();
        try {
            control1.addAircraft(plane1);
        } catch (NoSuitableGateException e) {
            fail();
        }
    }

    @Test
    public void addAircraftFail() throws NoSuitableGateException {
        ControlTower control1 = new ControlTower();
        control1.addAircraft(plane1);
        try {
            control1.addAircraft(plane2);
        } catch (NoSuitableGateException e) {

        }



    }

    @Test
    public void getAircraft() {
    }

    @Test
    public void findUnoccupiedGate() {
    }

    @Test
    public void findGateOfAircraft() {
    }
}