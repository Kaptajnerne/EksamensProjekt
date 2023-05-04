package com.example.eksamensprojekt_2sem.Controller;

import com.example.eksamensprojekt_2sem.Model.Organization;
import com.example.eksamensprojekt_2sem.Model.Project;
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

    //Get projects from org
    //TODO:: Session timer from signin page doesn't translate over to projects. "session.setMaxInactiveInterval(10);"
    @GetMapping(path = "projects/{organization_id}")
    public String showProjects(Model model,@PathVariable int organization_id) {
        List<Project> projects = repositoryDB.getProjects(organization_id);
        model.addAttribute("projects", projects);
        return "projects";
    }

    //edit org
    @GetMapping(path = "/editOrganization/{organization_id}")
    public String showEditOrganization(@PathVariable("organization_id") int organization_id, Model model) {
        model.addAttribute("organization_id", organization_id);
        Organization organization = repositoryDB.getOrganizationFromId(organization_id);
        model.addAttribute("organization", organization);
        return "editOrganization";
    }

    @PostMapping(path = "/editOrganization/{organization_id}")
    public String editOrganization(@ModelAttribute Organization organization, @PathVariable int organization_id) {
        repositoryDB.editOrganization(organization, organization_id);
        return  "redirect:/home/"+ organization_id;
    }

}
