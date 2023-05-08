package com.example.eksamensprojekt_2sem.Controller;

import com.example.eksamensprojekt_2sem.Model.Employee;
import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Repository.IRepository;
import com.example.eksamensprojekt_2sem.Repository.RepositoryDB;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequestMapping(path = "")
@org.springframework.stereotype.Controller
public class Controller {

    private RepositoryDB repositoryDB;

    public Controller(RepositoryDB repositoryDB) {
        this.repositoryDB = repositoryDB;
    }

    //---------------------------------ORGANIZATION JDBC METHODS---------------------------------//


    //---------------------------------EMPLOYEE JDBC METHODS-------------------------------------//


    //---------------------------------PROJECT JDBC METHODS--------------------------------------//
    //Get projects from org
    //TODO:: Session timer from signin page doesn't translate over to projects. "session.setMaxInactiveInterval(10);"
    @GetMapping(path = "projects/{organization_id}")
    public String showProjects(Model model,@PathVariable int organization_id) {
        List<Project> projects = repositoryDB.getProjects(organization_id);
        model.addAttribute("projects", projects);
        return "projects";
    }


    //Show create project
    //TODO:: Be able to write a employee to project
    @GetMapping(path = "projects/{organization_id}/create")
    public String showCreateProject(Model model, @PathVariable("organization_id") int organization_id) {
        Project project = new Project();
        List<Employee> employees = repositoryDB.getEmployeesByOrgID(organization_id);
        model.addAttribute("project", project);
        model.addAttribute("employees",employees);
        model.addAttribute("organization_id", organization_id);
        return "createProject";
    }

    //Create Project
   @PostMapping(path = "projects/{organization_id}/create")
    public String createProject(@ModelAttribute("project") Project project, @PathVariable("organization_id") int organization_id) {


       List<Integer> employeeIds = project.getEmployee_id();
       if (employeeIds == null) {
           employeeIds = new ArrayList<>();
       }
        repositoryDB.addProject(project, organization_id);
        return "redirect:/projects/" + organization_id;
    }

   /* @PostMapping(path = "projects/{organization_id}/addProject")
    public String addProject(@ModelAttribute("project") Project project, @PathVariable("organization_id") int organization_id) {

        List<Integer> employeeIds = Arrays.asList(project.getEmployee_id());  // Convert single employee ID to a list
        project.setEmployee_id(employeeIds);
        repositoryDB.addProject(project, organization_id);*/

    //---------------------------------TASK JDBC METHODS-----------------------------------------//


    //---------------------------------SUBTASK JDBC METHODS--------------------------------------//
}
