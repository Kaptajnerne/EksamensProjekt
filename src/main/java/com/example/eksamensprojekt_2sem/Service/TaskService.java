package com.example.eksamensprojekt_2sem.Service;

import com.example.eksamensprojekt_2sem.Model.Project;
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
    public void editTask(Task task, int task_id, int project_id) {
        taskRepositoryDB.editTask(task, task_id, project_id);
    }
    public Task getTaskByIDs(int task_id, int project_id) {
        return taskRepositoryDB.getTaskByIDs(task_id, project_id);
    }
    public void deleteTask(int task_id) {
        taskRepository.deleteTask(task_id);
    }

    public Task getTaskById(int taskId) {
       return taskRepository.getTaskbyTaskId(taskId);
    }

    public int getProIDbyTaskID(int task_id) {
        return taskRepository.getProIDbyTaskID(task_id);
    }
}
