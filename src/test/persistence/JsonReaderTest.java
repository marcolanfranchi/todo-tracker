

package persistence;

import model.Task;
import model.ToDoList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// reference: JsonReaderTest from JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ToDoList td = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyToDoList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyToDoList.json");
        try {
            ToDoList td = reader.read();
            assertEquals(0, td.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralToDoList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralToDoList.json");
        try {
            ToDoList td = reader.read();
            List<Task> tasks = td.getTasks();
            assertEquals(2, tasks.size());
            checkTask("Quiz7", Task.TaskCategory.SCHOOL, "Friday13th", false, tasks.get(0));
            checkTask("PetDog", Task.TaskCategory.CHORE, "Tomorrow", false, tasks.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
