package com.example.eksamensprojekt_2sem.Controller;

import com.example.eksamensprojekt_2sem.Model.Subtask;
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

    public SubtaskController(SubtaskService subtaskService) {
        this.subtaskService = subtaskService;
    }


    @GetMapping(path = "subtasks/{task_id}")
    public String showSubtasks(Model model, @PathVariable int task_id) {
        List<Subtask> subtasks = subtaskService.getSubtasksByTaskID(task_id);
        model.addAttribute("subtasks", subtasks);
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
}
