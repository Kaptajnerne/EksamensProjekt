package com.example.eksamensprojekt_2sem.Model;

import java.time.LocalDate;

public class Task {

    private int task_id;
    private String task_name;

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    private double hours;
    /*private LocalDate start_date;
    private LocalDate end_date;*/
    private int project_id;

    public Task(int task_id, String task_name, double hours, int project_id) {
        this.task_id = task_id;
        this.task_name = task_name;
        this.hours = hours;
        this.project_id = project_id;
    }

    public Task() {
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }
}
