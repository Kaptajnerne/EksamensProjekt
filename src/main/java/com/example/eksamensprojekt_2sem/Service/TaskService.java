package com.example.eksamensprojekt_2sem.Service;

import com.example.eksamensprojekt_2sem.Model.Task;
import com.example.eksamensprojekt_2sem.Repository.TaskRepositoryDB;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    TaskRepositoryDB taskRepositoryDB;
    public TaskService(TaskRepositoryDB taskRepositoryDB){
       this.taskRepositoryDB = taskRepositoryDB;
    }
    public void createTask(Task task, int project_id)  {
        taskRepositoryDB.createTask(task, project_id);
    }
    public List<Task> getTaskByProID(int project_id) {
        return taskRepositoryDB.getTaskByProID(project_id);
    }
}
