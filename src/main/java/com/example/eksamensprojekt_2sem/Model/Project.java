package com.example.eksamensprojekt_2sem.Model;

import java.util.List;

public class Project {

    private int project_id;
    private String project_name;
    private double estimated_time;
    private List<Employee> employees;
    private int organization_id;

    public Project(int project_id, String project_name, double estimated_time, List<Employee> employees, int organization_id) {
        this.project_id = project_id;
        this.project_name = project_name;
        this.estimated_time = estimated_time;
        this.employees = employees;
        this.organization_id = organization_id;
    }

    public Project(String project_name, double estimated_time) {
        this.project_name = project_name;
        this.estimated_time = estimated_time;
    }

    public Project() {
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employee) {
        this.employees = employee;
    }

    public int getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(int organization_id) {
        this.organization_id = organization_id;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

}