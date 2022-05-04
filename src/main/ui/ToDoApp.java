package ui;

import model.Task;
import model.ToDoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static model.Task.TaskCategory.*;


// ToDoList application
public class ToDoApp {
    private static final String JSON_STORE = "./data/todolist.json";
    private Scanner input;
    private ToDoList userList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: runs the ToDoApplication
    public ToDoApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        userList = new ToDoList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runToDo();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runToDo() {
        boolean keepGoing = true;
        String command;
        input = new Scanner(System.in);

        //init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye and remember to complete your tasks!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("v")) {
            doTaskView();
        } else if (command.equals("a")) {
            doAddTask();
        } else if (command.equals("c")) {
            doCompleteTask();
        } else if (command.equals("s")) {
            saveToDoList();
        } else if (command.equals("l")) {
            loadToDoList();
        } else if (command.equals("d")) {
            doDeleteTask();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tv -> View all tasks");
        System.out.println("\ta -> Add a task");
        System.out.println("\tc -> Mark task as completed");
        System.out.println("\ts -> Save my to-do list to file");
        System.out.println("\tl -> Load a to-do list from file");
        System.out.println("\td -> Delete a task");
        System.out.println("\tq -> Quit");
    }

    // MODIFIES: this
    // EFFECTS: displays all of the users tasks
    private void doTaskView() {
        ToDoList list = userList;
        printTasks(list);
    }

    // EFFECTS: prints all of the users tasks
    private void printTasks(ToDoList list) {
        System.out.println(list.stringToDoList());
    }

    //EFFECTS: adds the task to the ToDoList
    private void doAddTask() {
        System.out.println("\nEnter task name");
        String taskName = input.next();

        System.out.println("\nEnter task category");
        Task.TaskCategory category = chooseCategory();

        System.out.println("\nEnter the due date of this task");
        String due = input.next();

        System.out.println("\nHas this task been completed?");
        boolean completion = chooseCompletion();

        Task newTask = new Task(taskName, category, due, completion);
        userList.addTask(newTask);
        doTaskView();
    }

    //EFFECTS: deletes the task from the ToDoList
    private void doDeleteTask() {
        System.out.println("\nEnter task name");
        String taskName = input.next();
        Task deletedTask = userList.findTask(taskName);

        if (userList.contains(deletedTask)) {
            userList.deleteTask(deletedTask);
            doTaskView();
        } else {
            System.out.println("This task does not exist in your ToDo List");
        }
    }

    // EFFECTS: prompts user to choose the category of the task
    private Task.TaskCategory chooseCategory() {
        String selection = "";  // force entry into loop

        while (!(selection.equals("s") || selection.equals("c") || selection.equals("o"))) {
            System.out.println("\ns -> School Work");
            System.out.println("\nc -> Household Chores");
            System.out.println("\no -> Other");

            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("s")) {
            return SCHOOL;
        } else if (selection.equals("c")) {
            return CHORE;
        } else {
            return OTHER;
        }
    }

    // EFFECTS: prompts the user to chose the completion status of the task
    private boolean chooseCompletion() {
        String selection = "";  // force entry into loop

        while (!(selection.equals("y") || selection.equals("n"))) {
            System.out.println("\ny for Yes");
            System.out.println("\nn for No");

            selection = input.next();
            selection = selection.toLowerCase();
        }

        return selection.equals("y");
    }

    // updates the completion status to true for the given task
    public void doCompleteTask() {
        System.out.println("\nEnter the name of the Task you completed");
        String completedName = input.next();

        Task completedTask = userList.findTask(completedName);

        if (userList.contains(completedTask)) {
            userList.completeOneTask(completedTask);
            doTaskView();
        } else {
            System.out.println("This task does not exist in your ToDo List");
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveToDoList() {
        try {
            jsonWriter.open();
            jsonWriter.write(userList);
            jsonWriter.close();
            System.out.println("Saved your " + (userList.size()) + " task long ToDo list to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads todolist from file
    private void loadToDoList() {
        try {
            userList = jsonReader.read();
            System.out.println("Loaded your " + userList.size() + " task long ToDo list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}







