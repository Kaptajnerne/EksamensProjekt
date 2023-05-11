package com.example.eksamensprojekt_2sem.Controller;

import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Model.Task;
import com.example.eksamensprojekt_2sem.Repository.TaskRepository;
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
    @GetMapping(path = "tasks/{project_id}")
    public String showTasks(Model model, @PathVariable int project_id, HttpSession session) {
        if(session.getAttribute("project") != null) {
            List<Task> tasks = taskService.getTaskByProID(project_id);
            model.addAttribute("tasks", tasks);
            return "tasks";
        } else {
            return "redirect://";
        }
    }

    @GetMapping(path = "task/create/{project_id}")
    public String showCreateTask(Model model, @PathVariable("project_id") int project_id, HttpSession session) {
        Task task = new Task();
        if(session.getAttribute("project") != null) {
            model.addAttribute("tsk", task);
            model.addAttribute("project_id", project_id);
            Project project = (Project) session.getAttribute("project");
            return "createTask";

        } else {
            return "redirect://";
        }
    }

    //Add employee
    @PostMapping(path = "task/create/{project_id}")
    public String createTask(@ModelAttribute("task") Task task, @PathVariable("project_id") int project_id, HttpSession session) {
        if(session.getAttribute("organization") != null) {
            taskService.createTask(task, project_id);
            return "redirect:/task/" + project_id;
        } else {
            return "redirect://";
        }
    }



}
