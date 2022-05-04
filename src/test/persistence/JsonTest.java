package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.Task;

// reference: JsonTest from JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {
    protected void checkTask(String name, Task.TaskCategory category, String dueDate, Boolean completion, Task task) {
        assertEquals(name, task.getName());
        assertEquals(dueDate, task.getDueDate());
        assertEquals(category, task.getCategory());
        assertEquals(completion, task.getCompleted());
    }
}

