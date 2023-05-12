package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.Model.Task;

import java.util.List;

public interface TaskIRepository {

    //get task from project_id
    public List<Task> getTaskByProID(int project_id);

    //view task
    public void createTask(Task task, int project_id);

}
