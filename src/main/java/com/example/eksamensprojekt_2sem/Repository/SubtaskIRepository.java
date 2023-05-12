package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.Model.Subtask;

import java.util.List;

public interface SubtaskIRepository {

    //Create subtask
    public Subtask createSubtask(Subtask subtask, int task_id);

    //Get subtask from task_id
    public List<Subtask> getSubtasksByTaskID(int task_id);

    //Delete subtask
    public void deleteSubtask(int subtask_id);

    //Get subtask from subtask_id
    public Subtask getSubtaskByID(int subtask_id);
}
