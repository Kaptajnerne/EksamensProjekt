package com.example.eksamensprojekt_2sem.Controller;

import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Repository.RepositoryDB;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    private RepositoryDB repositoryDB;

    public Controller(RepositoryDB repositoryDB) {
        this.repositoryDB = repositoryDB;
    }

    @GetMapping(path = "")
    public String showProjects(Model model) {
        List<Project> projects = repositoryDB.getProjects();
        model.addAttribute("projects", projects);
        return "projects";
    }
}
