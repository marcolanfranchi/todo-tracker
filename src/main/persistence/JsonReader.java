package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Task;
import model.ToDoList;
import org.json.*;

// reference: JsonReader from JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads todolist from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads todolist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ToDoList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseToDoList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ToDoList from JSON object and returns it
    private ToDoList parseToDoList(JSONObject jsonObject) {
        ToDoList td = new ToDoList();
        addTasks(td, jsonObject);
        return td;
    }

    // MODIFIES: td
    // EFFECTS: parses tasks from JSON object and adds them to todolist
    private void addTasks(ToDoList td, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("ToDoList");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addTask(td, nextTask);
        }
    }

    // MODIFIES: td
    // EFFECTS: parses task from JSON object and adds it to todolist
    private void addTask(ToDoList td, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Task.TaskCategory category = Task.TaskCategory.valueOf(jsonObject.getString("category"));
        String due = jsonObject.getString("due date");
        boolean completed = jsonObject.getBoolean("completion");
        Task task = new Task(name, category, due, completed);
        td.addTask(task);
    }
}
