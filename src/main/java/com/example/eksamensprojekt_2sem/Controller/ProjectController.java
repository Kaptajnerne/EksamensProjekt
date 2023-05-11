package com.example.eksamensprojekt_2sem.Controller;

import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Repository.ProjectRepositoryDB;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "")
@org.springframework.stereotype.Controller
public class ProjectController {

    private ProjectRepositoryDB projectRepositoryDB;

    public ProjectController(ProjectRepositoryDB projectRepositoryDB) {
        this.projectRepositoryDB = projectRepositoryDB;
    }

    //---------------------------------PROJECT ENDPOINTS--------------------------------------//

    //Get projects from org
    //TODO:: Session timer from signin page doesn't translate over to projects. "session.setMaxInactiveInterval(10);"
    @GetMapping(path = "projects/{organization_id}")
    public String showProjects(Model model, @PathVariable int organization_id) {
        List<Project> projects = projectRepositoryDB.getProjectsByID(organization_id);
        model.addAttribute("projects", projects);
        return "projects";
    }


    //edit project page
    //TODO:: Why is project_ID in the parameter. Change names to lowercase, to increase code consistency
    @GetMapping(path = "/editProject/{project_ID}")
    public String editProject(@PathVariable("project_ID") int project_ID, Model model) {
        model.addAttribute("project_id", project_ID);
        Project project = projectRepositoryDB.getProject(project_ID);
        model.addAttribute("project", project);
        return "editProject";
    }

    //edit project
    //TODO:: Why is project_ID in the parameter. Change names to lowercase, to increase code consistency
    @PostMapping(path = "/editProject/{project_ID}")
    public String updateProject(@PathVariable("project_ID") int project_ID, @RequestParam("project_name") String project_name) {
        Project project = projectRepositoryDB.getProject(project_ID);
        project.setProject_name(project_name);
        projectRepositoryDB.editProject(project, project_ID);
        return "redirect:/projects/" + project_ID;
    }

}
