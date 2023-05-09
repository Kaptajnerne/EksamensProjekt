package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.DTO.ProjectEmployeeForm;
import com.example.eksamensprojekt_2sem.Model.Employee;
import com.example.eksamensprojekt_2sem.Model.Organization;
import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Util.ConnectionManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RepositoryDB implements IRepository {

    @Value("jdbc:mysql://localhost:3306/pct_db")
    private String db_url;

    @Value("root")
    private String uid;

    @Value("1234")
    private String pwd;

    //---------------------------------ORGANIZATION JDBC METHODS---------------------------------//

   /* //Login 2
    public void signIn2(HttpServletRequest request, HttpServletResponse response) {

        String organization_name = request.getParameter("organization_name");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = null;

        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM organization WHERE organization_name = ? AND password = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, organization_name);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                session.setAttribute("organization_id", rs.getInt("organization_id"));
                dispatcher = request.getRequestDispatcher("home");
            } else {
                request.setAttribute("status", "failed");
                dispatcher = request.getRequestDispatcher("index");
            }
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    //Sign in with user
    public Organization signIn(String organization_name, String password) {
        Organization organization = null;

        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM organization WHERE organization_name = ? AND password = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, organization_name);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int organization_id = rs.getInt("organization_id");
                organization = new Organization(organization_id, organization_name, password);
            }
            return organization;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //edit organization
    public void editOrganization(Organization organization, int organization_id) {
        try {
            Connection conn = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "UPDATE organization SET organization_name = ?, password = ? WHERE organization_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(SQL)) {
                stmt.setString(1, organization.getOrganization_name());
                stmt.setString(2, organization.getPassword());
                stmt.setInt(3, organization_id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    public Organization getOrganizationFromId(int organization_id) {
        try {
            Connection conn = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM organization WHERE organization_id = ?;";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, organization_id);
            ResultSet rs = ps.executeQuery();
            Organization organization1 = null;
            if (rs.next()) {
                String organization_name = rs.getString("ORGANIZATION_NAME");
                String password = rs.getString("PASSWORD");
                organization1 = new Organization(organization_id, organization_name, password);
            }
            return organization1;
        } catch (SQLException ex) {
            return null;
        }
    }


    //Sign up user
    public void signUp(Organization organization) {
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "INSERT INTO organization (organization_name, password) VALUES (?, ?)";
            PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, organization.getOrganization_name());
            pstmt.setString(2, organization.getPassword());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                int organization_id = rs.getInt(1);
                organization.setOrganization_id(organization_id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findOrganizationID(int employee_id) {
        int organization_id = 0;
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT organization_id from employee WHERE employee_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, employee_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                organization_id = rs.getInt("organization_id");
            }
            return organization_id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //---------------------------------EMPLOYEE JDBC METHODS-------------------------------------//

    //Get employee by employee_id
    public Employee getEmployee(int employee_id) {
        Employee employee = null;
        try {

            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM employee WHERE employee_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, employee_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String employee_firstname = rs.getString("employee_firstname");
                String employee_lastname = rs.getString("employee_lastname");
                String email = rs.getString("email");
                int organization_id = rs.getInt("organization_id");

                employee = new Employee(employee_id, employee_firstname, employee_lastname, email, organization_id);
            }
            return employee;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //Get all employees from org
    public List<Employee> getEmployeesByOrgID(int organization_id) {
        List<Employee> employees = new ArrayList<>();
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM employee WHERE organization_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, organization_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int employee_id = rs.getInt("employee_id");
                String employee_firstname = rs.getString("employee_firstname");
                String employee_lastname = rs.getString("employee_lastname");
                String email = rs.getString("email");

                employees.add(new Employee(employee_id, employee_firstname, employee_lastname, email, organization_id));
            }
            return employees;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Get one employee by org and emp id
    public Employee getEmployeeByIDs(int organization_id, int employee_id) {
        Employee employee = null;
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM employee WHERE organization_id = ? AND employee_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, organization_id);
            pstmt.setInt(2, employee_id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String employee_firstname = rs.getString("employee_firstname");
                String employee_lastname = rs.getString("employee_lastname");
                String email = rs.getString("email");

                employee = new Employee(employee_id, employee_firstname, employee_lastname, email, organization_id);
            }
            return employee;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //Create employee and add to organization
    public Employee createEmployee(Employee employee, int organization_id) {
        Employee emp = null;
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "INSERT INTO employee (employee_firstname, employee_lastname, email, organization_id) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, employee.getFirst_name());
            pstmt.setString(2, employee.getLast_name());
            pstmt.setString(3, employee.getEmail());
            pstmt.setInt(4, organization_id);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                int employee_id = rs.getInt(1);
                emp = new Employee(employee_id, employee.getFirst_name(), employee.getLast_name(), employee.getEmail(), organization_id);
            }
            return emp;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Edit employee
    public void editEmployee(Employee employee, int organization_id, int employee_id) {
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "UPDATE employee SET employee_firstname = ?, employee_lastname = ?, email = ? WHERE organization_id = ? AND employee_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, employee.getFirst_name());
            pstmt.setString(2, employee.getLast_name());
            pstmt.setString(3, employee.getEmail());
            pstmt.setInt(4, organization_id);
            pstmt.setInt(5, employee_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //---------------------------------PROJECT JDBC METHODS--------------------------------------//

    //Get projects from org_id
    public List<Project> getProjectsByOrgID(int organization_id) {
        List<Project> projects = new ArrayList<>();
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL1 = "SELECT * FROM project WHERE organization_id = ?;";
            PreparedStatement pstmt1 = con.prepareStatement(SQL1);
            pstmt1.setInt(1, organization_id);
            ResultSet rs1 = pstmt1.executeQuery();

            while (rs1.next()) {
                int project_id = rs1.getInt("project_id");
                String project_name = rs1.getString("project_name");
                double estimated_time = rs1.getDouble("estimated_time");

                //Create new project
                Project project = new Project(project_id, project_name, estimated_time, new ArrayList<>(), organization_id);

                //Employees to project
                String SQL2 = "SELECT * FROM employee INNER JOIN project_employee USING(employee_id) WHERE project_id = ?;";
                PreparedStatement pstmt2 = con.prepareStatement(SQL2);
                pstmt2.setInt(1, project_id);
                ResultSet rs2 = pstmt2.executeQuery();
                Set<Integer> addedEmployees = new HashSet<>(); // keep track of added employees

                while (rs2.next()) {
                    int employee_id = rs2.getInt("employee_id");
                    if (!addedEmployees.contains(employee_id)) { // check if employee has already been added
                        String first_name = rs2.getString("employee_firstname");
                        String last_name = rs2.getString("employee_lastname");
                        String email = rs2.getString("email");

                        Employee employee = new Employee(employee_id, first_name, last_name, email, organization_id);
                        project.addEmployee(employee);
                        addedEmployees.add(employee_id); // add employee id to the set
                    }
                }
                projects.add(project); // add the project to the list
            }
            return projects;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    //Create project
    public void createProject(ProjectEmployeeForm form, int organization_id) {

        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);

            int project_id = 0;
            //organization_id = 0;
            List<Integer> employee_ids = new ArrayList<>();

           /* //find organization_id
            String SQL1 = "SELECT organization_id FROM organization WHERE organization_name = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL1);
            pstmt.setString(1, form.getOrganization());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                organization_id = rs.getInt("organization_id");
            }*/

            //insert row in project
            String SQL2 = "INSERT INTO project (project_name, estimated_time, organization_id) VALUES (?, ?, ?);";
            PreparedStatement pstmt = con.prepareStatement(SQL2, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, form.getProject_name());
            pstmt.setDouble(2, form.getEstimated_time());
            pstmt.setInt(3, organization_id);
            int rows = pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                project_id = rs.getInt(1);
            }

            //find employee_ids
            String SQL3 = "SELECT employee_id FROM employee WHERE employee_firstname = ?;";
            pstmt = con.prepareStatement(SQL3);
            for (String name : form.getEmployees()) {
                pstmt.setString(1, name);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    employee_ids.add(rs.getInt("employee_id"));
                }
            }

            //insert into project_employee table
            String SQL4 = "INSERT INTO project_employee VALUES (?, ?);";
            pstmt = con.prepareStatement(SQL4);
            for (int i = 0; i < employee_ids.size(); i++) {
                pstmt.setInt(1, project_id);
                pstmt.setInt(1, employee_ids.get(i));
                rows = pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





    /* //Get project from project_id
    //TODO:: Change to better descriptive names, to increase code consistency and readability
    public Project getProject(int project_id) {
        Project project = null;
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM project WHERE project_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, project_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String project_name = rs.getString("project_name");
                double estimated_time = rs.getDouble("estimated_time");


                String employeeIdsString = rs.getString("employee_id");
                String[] employeeIdsArray = employeeIdsString.split(",");
                //Convert array of strings to int
                List<Integer> employee_ids = new ArrayList<>();
                for (String id : employeeIdsArray) {
                    employee_ids.add(Integer.parseInt(id));
                }

                int organization_id = rs.getInt("organization_id");

                project = (new Project(project_id, project_name, estimated_time, employee_ids, organization_id));
            }
            return project;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

    /*
    //Add projects to org
    public Project createProject(Project project, int organization_id, List<Integer> employee_ids) {
        Project project1 = null;
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);

            //List of employees
            List<Employee> employees = project.getEmployees();
            for (Integer employee_id  : employee_ids) {
                String SQL1 ="SELECT * FROM employee WHERE employee_id = ?;";
                PreparedStatement pstmt1 = con.prepareStatement(SQL1);
                pstmt1.setInt(1, employee_id );
                ResultSet rs1 = pstmt1.executeQuery();

                if(rs1.next()) {
                    employee_id = rs1.getInt("employee_id");
                    String employee_firstname = rs1.getString("employee_firstname");
                    String employee_lastname = rs1.getString("employee_lastname");
                    String email = rs1.getString("email");

                    employees.add(new Employee(employee_id, employee_firstname, employee_lastname, email, organization_id));
                }
            }

            //Insert project
            String SQL2 = "INSERT INTO project (project_name, estimated_time, organization_id) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement pstmt2 = con.prepareStatement(SQL2, Statement.RETURN_GENERATED_KEYS);
            pstmt2.setString(1, project.getProject_name());
            pstmt2.setDouble(2, project.getEstimated_time());
            pstmt2.setInt(3, organization_id);
            pstmt2.executeUpdate();
            ResultSet rs = pstmt2.getGeneratedKeys();

            if (rs.next()) {
                int project_id = rs.getInt(1);
                project1 = new Project(project_id, project.getProject_name(), project.getEstimated_time(), employees, organization_id);

                //Project employees inserted into project_employee table
                if (employees != null) {
                    SQL2 = "INSERT INTO project_employee (project_id, employee_id) VALUES (?, ?)";
                    pstmt2 = con.prepareStatement(SQL2);
                    for (Employee employee : employees) {
                        pstmt2.setInt(1, project_id);
                        pstmt2.setInt(2, employee.getEmployee_id());
                        pstmt2.addBatch();
                    }
                    pstmt2.executeBatch();
                    project1.setEmployees(employees);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return project1;
    }

    public void addProject(Project project, int organization_id) {

        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            //ID's
            organization_id = 0;
            int project_id = 0;
            int employee_id = 0;
            List<>

        }
    }*/

    /*//Update project
    //TODO:: Why is project_ID in the parameter. Change names to lowercase, to increase code consistency
    public void editProject(Project project, int project_ID) {
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "UPDATE project SET project_name = ?, estimated_time = ?";
            try (PreparedStatement stmt = con.prepareStatement(SQL)) {
                stmt.setString(1, project.getProject_name());
                stmt.setDouble(2, project.getEstimated_time());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }*/


    //---------------------------------TASK JDBC METHODS-----------------------------------------//

    //---------------------------------SUBTASK JDBC METHODS--------------------------------------//


}