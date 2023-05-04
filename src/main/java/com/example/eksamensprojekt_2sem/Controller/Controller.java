package com.example.eksamensprojekt_2sem.Controller;

import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Repository.RepositoryDB;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@RequestMapping(path = "")
@org.springframework.stereotype.Controller
public class Controller {

    private RepositoryDB repositoryDB;

    public Controller(RepositoryDB repositoryDB) {
        this.repositoryDB = repositoryDB;
    }
   /* @GetMapping(path = "")
    public String startPage (Model model){
        List<Project> projects = repositoryDB.getProjects();
        model.addAttribute("projects", projects);

        return "projects";
    }
    */


    //Get projects from org
    //TODO:: Session timer from signin page doesn't translate over to projects. "session.setMaxInactiveInterval(10);"
    @GetMapping(path = "projects/{organization_id}")
    public String showProjects(Model model,@PathVariable int organization_id) {
        List<Project> projects = repositoryDB.getProjects(organization_id);
        model.addAttribute("projects", projects);
        return "projects";
    }

    //edit project
    @GetMapping(path="/editProject/{project_ID}")
    public String editProject(@PathVariable("project_ID") int project_ID, Model model) {
        model.addAttribute("project_id", project_ID);
        Project project = repositoryDB.getProject(project_ID);
        model.addAttribute("project", project);
        return "editProject";
    }
}
