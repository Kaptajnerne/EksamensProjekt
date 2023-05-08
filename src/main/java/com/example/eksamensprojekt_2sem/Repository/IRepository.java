package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.Model.Organization;
import com.example.eksamensprojekt_2sem.Model.Project;

import java.util.List;

public interface IRepository {

    //---------------------------------ORGANIZATION JDBC METHODS---------------------------------//
    //Sign in with user
    public Organization signIn(String organization_name, String password);

    //---------------------------------EMPLOYEE JDBC METHODS-------------------------------------//


    //---------------------------------PROJECT JDBC METHODS--------------------------------------//
    //Get projects from org
    public List<Project> getProjects(int organization_id);
    //Add projects to org
    public void addProject (Project project);

    //---------------------------------TASK JDBC METHODS-----------------------------------------//


    //---------------------------------SUBTASK JDBC METHODS--------------------------------------//
}
