package com.example.eksamensprojekt_2sem.Controller;

import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Model.Task;
import com.example.eksamensprojekt_2sem.Repository.RepositoryDB;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping(path="/editProject/{project_ID}")
    public String updateProject(@PathVariable("project_ID") int project_ID, @RequestParam("project_name") String project_name) {
        Project project = repositoryDB.getProject(project_ID);
        project.setProject_name(project_name);
        repositoryDB.editProject(project,project_ID);
        return "redirect:/projects/"+project_ID;
    }

    //delete project
    @GetMapping(path="/deleteProject/{project_id}")
    public String deleteProject (@PathVariable("project_id") int project_id,Model model){
        model.addAttribute("project_id", project_id);
        Project project = repositoryDB.getProject(project_id);
        model.addAttribute("project",project);
        return "deleteProject";
    }
    @PostMapping(path="/deleteProject/{project_id}")
    public String removeProject (@PathVariable("project_id") int project_id, Model model) {
        repositoryDB.deleteProject(project_id);
        return "redirect:/projects/";
    }
    @GetMapping("/tasks/delete/{taskId}")
    public String showDeleteTaskPage(@PathVariable("taskId") int taskId, Model model) {
        Task task = repositoryDB.getTask(taskId);
        model.addAttribute("task", task);
        return "deleteTask";
    }

    @PostMapping("/tasks/delete")
    public String deleteTask(@RequestParam("taskId") int taskId) {
        repositoryDB.deleteTask(taskId);
        return "redirect:/tasks";
    }
}
