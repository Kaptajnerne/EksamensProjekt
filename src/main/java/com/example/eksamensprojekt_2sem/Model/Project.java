package com.example.eksamensprojekt_2sem.Model;

public class Project {

    private int project_id;
    private String project_name;
    private double estimated_time;
    private int employee_id;
    private int organization_id;

    public Project(int project_id , String project_name, double estimated_time, int employee_id, int organization_id) {
        this.project_id = project_id;
        this.project_name = project_name;
        this.estimated_time = estimated_time;
        this.employee_id = employee_id;
        this.organization_id = organization_id;

    }

    public Project(int projectId, String projectName) {
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public double getEstimated_time() {
        return estimated_time;
    }

    public void setEstimated_time(double estimated_time) {
        this.estimated_time = estimated_time;
    }


    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(int organization_id) {
        this.organization_id = organization_id;
    }
}
