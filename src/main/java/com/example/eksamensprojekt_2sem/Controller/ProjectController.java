package com.example.eksamensprojekt_2sem.Controller;

import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Model.Task;
import com.example.eksamensprojekt_2sem.Service.ProjectService;
import com.example.eksamensprojekt_2sem.Service.TaskService;
import com.example.eksamensprojekt_2sem.Service.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "")
@org.springframework.stereotype.Controller
public class ProjectController {

    private ProjectService projectService;
    private UserService userService;
    TaskService taskService;

    public ProjectController(ProjectService projectService, UserService userService, TaskService taskService) {
        this.projectService = projectService;
        this.userService = userService;
        this.taskService = taskService;
    }

    //---------------------------------PROJECT ENDPOINTS--------------------------------------//

    //Get projects from org
    //TODO:: Session timer from signin page doesn't translate over to projects. "session.setMaxInactiveInterval(10);"
    @GetMapping(path = "projects/{user_id}")
    public String showProjects(Model model, @PathVariable int user_id) {
        List<Project> projects = projectService.getProjectsByID(user_id);

        for (Project project : projects) {
            int project_id = project.getProject_id();

            List<Task> tasks = taskService.getTaskByProID(project_id);

            double projectCalculatedTime = 0; // Initialize the calculated time for each project

            for (Task task : tasks) {
                double taskCalculatedTime = taskService.getTaskCalculatedTime(task.getTask_id());
                task.setCalculatedTime(taskCalculatedTime);

                projectCalculatedTime += taskCalculatedTime; // Accumulate the calculated time for each task
            }

            project.setTasks(tasks);
            project.setProjectCalculatedTime(projectCalculatedTime);
        }

        model.addAttribute("projects", projects);


        return "Project/projects";
    }




    //Create project page
    @GetMapping(path = "projects/create/{user_id}")
    public String showCreateProject(Model model, @PathVariable int user_id) {
        Project project = new Project();
        model.addAttribute("project", project);
        model.addAttribute("user_id", user_id);
        return "Project/createProject";
    }

    //Create project
    @PostMapping(path = "projects/create/{user_id}")
    public String createProject(@ModelAttribute("project") Project project, @PathVariable int user_id) {
        projectService.createProject(project, user_id);
        return "redirect:/projects/" + user_id;
    }


    //Edit project page
    @GetMapping(path = "/projects/{user_id}/edit/{project_id}")
    public String showEditProject(Model model, @PathVariable int project_id, @PathVariable int user_id) {
        Project project = projectService.getProjectByIDs(project_id, user_id);
        model.addAttribute("project", project);
        model.addAttribute("project_id", project_id);
        model.addAttribute("user_id", user_id);
        return "Project/editProject";
    }

    //Edit project
    @PostMapping(path = "/projects/{user_id}/edit/{project_id}")
    public String editProject(@PathVariable int project_id, @PathVariable int user_id, @ModelAttribute Project project) {
        //Project project = projectService.getProjectByIDs(project_id, user_id);
        projectService.editProject(project, project_id, user_id);
        return "redirect:/projects/" + user_id;
    }

    //delete project
    @GetMapping(path = "/deleteProject/{project_id}")
    public String deleteProject(@PathVariable("project_id") int project_id, Model model) {
        model.addAttribute("project_id", project_id);
        Project project = projectService.getProjectByProjectID(project_id);
        model.addAttribute("project", project);
        return "Project/deleteProject";
    }

    @PostMapping(path = "/deleteProject/{project_id}")
    public String removeProject(@PathVariable("project_id") int project_id, Model model) {
        int user_id = userService.getUserID(project_id);
        projectService.deleteProject(project_id);
        return "redirect:/projects/" + user_id;
    }
}
/*
    @GetMapping("/tasks/delete/{task_id}")
    public String showDeleteTaskPage(@PathVariable("task_id") int task_id, Model model) {
        Task task = TaskService.getTask(task_id);
        model.addAttribute("task", task);
        return "deleteTask";
    }

    @PostMapping("/tasks/delete/{task_id}")
    public String deleteTask(@RequestParam("taskId") int taskId) {
        TaskSerive.deleteTask(taskId);
        return "redirect:/projects";
    }

    @GetMapping(path = "/subtask/delete/{task_id}")
    public String deleteSubtask (@PathVariable("task_id") int task_id, Model model){
        Task task = SubtaskService.getTask(task_id);
        model.addAttribute("task",task);
        return "deleteSubtask";
    }

    @PostMapping(path = "/subtask/delete/{task_id}")
    public String removeSubtask(@RequestParam("task_id") int task_id){
        SubtaskService.deleteSubtask(task_id);
        return "redirect:/projects";
    }
}
*/
