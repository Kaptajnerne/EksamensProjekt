package com.example.eksamensprojekt_2sem.Controller;

import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Repository.ProjectRepositoryDB;
import com.example.eksamensprojekt_2sem.Service.ProjectService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "")
@org.springframework.stereotype.Controller
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    //---------------------------------PROJECT ENDPOINTS--------------------------------------//

    //Get projects from org
    //TODO:: Session timer from signin page doesn't translate over to projects. "session.setMaxInactiveInterval(10);"
    @GetMapping(path = "projects/{user_id}")
    public String showProjects(Model model, @PathVariable int user_id) {
        List<Project> projects = projectService.getProjectsByID(user_id);
        model.addAttribute("projects", projects);
        return "projects";
    }



   /* //edit project page
    //TODO:: Why is project_ID in the parameter. Change names to lowercase, to increase code consistency
    @GetMapping(path = "/editProject/{project_ID}")
    public String editProject(@PathVariable("project_ID") int project_ID, Model model) {
        model.addAttribute("project_id", project_ID);
        Project project = projectService.getProject(project_ID);
        model.addAttribute("project", project);
        return "editProject";
    }

    //edit project
    //TODO:: Why is project_ID in the parameter. Change names to lowercase, to increase code consistency
    @PostMapping(path = "/editProject/{project_ID}")
    public String updateProject(@PathVariable("project_ID") int project_ID, @RequestParam("project_name") String project_name) {
        Project project = projectService.getProject(project_ID);
        project.setProject_name(project_name);
        projectService.editProject(project, project_ID);
        return "redirect:/projects/" + project_ID;
    }*/

}
