package towersim.ground;

// add any required imports here

import org.junit.Before;
import org.junit.Test;
import towersim.aircraft.Aircraft;
import towersim.aircraft.AircraftCharacteristics;
import towersim.aircraft.FreightAircraft;
import towersim.aircraft.PassengerAircraft;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;
import towersim.util.NoSpaceException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GateTest {
    private Gate gate1;
    private Gate gate785;

    private Aircraft plane1;
    private Aircraft plane2;

    private Task wait;
    private Task load20;
    private Task load60;
    private Task away;
    private Task takeoff;


    @Before
    public void setUp(){
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
        TaskList taskList1 = new TaskList(tasks1);


        List<Task> tasks2 = new ArrayList<>();
        tasks2.add(wait);
        tasks2.add(load60);
        tasks2.add(takeoff);
        tasks2.add(away);
        TaskList taskList2 = new TaskList(tasks2);

        //Instantiate plane object
        plane1 = new PassengerAircraft("Passenger123",
                AircraftCharacteristics.AIRBUS_A320,taskList1,27000,50);
        plane2 = new FreightAircraft("Cargo899",
                AircraftCharacteristics.BOEING_747_8F,taskList2,222110,103000);
    }

    @Test
    public void getGateNumberTest() {
        assertEquals("gate number did not match",1 , gate1.getGateNumber());
        assertEquals("gate number did not match",785 , gate785.getGateNumber());
        try {
            assertEquals("gate number did not match", 2, gate1.getGateNumber());
            fail();
        } catch (AssertionError e) {
            //passed test
        }
    }

    @Test
    public void parkAircraftTest() {
        try {
            //Plane 1 should park successfully
            gate1.parkAircraft(plane1);
            try {
                gate1.parkAircraft(plane2);
                fail();
            } catch (NoSpaceException e) {
                //Plane 2 shouldnt be able to park
            }
        } catch (NoSpaceException e) {
            fail();
        }
    }

    @Test
    public void aircraftLeavesTest() throws NoSpaceException {
        gate1.parkAircraft(plane1);
        gate1.aircraftLeaves();
        assertEquals("aircraft did not leave", false, gate1.isOccupied());
    }

    @Test
    public void noAircraftLeavesTest() {
        gate1.aircraftLeaves();
        assertEquals("Gate contains aircraft", false, gate1.isOccupied());
    }

    @Test
    public void isOccupiedTest() throws NoSpaceException {
        gate1.parkAircraft(plane1);
        assertTrue(gate1.isOccupied());
    }

    @Test
    public void isNotOccupiedTest() {
        gate1.aircraftLeaves();
        assertFalse(gate1.isOccupied());
    }

    @Test
    public void getAircraftAtGateTest() throws NoSpaceException {
        gate1.parkAircraft(plane2);
        assertTrue(gate1.getAircraftAtGate() == plane2);

        gate785.parkAircraft(plane1);
        assertTrue(gate785.getAircraftAtGate() == plane1);
        assertFalse(gate785.getAircraftAtGate() == null);
    }

    @Test
    public void getNullAircraftAtGateTest() {
        assertTrue(gate1.getAircraftAtGate() == null);
    }

    @Test
    public void testToStringAircraft() throws NoSpaceException {
        String gate1String = "Gate 1 [Passenger123]";
        gate1.parkAircraft(plane1);
        assertEquals("String did not match", gate1String, gate1.toString());

        String gate1StringWrong = "Gate  1 [ Passenger123 ]";
        assertNotEquals("String not expected to match", gate1StringWrong,
                gate1.toString());

        String gate1StringWrong2 = " Gate 1 Passenger123 ";
        assertNotEquals("String not expected to match", gate1StringWrong2,
                gate1.toString());
    }

    @Test
    public void testToStringEmpty() throws NoSpaceException {
        String gate1String = "Gate 1 [empty]";
        assertEquals("String did not match", gate1String, gate1.toString());

        String gate1StringWrong = "Gate 1 [Passenger123]";
        assertNotEquals("String did not match", gate1StringWrong,
                gate1.toString());
    }

}
