package com.example.eksamensprojekt_2sem.DTO;

import java.util.List;

public class ProjectEmployeeForm {

    private int project_id;
    private String project_name;
    private double estimated_time;
    private String organization;
    private List<String> employees;

    public ProjectEmployeeForm(int project_id, String project_name, double estimated_time, String organization, List<String> employees) {
        this.project_id = project_id;
        this.project_name = project_name;
        this.estimated_time = estimated_time;
        this.organization = organization;
        this.employees = employees;
    }

    public ProjectEmployeeForm(int project_id, String project_name, double estimated_time, List<String> employees) {
        this.project_id = project_id;
        this.project_name = project_name;
        this.estimated_time = estimated_time;
        this.employees = employees;
    }

    //Default constructor
    public ProjectEmployeeForm() {
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public List<String> getEmployees() {
        return employees;
    }

    public void setEmployees(List<String> employees) {
        this.employees = employees;
    }
}
