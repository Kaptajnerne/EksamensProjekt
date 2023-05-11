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

    //Create project page
    @GetMapping(path = "projects/create/{user_id}")
    public String showCreateProject(Model model, @PathVariable int user_id) {
        Project project = new Project();
        model.addAttribute("project", project);
        model.addAttribute("user_id", user_id);
        return "createProject";
    }

    //Create project
    @PostMapping(path = "projects/create/{user_id}")
    public String createProject(@ModelAttribute("project") Project project, @PathVariable int user_id) {
        projectService.createProject(project, user_id);
        return "redirect:/projects/" + user_id;
    }



    //Edit project page
    @GetMapping(path = "/projects/{user_id}/edit/{project_id}")
    public String showEditProject(Model model , @PathVariable int project_id, @PathVariable int user_id) {
        Project project = projectService.getProjectByIDs(project_id, user_id);
        model.addAttribute("project", project);
        model.addAttribute("project_id", project_id);
        model.addAttribute("user_id", user_id);
        return "editProject";
    }

    //Edit project
    @PostMapping(path = "/projects/{user_id}/edit/{project_id}")
    public String editProject(@PathVariable int project_id, @PathVariable int user_id, @ModelAttribute Project project) {
        //Project project = projectService.getProjectByIDs(project_id, user_id);
        projectService.editProject(project, project_id, user_id);
        return "redirect:/projects/" + user_id;
    }

}
