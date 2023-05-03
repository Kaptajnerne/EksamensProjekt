package com.example.eksamensprojekt_2sem.Controller;

import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Repository.RepositoryDB;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@RequestMapping(path = "")
@org.springframework.stereotype.Controller
public class Controller {

    private RepositoryDB repositoryDB;

    public Controller(RepositoryDB repositoryDB) {
        this.repositoryDB = repositoryDB;
    }
    //All Projects
    @GetMapping(path = "projects")
    public String showProjects(Model model) {
        List<Project> projects = repositoryDB.getProjects();
        model.addAttribute("projects", projects);
        return "projects";
    }
}
