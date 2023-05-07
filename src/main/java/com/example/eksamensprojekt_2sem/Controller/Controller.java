package com.example.eksamensprojekt_2sem.Controller;

import com.example.eksamensprojekt_2sem.Model.Organization;
import com.example.eksamensprojekt_2sem.Model.Employee;
import com.example.eksamensprojekt_2sem.Model.Organization;
import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Repository.RepositoryDB;
import jakarta.servlet.http.HttpSession;
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

    //---------------------------------ORGANIZATION ENDPOINTS---------------------------------//


    //---------------------------------EMPLOYEE ENDPOINTS-------------------------------------//

    // Get employees from org
    @GetMapping(path = "employees/{organization_id}")
    public String showEmployees(Model model, @PathVariable int organization_id) {
        List<Employee> employees = repositoryDB.getEmployeesByID(organization_id);
        model.addAttribute("employees", employees);
        return "employees";
    }

    //Add employee page
    @GetMapping(path = "employees/create/{organization_id}")
    public String showCreateEmployee(Model model, @PathVariable("organization_id") int organization_id, HttpSession session) {
        Employee employee = new Employee();

        if( session.getAttribute("organization") != null) {
            model.addAttribute("emp", employee);
            model.addAttribute("organization_id", organization_id);
            Organization org = (Organization) session.getAttribute("organization");
            return "createEmployee";

        } else {
            return "redirect://";
        }
    }

    //Add employee
    @PostMapping(path = "employees/create/{organization_id}")
    public String createEmployee(@ModelAttribute("employee") Employee employee, @PathVariable("organization_id") int organization_id, HttpSession session) {

        if(session.getAttribute("organization") != null) {
            Organization org = (Organization) session.getAttribute("organization");
            repositoryDB.createEmployee(employee, organization_id);
            return "redirect:/employees/" + organization_id;
        } else {
            return "redirect://";
        }

    }




    //---------------------------------PROJECT ENDPOINTS--------------------------------------//

    //Get projects from org
    //TODO:: Session timer from signin page doesn't translate over to projects. "session.setMaxInactiveInterval(10);"
    @GetMapping(path = "projects/{organization_id}")
    public String showProjects(Model model, @PathVariable int organization_id) {
        List<Project> projects = repositoryDB.getProjectsByID(organization_id);
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
    public String editOrganization(@PathVariable("organization_id") int organization_id, @RequestParam("organization_name")  String organization_name, String password) {
        Organization organization = repositoryDB.getOrganizationFromId(organization_id);
        organization.setOrganization_name(organization_name);
        organization.setPassword(password);
        repositoryDB.editOrganization(organization,organization_id);
        return  "redirect:/home/"+ organization_id;


    }


    //edit project page
    //TODO:: Why is project_ID in the parameter. Change names to lowercase, to increase code consistency
    @GetMapping(path = "/editProject/{project_ID}")
    public String editProject(@PathVariable("project_ID") int project_ID, Model model) {
        model.addAttribute("project_id", project_ID);
        Project project = repositoryDB.getProject(project_ID);
        model.addAttribute("project", project);
        return "editProject";
    }

    //edit project
    //TODO:: Why is project_ID in the parameter. Change names to lowercase, to increase code consistency
    @PostMapping(path = "/editProject/{project_ID}")
    public String updateProject(@PathVariable("project_ID") int project_ID, @RequestParam("project_name") String project_name) {
        Project project = repositoryDB.getProject(project_ID);
        project.setProject_name(project_name);
        repositoryDB.editProject(project, project_ID);
        return "redirect:/projects/" + project_ID;
    }


    //---------------------------------TASK ENDPOINTS-----------------------------------------//

    //---------------------------------SUBTASK ENDPOINTS--------------------------------------//

}
