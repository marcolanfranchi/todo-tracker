package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToDoList implements Writable {
    // represents an ArrayList of tasks with a name
    ArrayList<Task> toDoList;

    // constructs an empty todolist with name
    public ToDoList() {
        toDoList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: inserts task into the todolist unless it is already there
    public void addTask(Task task) {
        if (!toDoList.contains(task)) {
            toDoList.add(task);
        }
        EventLog.getInstance().logEvent(new Event("Added Task '" + task.getName()
                + "' to the to-do list"));
    }

    // MODIFIES: this
    // EFFECTS: removes the task from the ToDoList
    public void deleteTask(Task task) {

        toDoList.remove(task);
        EventLog.getInstance().logEvent(new Event("Deleted Task '" + task.getName()
                + "' from the to-do list"));
    }

    // MODIFIES: this
    // EFFECTS: sets the status of completion of the task to true in the to do list
    // REQUIRES: completedTask is in the ToDoList
    public void completeOneTask(Task completedTask) {
        for (Task t : toDoList) {
            if (t.sameTask(completedTask)) {
                t.completeTask();
            }
        }
        EventLog.getInstance().logEvent(new Event("Completed Task" + completedTask.getName()));
    }


    // EFFECTS: if the task is contained within the todolist, return true. Otherwise return false
    public boolean contains(Task task) {
        return toDoList.contains(task);
    }

    // EFFECTS: returns the size of the todolist
    public int size() {
        return toDoList.size();
    }

    // EFFECTS: returns an unmodifiable list of tasks in this todolist
    public List<Task> getTasks() {
        return Collections.unmodifiableList(toDoList);
    }


    // EFFECTS: returns a list of String representations for each Task in the list
    public ArrayList<String> listToStrings() {
        ArrayList<String> sl = new ArrayList<>();
        for (Task t : toDoList) {
            sl.add(t.taskToString());
        }
        return sl;
    }

    // EFFECTS: returns the String representation for the entire ToDoList
    public String stringToDoList() {
        return listToStrings().toString();
    }

    // REQUIRES: s matches the name of an existing Task in the ToDoList
    // EFFECTS: returns the task from the ToDoList with the matching name
    public Task findTask(String s) {
        Task foundTask = new Task("", Task.TaskCategory.OTHER, "", false);
        for (Task t : toDoList) {
            if (t.getName().equals(s)) {
                foundTask = t;
            }
        }
        return foundTask;
    }

    @Override
    // EFFECTS: returns the todolist as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ToDoList", toDoListToJson());
        return json;
    }

    // EFFECTS: returns things in this todolist as a JSON array
    private JSONArray toDoListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t : toDoList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: deletes the task that matches the given string representation of a task
    public void deleteTaskString(String stringTask) {
        for (Task task : toDoList) {
            if (task.taskToString().equals(stringTask)) {
                deleteTask(task);
            }
        }
    }
}








