package persistence;

import model.Task;
import model.ToDoList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// reference: JsonWriterTest from JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            ToDoList td = new ToDoList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            ToDoList td = new ToDoList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyToDoList.json");
            writer.open();
            writer.write(td);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyToDoList.json");
            td = reader.read();
            assertEquals(0, td.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ToDoList td = new ToDoList();
            td.addTask(new Task("Quiz7", Task.TaskCategory.SCHOOL, "Friday13th", false));
            td.addTask(new Task("PetDog", Task.TaskCategory.CHORE, "Tomorrow", false));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralToDoList.json");
            writer.open();
            writer.write(td);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralToDoList.json");
            td = reader.read();
            List<Task> tasks = td.getTasks();
            assertEquals(2, tasks.size());
            checkTask("Quiz7", Task.TaskCategory.SCHOOL, "Friday13th", false, tasks.get(0));
            checkTask("PetDog", Task.TaskCategory.CHORE, "Tomorrow", false, tasks.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

