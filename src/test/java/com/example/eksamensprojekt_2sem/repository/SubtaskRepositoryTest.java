package com.example.eksamensprojekt_2sem.repository;

import com.example.eksamensprojekt_2sem.Model.Subtask;
import com.example.eksamensprojekt_2sem.Model.Task;
import com.example.eksamensprojekt_2sem.Repository.SubtaskRepositoryDB;
import com.example.eksamensprojekt_2sem.Repository.TaskRepositoryDB;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ComponentScan("com.example.eksamensprojekt_2sem.Repository.SubtaskRepositoryDB")
public class SubtaskRepositoryTest {

    /*@Autowired
    private SubtaskRepositoryDB subtaskRepositoryDB;

    @Test
    public void testCreateSubtask() {
        //Test data
        int task_id = 10;
        Subtask subtask = new Subtask(1, "Test Subtask", 1.0, LocalDate.now(), LocalDate.now(), 1, task_id);

        //Create subtask method
        subtaskRepositoryDB.createSubtask(subtask, task_id);

        //Verify the subtask is created
        assertNotNull(subtask.getTask_id());
        assertEquals("Test Subtask", subtask.getSubtask_name());
        assertEquals(1.0, subtask.getHours());
        assertEquals(LocalDate.now(), subtask.getStart_date());
        assertEquals(LocalDate.now(), subtask.getEnd_date());
        assertEquals(1, subtask.getStatus());
    }

    @Test
    public void testEditSubtask() {
        //Test data
        int task_id = 10;
        Subtask subtask = new Subtask(1, "Test Subtask", 5.0, LocalDate.now(), LocalDate.now(), 1, task_id);

        //Create subtask method
        subtaskRepositoryDB.createSubtask(subtask, task_id);

        //Edit subtask valuues
        subtask.setSubtask_name("Updated Task");
        subtask.setHours(82.0);
        subtask.setStart_date(LocalDate.now().plusDays(1));
        subtask.setEnd_date(LocalDate.now().plusDays(2));
        subtask.setStatus(2);

        //Edit subtask method
        subtaskRepositoryDB.editSubtask(subtask, subtask.getSubtask_id(), task_id);

        //Get updated subtask
        Subtask updatedSubtask = subtaskRepositoryDB.getSubtaskByID(subtask.getSubtask_id());

        //Verify subtask is updated
        assertNotNull(updatedSubtask);
        assertEquals(subtask.getSubtask_name(), updatedSubtask.getSubtask_name());
        assertEquals(subtask.getHours(), updatedSubtask.getHours(), 0.0);
        assertEquals(subtask.getStart_date(), updatedSubtask.getStart_date());
        assertEquals(subtask.getEnd_date(), updatedSubtask.getEnd_date());
        assertEquals(subtask.getStatus(), updatedSubtask.getStatus());
    }

    @Test
    public void testDeleteSubtask() {
        //Test data
        int task_id = 10;
        Subtask subtask = new Subtask(1, "Test Task", 5.0, LocalDate.now(), LocalDate.now().plusDays(1), 1, task_id);

        //Create subtask method
        subtaskRepositoryDB.createSubtask(subtask, task_id);

        //Delete subtask method
        subtaskRepositoryDB.deleteSubtask(subtask.getTask_id());

        //Verify subtask is deleted
        assertNull(subtaskRepositoryDB.getSubtaskByID(subtask.getTask_id()));
    }
*/
}
