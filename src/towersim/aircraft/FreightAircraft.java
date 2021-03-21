package towersim.aircraft;

import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;

/**
 * Represents an aircraft capable of carrying freight cargo.
 */
public class FreightAircraft extends Aircraft {

    /** Maximum freight capacity of aircraft */
    private final int maxFreight;

    /** Amount of freight onboard, in kilograms  */
    private int freightAmount;

    /**
     * Creates a new freight aircraft with the given callsign, task list,
     * fuel capacity, amount of fuel and kilograms of freight.
     * If the given amount of freight is less than zero or greater than the
     * aircraft's maximum freight capacity as defined in the aircraft's
     * characteristics, then an IllegalArgumentException should be thrown.
     *
     * @param callsign unique callsign
     * @param characteristics characteristics that describe this aircraft
     * @param tasks task list to be used by aircraft
     * @param fuelAmount current amount of fuel onboard, in litres
     * @param freightAmount current amount of freight onboard, in kilograms
     *
     * @throws IllegalArgumentException if freightAmount < 0 or if
     * freightAmount > freight capacity
     */
    public FreightAircraft(String callsign,
                              AircraftCharacteristics characteristics,
                           TaskList tasks, double fuelAmount,
                           int freightAmount) {
        super(callsign, characteristics, tasks, fuelAmount);
        this.freightAmount = freightAmount;
        this.maxFreight = this.getCharacteristics().freightCapacity;

        if (freightAmount < 0 || freightAmount > maxFreight) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns the total weight of the aircraft in its current state.
     * The total weight for a freight aircraft is calculated as the sum of:
     *
     * the aircraft's empty weight
     * the amount of fuel onboard the aircraft multiplied by the weight
     * of a litre of fuel
     *
     * the weight of the aircraft's freight onboard
     *
     * @return total weight of aircraft in kilograms
     */
    @Override
    public double getTotalWeight() {
        return this.freightAmount + super.getTotalWeight();
    }

    /**
     * Returns the number of ticks required to load the aircraft at the gate.
     *
     * The freight to be loaded is equal to the maximum freight capacity of the
     * aircraft multiplied by the load ratio specified in the aircraft's current
     * task (see Task.getLoadPercent()). The result of this calculation should
     * be rounded to the nearest whole kilogram.
     *
     * @return time to load aircraft, in ticks
     */
    @Override
    public int getLoadingTime() {

        int freightCapacity = this.characteristics.freightCapacity;
        int loadPercentage = this.tasks.getCurrentTask().getLoadPercent();

        //Weight of freight to be loaded in kg.
        int freightLoaded =
                (int) Math.round((double) freightCapacity * (loadPercentage / 100));
        if (freightLoaded < 1000) {
            return 1;
        } else if (freightLoaded > 50000) {
            return 3;
        } else {
            return 2;
        }
    }

    /**
     * Returns the current occupancy level of this entity as a percentage from 0
     * to 100.
     *
     * @return occupancy level as a percentage
     */
    @Override
    public int calculateOccupancyLevel() {
        int freight = this.freightAmount;
        int maxFreight = this.maxFreight;
        return (int) Math.round( (double) (freight / maxFreight) * 100);
    }

    /**
     * Updates the aircraft's state on each tick of the simulation.
     *
     * Firstly, the Aircraft.tick() method in the superclass should be called
     * to perform refueling and burning of fuel.
     *
     * Next, if the aircraft's current task is a LOAD task, freight should be
     * loaded onto the aircraft. The amount of freight to load in a single
     * call of tick() is equal to the total amount of freight to be loaded
     * based on the LOAD task's load percentage, divided by the loading time
     * given by getLoadingTime(). This ensures that freight is loaded in
     * equal increments across the entire loading time. The result of this
     * division operation may yield a freight amount that is not an integer,
     * in which case it should be rounded to the nearest whole integer
     * (kilogram).
     *
     * Note that the total amount of freight on the aircraft should not be
     * allowed to exceed the maximum freight capacity of the aircraft, given
     * by AircraftCharacteristics.freightCapacity.
     *
     * For example, suppose an aircraft initially has 0kg of freight onboard
     * and has a current task of type LOAD with a load percentage of 65%. The
     * aircraft has a freight capacity of 40,000kg. Then, the total amount of
     * freight to be loaded is 65% of 40,000kg = 26,000kg. According to
     * getLoadingTime(), this amount of freight will take 2 ticks to load.
     * So, a single call to tick() should increase the amount of freight
     * onboard by 26,000kg / 2 = 13,000kg.
     */
    @Override
    public void tick() {
        super.tick();

        //Aircraft's current task
        Task currentTask = this.getTaskList().getCurrentTask();

        //Variables for weight calculation
        int loadPercentage =
                (int) Math.round((double) currentTask.getLoadPercent() * this.maxFreight);
        int loadingTime = this.getLoadingTime();


        //Weight of cargo to be loaded to aircraft
        int loadFreight =
                (int) Math.round((double) loadPercentage / loadingTime);

        if (currentTask.equals(TaskType.LOAD)) {
            this.freightAmount += loadFreight;
            //Maximum capacity reached
            if (this.freightAmount > this.maxFreight) {
                this.freightAmount = this.maxFreight;
            }
        }
    }
}
