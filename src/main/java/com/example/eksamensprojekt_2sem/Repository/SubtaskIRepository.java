package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.Model.Subtask;

import java.util.List;

public interface SubtaskIRepository {
    public Subtask createSubtask(Subtask subtask, int task_id);

    public List<Subtask> getSubtasksByTaskID(int task_id);
}
