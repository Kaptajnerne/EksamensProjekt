package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.Model.Task;

import java.util.List;

public interface TaskIRepository {

    //get task from project_id
    public List<Task> getTaskByProID(int project_id);

    //view task
    public void createTask(Task task, int project_id);

    //Edit task
    public void editTask(Task task, int task_id, int project_id);

    //Get project from user_id and user_id
    public Task getTaskByIDs(int task_id, int project_id);

    //Delete task
    public void deleteTask(int task_id);

    //Get task from task_id
    public Task getTaskbyTaskId(int task_id);

    //Get project_id from task_id
    public int getProIDbyTaskID(int task_id);

}
