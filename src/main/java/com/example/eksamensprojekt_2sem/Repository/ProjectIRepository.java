package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.Model.Project;

import java.util.List;

public interface ProjectIRepository {

    //Get projects from org
    public List<Project> getProjectsByID(int organization_id);

    //Create Project
    public void createProject(Project project, int user_id);
}
