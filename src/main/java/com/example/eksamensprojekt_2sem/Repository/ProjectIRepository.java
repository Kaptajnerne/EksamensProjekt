package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.Model.Project;

import java.util.List;

public interface ProjectIRepository {

    //Get projects from org
    public List<Project> getProjectsByID(int organization_id);

    //Create project
    public void createProject(Project project, int user_id);

    //Edit project
    public void editProject(Project project, int project_id, int user_id);

    //Delete project
    public void deleteProject(int projectId);

    //Get project by project_id and user_id
    public Project getProjectByIDs(int project_id, int user_id);

    public int getProjectID (int task_id);
}
