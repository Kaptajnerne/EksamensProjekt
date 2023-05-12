package com.example.eksamensprojekt_2sem.Controller;

import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Model.Task;
import com.example.eksamensprojekt_2sem.Service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TaskController {
    TaskService taskService;
    public TaskController(TaskService taskService){
        this.taskService=taskService;
    }

    //get task by project_id
    @GetMapping(path = "tasks/{project_id}")
    public String showTasks(Model model, @PathVariable int project_id, HttpSession session) {
        List<Task> tasks = taskService.getTaskByProID(project_id);
        model.addAttribute("tasks", tasks);
        return "tasks";
       /* if(session.getAttribute("project") != null) {
        } else {
            return "redirect://";
        }*/
    }

    //create task page
    @GetMapping(path = "task/create/{project_id}")
    public String showCreateTask(Model model, @PathVariable("project_id") int project_id, HttpSession session) {
        Task task = new Task();
        model.addAttribute("task", task);
        model.addAttribute("project_id", project_id);
        return "createTask";

        /*if(session.getAttribute("project") != null) {
            Project project = (Project) session.getAttribute("project");
        } else {
            return "redirect://";
        }*/
    }

    @PostMapping(path = "task/create/{project_id}")
    public String createTask(@ModelAttribute("task") Task task, @PathVariable("project_id") int project_id, HttpSession session) {
        taskService.createTask(task, project_id);
        return "redirect:/tasks/" + project_id;

       /* if(session.getAttribute("user") != null) {

        } else {
            return "redirect://";
        }*/
    }
    //Edit project page
    @GetMapping(path = "/tasks/{project_id}/edit/{task_id}")
    public String showEditTask(Model model , @PathVariable int task_id, @PathVariable int project_id) {
        Task task = taskService.getTaskByIDs(task_id, project_id);
        model.addAttribute("task", task);
        model.addAttribute("task_id", task_id);
        model.addAttribute("project_id", project_id);
        return "editTask";
    }

    //Edit project
    @PostMapping(path = "/tasks/{project_id}/edit/{task_id}")
    public String editTask(@PathVariable int task_id, @PathVariable int project_id, @ModelAttribute Task task) {
        //Task task = taskService.getTaskByIDs(, task_id, project_id);
        taskService.editTask(task, task_id, project_id);
        return "redirect:/tasks/" + project_id;
    }



}
