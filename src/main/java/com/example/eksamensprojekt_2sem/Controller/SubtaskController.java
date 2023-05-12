package com.example.eksamensprojekt_2sem.Controller;

import com.example.eksamensprojekt_2sem.Model.Subtask;
import com.example.eksamensprojekt_2sem.Model.Task;
import com.example.eksamensprojekt_2sem.Service.ProjectService;
import com.example.eksamensprojekt_2sem.Service.SubtaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SubtaskController {

    private SubtaskService subtaskService;
    private ProjectService projectService;

    public SubtaskController(SubtaskService subtaskService, ProjectService projectService) {
        this.subtaskService = subtaskService;
        this.projectService = projectService;
    }


    @GetMapping(path = "subtasks/{task_id}")
    public String showSubtasks(Model model, @PathVariable int task_id) {
        List<Subtask> subtasks = subtaskService.getSubtasksByTaskID(task_id);
        int project_id = projectService.getProjectID(task_id);
        model.addAttribute("subtasks", subtasks);
        model.addAttribute("project_id", project_id);
        return "subtasks";
    }

    @GetMapping(path = "subtasks/create/{task_id}")
    public String showCreateSubtask(Model model, @PathVariable("task_id") int task_id) {
        Subtask subtask = new Subtask();
        model.addAttribute("subtask", subtask);
        model.addAttribute("task_id", task_id);
        return "createSubtask";
    }

    @PostMapping(path = "subtasks/create/{task_id}")
    public String createSubtask(@ModelAttribute("subtask") Subtask subtask, @PathVariable("task_id") int task_id) {
        subtaskService.createSubtask(subtask, task_id);
        return "redirect:/subtasks/" + task_id;

    }

    //Edit subtask page
    @GetMapping(path = "/subtask/{task_id}/edit/{subtask_id}")
    public String showEditSubtask(Model model , @PathVariable int subtask_id, @PathVariable int task_id) {
        Subtask subtask = subtaskService.getSubtaskbyIDs(subtask_id, task_id);
        model.addAttribute("subtask", subtask);
        model.addAttribute("subtask_id", subtask_id);
        model.addAttribute("task_id", task_id);
        return "editSubtask";
    }

    //Edit subtask
    @PostMapping(path = "/subtask/{task_id}/edit/{subtask_id}")
    public String editSubtask(@PathVariable int subtask_id, @PathVariable int task_id, @ModelAttribute Subtask subtask) {
        //Task task = taskService.getTaskByIDs(, task_id, project_id);
        subtaskService.editSubtask(subtask, subtask_id, task_id);
        return "redirect:/subtasks/" + task_id;
    }
}
