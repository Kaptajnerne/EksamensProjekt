package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.Model.Subtask;

import java.util.List;

public interface SubtaskIRepository {

    //Get subtask from task_id
    public List<Subtask> getSubtasksByTaskID(int task_id);

    //Create subtask
    public Subtask createSubtask(Subtask subtask, int task_id);

    //Edit subtask
    public void editSubtask(Subtask subtask, int subtask_id, int task_id);

    //Get subtask from subtask_id and task_id
    public Subtask getSubtaskByIDs(int subtask_id, int task_id);

    //Delete subtask
    public void deleteSubtask(int subtask_id);

    //Get subtask from subtask_id
    public Subtask getSubtaskByID(int subtask_id);
}
