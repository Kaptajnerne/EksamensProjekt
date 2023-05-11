package com.example.eksamensprojekt_2sem.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "")
@org.springframework.stereotype.Controller
public class EmployeeController {


    //---------------------------------EMPLOYEE ENDPOINTS-------------------------------------//

    /*
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

        if(session.getAttribute("organization") != null) {
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
            repositoryDB.createEmployee(employee, organization_id);
            return "redirect:/employees/" + organization_id;
        } else {
            return "redirect://";
        }
    }


    // Edit employee page
    @GetMapping(path = "organizations/{organization_id}/employees/{employee_id}/edit")
    public String showEditEmployee(@PathVariable int organization_id, @PathVariable int employee_id, Model model, HttpSession session) {

        if (session.getAttribute("organization") != null) {
            Employee employee = repositoryDB.getEmployeeByIDs(organization_id, employee_id);
            model.addAttribute("employee", employee);
            model.addAttribute("organization_id", organization_id);
            model.addAttribute("employee_id", employee_id);
            return "editEmployee";
        } else {
            return "redirect://";
        }
    }


    // Edit employee
    @PostMapping(path = "organizations/{organization_id}/employees/{employee_id}/edit")
    public String editEmployee(@ModelAttribute Employee employee, @PathVariable int organization_id, @PathVariable int employee_id, HttpSession session) {

        if (session.getAttribute("organization") != null) {
            repositoryDB.editEmployee(employee, organization_id, employee_id);

            return "redirect:/employees/" + organization_id;
        } else {
            return "redirect://";
        }
    }

     */
}
