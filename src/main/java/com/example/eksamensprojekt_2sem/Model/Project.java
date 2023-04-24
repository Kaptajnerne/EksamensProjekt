package com.example.eksamensprojekt_2sem.Model;

public class Project {

    private int projectID;
    private String projectName;
    private double duration;

    public Project(int projectID ,String projectName, double duration) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.duration = duration;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }
}
