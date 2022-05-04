package model;

import org.json.JSONObject;

// Represents a Task having a name, category, due date and time, and whether the task
// is completed or not
public class Task {
    private final String name;            // name of the task
    private final TaskCategory category;  // category of the task (School, Chore, Other)
    private final String due;             // due date and time of task
    private boolean completed;       //  whether task is completed or not


    public enum TaskCategory {
        SCHOOL, CHORE, OTHER
    }


    // REQUIRES: taskName has a non-zero length, taskCategory is one of: (SCHOOL, CHORE,
    //           or OTHER), dueDate has a non-zero length
    // EFFECTS: name of the task is set to taskName, category is set to taskCategory, due
    //          date is set to dueDate, the status of completion is set to taskCompleted
    //          where True implies the task is already completed and false otherwise.
    public Task(String taskName, TaskCategory categoryOfTask, String dueDate, boolean taskCompleted) {
        name = taskName;
        category = categoryOfTask;
        due = dueDate;
        completed = taskCompleted;
    }

    // getters
    public String getName() {
        return name;
    }

    public TaskCategory getCategory() {
        return category;
    }

    public String getDueDate() {
        return due;
    }

    public Boolean getCompleted() {
        return completed;
    }


    // MODIFIES: this
    // EFFECTS:  status of completion is updated to completed
    public void completeTask() {
        completed = true;
    }


    // EFFECTS: returns a string representation of a Task
    public String taskToString() {
        return "[" + name + ", Due by: " + due + ", Category: " + categoryToString() + ", Completed: "
                + completedToString() + "]";
    }


    // EFFECTS: returns a string representation of the status of completion
    public String completedToString() {
        if (completed) {
            return "Yes";
        } else {
            return "No";
        }

    }

    // EFFECTS: returns a string representation of task's Category
    String categoryToString() {
        if (category == TaskCategory.SCHOOL) {
            return "School Work";
        } else if (category == TaskCategory.CHORE) {
            return "Household Chore";
        } else {
            return "Other";
        }

    }

    // EFFECTS: returns true if the task is equivalent, false otherwise
    public boolean sameTask(Task task) {
        return equals(task);
    }


    // EFFECTS: returns json representation of a task
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("category", category);
        json.put("due date", due);
        json.put("completion", completed);
        return json;
    }

}




