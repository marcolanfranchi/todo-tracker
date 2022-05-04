package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Task.TaskCategory.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    public Task testTask;
    private Task testTask2;
    private Task testTask3;


    @BeforeEach
    public void runBefore() {
        testTask = new Task("ANTH 202 Quiz 6", SCHOOL,
                "Fri Oct 15 11:59pm", false);
        testTask2 = new Task("Wash Dishes", CHORE,
                "Sat Oct 16 12:00pm", false);
        testTask3 = new Task("Visit Grandma and Grandpa", OTHER,
                "Sun Oct 17 6:00pm", false);
    }


    @Test
    void testConstructors() {
        assertEquals("ANTH 202 Quiz 6", testTask.getName());
        assertEquals(SCHOOL, testTask.getCategory());
        assertEquals("Fri Oct 15 11:59pm", testTask.getDueDate());
        assertFalse(testTask.getCompleted());
    }

    @Test
    void testCompleteTask() {
        assertFalse(testTask.getCompleted());
        testTask.completeTask();
        assertTrue(testTask.getCompleted());
    }

    @Test
    void testTaskToString() {
        assertEquals("[ANTH 202 Quiz 6, Due by: Fri Oct 15 11:59pm, " +
                "Category: School Work, Completed: No]", testTask.taskToString());
        assertEquals("[Wash Dishes, Due by: Sat Oct 16 12:00pm, " +
                "Category: Household Chore, Completed: No]", testTask2.taskToString());
    }

    @Test
    void testCompletedString() {
        assertEquals("No", testTask.completedToString());
    }

    @Test
    void testCategoryString() {
        assertEquals("School Work", testTask.categoryToString());
        assertEquals("Household Chore", testTask2.categoryToString());
        assertEquals("Other", testTask3.categoryToString());

    }


}
