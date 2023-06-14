package com.example.eksamensprojekt_2sem.Controller;

import com.example.eksamensprojekt_2sem.DTO.TaskSubtaskDTO;
import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Model.Subtask;
import com.example.eksamensprojekt_2sem.Model.Task;
import com.example.eksamensprojekt_2sem.Service.ProjectService;
import com.example.eksamensprojekt_2sem.Service.SubtaskService;
import com.example.eksamensprojekt_2sem.Service.TaskService;
import com.example.eksamensprojekt_2sem.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.webservices.client.HttpWebServiceMessageSenderBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TaskController {

    TaskService taskService;
    ProjectService projectService;
    UserService userService;

    public TaskController(TaskService taskService, UserService userService, ProjectService projectService){
        this.taskService=taskService;
        this.userService = userService;
        this.projectService = projectService;
    }

    public boolean isSignedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    //Get tasks
    @GetMapping(path = "tasks/{project_id}")
    public String showTasks(Model model, @PathVariable int project_id, HttpSession session) {
        if(isSignedIn(session)) {
            List<Task> tasks = taskService.getTaskByProID(project_id);
            int user_id = userService.getUserID(project_id);

            //Calculated time for task + subtask
            double projectCalculatedTime = projectService.getProjectCalculatedTime(project_id);
            for (Task task : tasks) {
                double taskCalculatedTime = taskService.getTaskCalculatedTime(task.getTask_id());
                task.setCalculatedTime(taskCalculatedTime);
            }

            model.addAttribute("tasks", tasks);
            model.addAttribute("user_id", user_id);
            model.addAttribute("projectCalculatedTime", projectCalculatedTime);
            return "Task/tasks";
        }
        return "redirect:/sessionTimeout";
    }


    //Create task page
    @GetMapping(path = "task/create/{project_id}")
    public String showCreateTask(Model model, @PathVariable("project_id") int project_id, HttpSession session) {

        if (isSignedIn(session)) {
            Task task = new Task();
            Project project = projectService.getProjectByProjectID(project_id);

            model.addAttribute("task", task);
            model.addAttribute("project_id", project_id);
            model.addAttribute("project_start_date", project.getStart_date());
            model.addAttribute("project_end_date", project.getEnd_date());
            return "Task/createTask";
        }
        return "redirect:/sessionTimeout";
    }

    //Create task
    @PostMapping(path = "task/create/{project_id}")
    public String createTask(@ModelAttribute("task") Task task, @PathVariable("project_id") int project_id, HttpSession session) {
        if (isSignedIn(session)) {
            taskService.createTask(task, project_id);
            return "redirect:/tasks/" + project_id;
        }
        return "redirect:/sessionTimeout";
    }


    //Edit task page
    @GetMapping(path = "/task/{project_id}/edit/{task_id}")
    public String showEditTask(Model model , @PathVariable int task_id, @PathVariable int project_id, HttpSession session) {
        if (isSignedIn(session)) {
            Task task = taskService.getTaskByIDs(task_id, project_id);
            Project project = projectService.getProjectByProjectID(project_id);

            model.addAttribute("task", task);
            model.addAttribute("task_id", task_id);
            model.addAttribute("project_id", project_id);
            model.addAttribute("project_start_date", project.getStart_date());
            model.addAttribute("project_end_date", project.getEnd_date());
            return "Task/editTask";
        }
        return "redirect:/sessionTimeout";
    }

    //Edit task
    @PostMapping(path = "/task/{project_id}/edit/{task_id}")
    public String editTask(@PathVariable int task_id, @PathVariable int project_id, @ModelAttribute Task updatedTask, HttpSession session) {
        if (isSignedIn(session)) {
            Task existingTask = taskService.getTaskByIDs(task_id, project_id);

            existingTask.setTask_name(updatedTask.getTask_name());
            existingTask.setHours(updatedTask.getHours());
            existingTask.setStatus(updatedTask.getStatus());

            // Check and update start_date if not null
            LocalDate updatedStartDate = updatedTask.getStart_date();
            if (updatedStartDate != null) {
                existingTask.setStart_date(updatedStartDate);
            }

            // Check and update end_date if not null
            LocalDate updatedEndDate = updatedTask.getEnd_date();
            if (updatedEndDate != null) {
                existingTask.setEnd_date(updatedEndDate);
            }

            taskService.editTask(existingTask, task_id, project_id);
            return "redirect:/tasks/" + project_id;
        }
        return "redirect:/sessionTimeout";
    }


    //Delete task page
    @GetMapping(path = "task/delete/{task_id}")
    public String deleteTask(@PathVariable("task_id") int task_id, Model model, HttpSession session) {
        if (isSignedIn(session)) {
            Task task = taskService.getTaskById(task_id);

            model.addAttribute("task_id", task_id);
            model.addAttribute("task", task);
            return "Task/deleteTask";
        }
        return "redirect:/sessionTimeout";
    }

    //Delete task
    @PostMapping(path = "task/delete/{task_id}")
    public String removeTask(@PathVariable("task_id") int task_id, Model model, HttpSession session) {
        if (isSignedIn(session)) {
            int projectID = taskService.getProIDbyTaskID(task_id);
            taskService.deleteTask(task_id);
            return "redirect:/tasks/" + projectID;
        }
        return "redirect:/sessionTimeout";
    }



    //get project with task and subtasks
    @GetMapping(path="project/{project_id}")
    public String showProject(Model model, @PathVariable int project_id, HttpSession session) {

        if (isSignedIn(session)) {
            List<TaskSubtaskDTO> taskSubtasks = taskService.getTaskSubtasksByProID(project_id);
            double projectCalculatedTime = projectService.getProjectCalculatedTime(project_id);

            int user_id = userService.getUserID(project_id);

            double taskCalculatedTime1 = projectService.getProjectCalculatedTime(project_id);
            for (TaskSubtaskDTO task : taskSubtasks) {
                double taskCalculatedTime2 = taskService.getTaskCalculatedTime(task.getId());
                task.setCalculatedTime(taskCalculatedTime2);
            }

        model.addAttribute("taskSubtask", taskSubtasks);
        model.addAttribute("projectCalculatedTime", projectCalculatedTime);
        model.addAttribute("taskCalculatedTime", taskCalculatedTime1);
        model.addAttribute("user_id", user_id);

            return "Task/taskSubtask";
        }
        return "redirect:/sessionTimeout";
    }
}