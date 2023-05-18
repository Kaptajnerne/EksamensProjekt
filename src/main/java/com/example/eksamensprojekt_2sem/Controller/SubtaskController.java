package com.example.eksamensprojekt_2sem.Controller;

import com.example.eksamensprojekt_2sem.Model.Subtask;
import com.example.eksamensprojekt_2sem.Service.ProjectService;
import com.example.eksamensprojekt_2sem.Service.SubtaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
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
        return "Subtask/subtasks";
    }

    @GetMapping(path = "subtasks/create/{task_id}")
    public String showCreateSubtask(Model model, @PathVariable("task_id") int task_id) {
        Subtask subtask = new Subtask();
        model.addAttribute("subtask", subtask);
        model.addAttribute("task_id", task_id);
        return "Subtask/createSubtask";
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
        return "Subtask/editSubtask";
    }

    //Edit subtask
    @PostMapping(path = "/subtask/{task_id}/edit/{subtask_id}")
    public String editSubtask(@PathVariable int subtask_id, @PathVariable int task_id, @ModelAttribute Subtask updatedSubtask) {
        Subtask existingSubtask = subtaskService.getSubtaskbyIDs(subtask_id, task_id);

        existingSubtask.setSubtask_name(updatedSubtask.getSubtask_name());
        existingSubtask.setHours(updatedSubtask.getHours());
        existingSubtask.setStatus(updatedSubtask.getStatus());

        // Check and update start_date if not null
        LocalDate updatedStartDate = updatedSubtask.getStart_date();
        if (updatedStartDate != null) {
            existingSubtask.setStart_date(updatedStartDate);
        }

        // Check and update end_date if not null
        LocalDate updatedEndDate = updatedSubtask.getEnd_date();
        if (updatedEndDate != null) {
            existingSubtask.setEnd_date(updatedEndDate);
        }

        subtaskService.editSubtask(existingSubtask, subtask_id, task_id);
        return "redirect:/subtasks/" + task_id;
    }


    @GetMapping(path = "subtasks/delete/{subtask_id}")
    public String showDeleteSubtask(Model model, @PathVariable("subtask_id") int subtask_id) {
        Subtask subtask = subtaskService.getSubtaskByID(subtask_id);
        model.addAttribute("subtask", subtask);
        return "Subtask/deleteSubtask";
    }

    @PostMapping(path = "subtasks/delete/{subtask_id}")
    public String deleteSubtask(@PathVariable("subtask_id") int subtask_id) {
        int task_id = subtaskService.getSubtaskByID(subtask_id).getTask_id();
        subtaskService.deleteSubtask(subtask_id);
        return "redirect:/subtasks/" + task_id;
    }

}
