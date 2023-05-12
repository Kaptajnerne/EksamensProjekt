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
}
