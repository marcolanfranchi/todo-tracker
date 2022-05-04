package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static model.Task.TaskCategory.CHORE;
import static model.Task.TaskCategory.SCHOOL;
import static org.junit.jupiter.api.Assertions.*;

class ToDoListTest {
    private ToDoList testList;
    private Task testTask1;
    private Task testTask2;

    @BeforeEach
    void runBefore() {
        testList = new ToDoList();
        testTask1 = new Task("ANTH 202 Quiz 6", SCHOOL,
                "Fri Oct 15 11:59pm", false);
        testTask2 = new Task("Wash Dishes", CHORE,
                "Sat Oct 16 12:00pm", false);

    }


    @Test
    void testAddTask() {
        assertFalse(testList.contains(testTask1));
        testList.addTask(testTask1);
        assertTrue(testList.contains(testTask1));
        assertEquals(1, testList.size());

    }

    @Test
    void testAddTaskExisting() {
        testList.addTask(testTask1);
        testList.addTask(testTask2);
        testList.addTask(testTask1);
        assertTrue(testList.contains(testTask1));
        assertEquals(2, testList.size());
    }


    @Test
    void testDeleteTask() {
        testList.addTask(testTask2);
        testList.addTask(testTask1);
        testList.deleteTask(testTask2);
        assertEquals(1, testList.size());
        assertFalse(testList.contains(testTask2));
        assertTrue((testList.contains(testTask1)));

    }

    @Test
    void testContains() {
        testList.addTask(testTask1);
        assertEquals(testList.size(), 1);
        assertTrue(testList.contains(testTask1));
    }


    @Test
    void testListToStrings() {
        ToDoList taskList = new ToDoList();
        ArrayList<String> stringList = new ArrayList<>();
        taskList.addTask(testTask1);
        taskList.addTask(testTask2);
        stringList.add(testTask1.taskToString());
        stringList.add(testTask2.taskToString());
        assertEquals(taskList.listToStrings(), stringList);
    }


    @Test
    void testStringToDoList() {
        testList.addTask(testTask1);
        testList.addTask((testTask2));
        assertEquals(testList.stringToDoList(),
                "[[ANTH 202 Quiz 6, Due by: Fri Oct 15 11:59pm, Category: School Work, Completed: No], " +
                        "[Wash Dishes, Due by: Sat Oct 16 12:00pm, Category: Household Chore, Completed: No]]");
    }


    @Test
    void testFindTask() {
        testList.addTask(testTask1);
        testList.addTask(testTask2);
        assertEquals(testTask1, testList.findTask("ANTH 202 Quiz 6"));
        assertEquals(testTask2, testList.findTask("Wash Dishes"));

    }

    @Test
    void testCompleteOneTask() {
        //ToDoList testList2 = new ToDoList();

        testList.addTask(testTask1);
        testList.addTask(testTask2);
        testList.completeOneTask(testTask1);

        //testList2.addTask(testTask1.completeTask());
        //testList2.addTask(testTask2);

        assertEquals("[[ANTH 202 Quiz 6, Due by: Fri Oct 15 11:59pm, " +
                        "Category: School Work, Completed: Yes], " +
                        "[Wash Dishes, Due by: Sat Oct 16 12:00pm, " +
                        "Category: Household Chore, Completed: No]]",
                testList.stringToDoList());
    }
}