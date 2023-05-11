package com.example.eksamensprojekt_2sem.Service;

import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Repository.ProjectRepositoryDB;
import com.example.eksamensprojekt_2sem.Repository.UserRepositoryDB;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {


    private ProjectRepositoryDB projectRepositoryDB;

    public ProjectService(ProjectRepositoryDB projectRepositoryDB) {
        this.projectRepositoryDB = projectRepositoryDB;
    }


    //Get projects by project_id
    public List<Project> getProjectsByID(int user_id) {
        return projectRepositoryDB.getProjectsByID(user_id);
    }

    //Create project
    public void createProject(Project project, int user_id) {
        projectRepositoryDB.createProject(project, user_id);
    }

    //Edit proejct
    public void editProject(Project project, int project_id, int user_id) {
        projectRepositoryDB.editProject(project, project_id, user_id);
    }

    //Get project by project_id
    public Project getProjectByIDs(int project_id, int user_id) {
        return projectRepositoryDB.getProjectByIDs(project_id, user_id);
    }


}
