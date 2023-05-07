package com.example.eksamensprojekt_2sem.Controller;

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
    public String showEmployees(Model model, @PathVariable int organization_id, HttpSession session) {

        if(session.getAttribute("organization") != null) {
            List<Employee> employees = repositoryDB.getEmployeesByOrgID(organization_id);
            model.addAttribute("employees", employees);
            return "employees";
        } else {
            return "redirect://";
        }
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


    //Edit employee page
    @GetMapping(path = "employees/edit/{employee_id}")
    public String showEditEmployee(@PathVariable int employee_id, Model model, HttpSession session) {

        if (session.getAttribute("organization") != null) {
            Employee employee = repositoryDB.getEmployeeByID(employee_id);
            model.addAttribute("employee", employee);
            model.addAttribute("employee_id", employee_id);
            return "editEmployee";
        } else {
            return "redirect://";
        }
    }


    //Edit employee
    @PostMapping(path = "employees/edit/{employee_id}")
    public String editEmployee(@ModelAttribute Employee employee, @PathVariable int employee_id, HttpSession session) {

        if (session.getAttribute("organization") != null) {
            repositoryDB.editEmployee(employee, employee_id);

            int organization_id = repositoryDB.findOrganizationID(employee_id);
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
