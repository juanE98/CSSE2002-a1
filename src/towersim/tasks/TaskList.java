package towersim.tasks;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a circular list of tasks for an aircraft to cycle through.
 */
public class TaskList {

    /** The current task pointed to on the list */
    private Task currentTask;

    /** number of Tasks in the list */
    private int totalTasks;

    /** List of the tasks available for the aircraft to cycle through */
    private List<Task> tasksAvailable;

    /**
     * Creates a new TaskList with the given list of tasks.
     *
     * Initially, the current task (as returned by getCurrentTask()) should
     * be the first task in the given list.
     *
     * @param tasks list of tasks
     */
    public TaskList(List<Task> tasks) {
        this.tasksAvailable = new LinkedList<Task>(tasks);
        this.currentTask = this.tasksAvailable.get(0);
        this.totalTasks = tasksAvailable.size();
    }

    /**
     * Returns the current task in the list.
     *
     * @return current task
     */
    public Task getCurrentTask() {
        return this.currentTask;
    }

    /**
     * Returns the task in the list that comes after the current task.
     * After calling this method, the current task should still be the same
     * as it was before calling the method.
     * Note that the list is treated as circular, so if the current task is
     * the last in the list, this method should return the first element of
     * the list.
     *
     * @return next task
     */
    public Task getNextTask() {
        int currentTaskIndex = tasksAvailable.indexOf(this.getCurrentTask());
        currentTaskIndex += 1;
        if (currentTaskIndex > totalTasks) {
            currentTaskIndex = 0;
        }
        return this.tasksAvailable.get(currentTaskIndex);
    }

    /**
     * Moves the reference to the current task forward by one in the circular
     * task list.
     * After calling this method, the current task should be the next task in
     * the circular list after the "old" current task.
     * Note that the list is treated as circular, so if the current task is
     * the last in the list, the new current task should be the first element
     * of the list.
     *
     */
    public void moveToNextTask() {
        this.currentTask = this.getNextTask();
    }

    /**
     * Returns the human-readable string representation of this task list.
     * The format of the string to return is:
     *
     * TaskList currently on currentTask [taskNum/totalNumTasks]
     *
     * where currentTask is the toString() representation of the current task
     * as returned by Task.toString(), taskNum is the place the current task
     * occurs in the task list, and totalNumTasks is the number of tasks in
     * the task list.
     *
     * For example, a task list with the list of tasks [AWAY, LAND, WAIT,
     * LOAD, TAKEOFF] which is currently on the WAIT task would have a string
     * representation of "TaskList currently on WAIT [3/5]".
     *
     * @return string representation of this task list
     */
    @Override
    public String toString() {
        String str = String.format("TaskList currently on %s [%d/%d]",
                this.getCurrentTask(),tasksAvailable.indexOf(currentTask),
                this.totalTasks);
        return str;
    }

}
