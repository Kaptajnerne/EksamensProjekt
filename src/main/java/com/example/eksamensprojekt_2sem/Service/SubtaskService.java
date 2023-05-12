package com.example.eksamensprojekt_2sem.Service;

import com.example.eksamensprojekt_2sem.Model.Subtask;
import com.example.eksamensprojekt_2sem.Repository.SubtaskRepositoryDB;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtaskService {

    private SubtaskRepositoryDB subtaskRepositoryDB;

    public SubtaskService (SubtaskRepositoryDB subtaskRepositoryDB) {
        this.subtaskRepositoryDB=subtaskRepositoryDB;
    }

    //Create subtask
    public void createSubtask(Subtask subtask, int task_id) {
        subtaskRepositoryDB.createSubtask(subtask, task_id);
    }

    //Get subtask by taskID
    public List<Subtask> getSubtasksByTaskID(int task_id) {
       return subtaskRepositoryDB.getSubtasksByTaskID(task_id);
    }

    //Delete subtask
    public void deleteSubtask(int subtask_id) {
        subtaskRepositoryDB.deleteSubtask(subtask_id);
    }

    //Get subtask from subtask_id
    public Subtask getSubtaskByID(int subtask_id) {
        return subtaskRepositoryDB.getSubtaskByID(subtask_id);
    }

}
