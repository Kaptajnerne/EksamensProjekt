package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.Model.Organization;
import com.example.eksamensprojekt_2sem.Model.Project;

import java.util.List;

public interface IRepository {

    public Organization signIn(String organization_name, String password);

    public List<Project> getProjects();
}
