package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.Model.Organization;
import com.example.eksamensprojekt_2sem.Model.Project;

import java.util.List;

public interface IRepository {

    //Sign in with user
    public Organization signIn(String organization_name, String password);

    //Get projects from org
    public List<Project> getProjects(int organization_id);
}
