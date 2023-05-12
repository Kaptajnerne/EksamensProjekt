package com.example.eksamensprojekt_2sem.Model;

public class Subtask {

    private int subtask_id;
    private String subtask_name;
    private double hours;
    private int task_id;

    public Subtask(int subtask_id, String subtask_name, double hours, int task_id) {
        this.subtask_id = subtask_id;
        this.subtask_name = subtask_name;
        this.hours=hours;
        this.task_id = task_id;
    }

    public Subtask() {
    }

    public int getSubtask_id() {
        return subtask_id;
    }

    public void setSubtask_id(int subtask_id) {
        this.subtask_id = subtask_id;
    }

    public String getSubtask_name() {
        return subtask_name;
    }

    public void setSubtask_name(String subtask_name) {
        this.subtask_name = subtask_name;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }
}
