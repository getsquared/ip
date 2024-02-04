package toothless.task;

import toothless.exception.ToothlessException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * A TaskList class to store and operate on tasks in list. 
 */
public class TaskList {
    private ArrayList<Task> tasks;

    private static final DateTimeFormatter DATETIME_PARSE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * A public constructor to initialize new TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * A public constructor to initialize new TaskList with given ArrayList.
     *
     * @param taskArrayList ArrayList with Tasks.
     */
    public TaskList(ArrayList<Task> taskArrayList) {
        this.tasks = taskArrayList;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return integer of number of tasks in the list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Getter for ArrayList of Tasks.
     *
     * @return ArrayList of Tasks.
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Marks a task in the list as done.
     *
     * @param index Index of task to mark as done.
     * @return Marked task.
     */
    public Task markTask(int index) {
        Task task = tasks.get(index - 1);
        task.markAsDone();
        return task;
    }

    /**
     * Marks a task in the list as not done.
     *
     * @param index Index of task to mark as not done.
     * @return Unmarked task.
     */

    public Task unmarkTask(int index) {
        Task task = tasks.get(index - 1);
        task.unmarkAsDone();
        return task;
    }

    /**
     * Deletes a task in the list.
     *
     * @param index Index of task to delete.
     * @return Deleted task.
     */
    public Task deleteTask(int index) {
        Task deletedTask = tasks.get(index - 1);
        tasks.remove(index - 1);
        return deletedTask;
    }

    /**
     * Adds ToDo task to list.
     *
     * @param taskDescription String to describe the task.
     * @return New ToDo task.
     */
    public Task addToDoToList(String taskDescription) {
        Task newTask = new ToDo(taskDescription);
        tasks.add(newTask);
        return newTask;
    }

    /**
     * Adds Deadline task to list.
     *
     * @param taskDescription String to describe the task.
     * @param by              String for datetime deadline the task is due by.
     * @return New Deadline task.
     * @throws ToothlessException if datetime fails to parse.
     */
    public Task addDeadlineToList(String taskDescription, String by) throws ToothlessException {
        try {
            LocalDateTime deadlineBy = LocalDateTime.parse(by, DATETIME_PARSE_FORMATTER);
            Task newTask = new Deadline(taskDescription, deadlineBy);
            tasks.add(newTask);
            return newTask;
        } catch (DateTimeParseException e) {
            throw new ToothlessException("Sorry, /by field datetime should use the following format: " +
                    "[yyyy-mm-dd hh:mm].");
        }
    }

    /**
     * Adds Event task to list.
     *
     * @param taskDescription String to describe the task.
     * @param from            String for datetime start of event.
     * @param to              String for datetime end of event.
     * @return New Event task.
     * @throws ToothlessException if datetime fails to parse.
     */
    public Task addEventToList(String taskDescription, String from, String to) throws ToothlessException {
        try {
            LocalDateTime eventFrom = LocalDateTime.parse(from, DATETIME_PARSE_FORMATTER);
            LocalDateTime eventTo = LocalDateTime.parse(to, DATETIME_PARSE_FORMATTER);
            Task newTask = new Event(taskDescription, eventFrom, eventTo);
            tasks.add(newTask);
            return newTask;
        } catch (DateTimeParseException e) {
            throw new ToothlessException("Sorry, /from and /to field datetime should use the following format: " +
                    "[yyyy-mm-dd hh:mm].");
        }

    }
}