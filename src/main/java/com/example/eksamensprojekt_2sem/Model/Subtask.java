package com.example.eksamensprojekt_2sem.Model;

import java.time.LocalDate;

public class Subtask {

    private int subtask_id;
    private String subtask_name;
    private LocalDate start_date;
    private LocalDate end_date;
    private int project_id;

    public Subtask(int subtask_id, String subtask_name, LocalDate start_date, LocalDate end_date, int project_id) {
        this.subtask_id = subtask_id;
        this.subtask_name = subtask_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.project_id = project_id;
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

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

}
