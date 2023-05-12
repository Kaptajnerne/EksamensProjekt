package com.example.eksamensprojekt_2sem.Service;

import com.example.eksamensprojekt_2sem.Model.Task;
import com.example.eksamensprojekt_2sem.Repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository){
       this.taskRepository=taskRepository;
    }
    public void createTask(Task task, int project_id)  {
        taskRepository.createTask(task, project_id);
    }
    public List<Task> getTaskByProID(int project_id) {
        return taskRepository.getTaskByProID(project_id);
    }
}
