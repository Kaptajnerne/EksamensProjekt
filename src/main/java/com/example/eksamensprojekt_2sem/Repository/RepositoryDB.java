package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.Model.Employee;
import com.example.eksamensprojekt_2sem.Model.Organization;
import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Model.Subtask;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
                organization1 = new Organization(organization_id,organization_name, password);
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

    //Get projects from org id
    public List<Project> getProjectsByID(int organization_id) {
        List<Project> projects = new ArrayList<>();
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM project WHERE organization_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, organization_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int project_id = rs.getInt("project_id");
                String project_name = rs.getString("project_name");
                double estimated_time = rs.getDouble("estimated_time");
                int employee_id = rs.getInt("employee_id");

                projects.add(new Project(project_id, project_name, estimated_time, employee_id, organization_id));
            }
            return projects;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Get project from org id
    //TODO:: Change to better descriptive names, to increase code consistency and readability
    public Project getProject(int id) {
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM project WHERE project_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            Project project = null;
            while (rs.next()) {
                int project_id = rs.getInt("project_id");
                String project_name = rs.getString("project_name");

                project = new Project(project_id, project_name);
            }
            return project;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Update project
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
    }

    //---------------------------------TASK JDBC METHODS-----------------------------------------//

    //---------------------------------SUBTASK JDBC METHODS--------------------------------------//
    //Get subtasks from project_id
    public List<Subtask> getSubtasksByProjectID(int project_id) {
        List<Subtask> subtasks = new ArrayList<>();
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL1 = "SELECT * FROM subtask WHERE task_id IN (SELECT task_id FROM task WHERE project_id = ?);";
            PreparedStatement pstmt1 = con.prepareStatement(SQL1);
            pstmt1.setInt(1, project_id);
            ResultSet rs1 = pstmt1.executeQuery();

            while (rs1.next()) {
                int subtask_id = rs1.getInt("subtask_id");
                String subtask_name = rs1.getString("subtask_name");
                LocalDate start_date = rs1.getObject("start_date", LocalDate.class);
                LocalDate end_date = rs1.getObject("end_date", LocalDate.class);

                subtasks.add(new Subtask(subtask_id, subtask_name, start_date, end_date, project_id));
            }
            return subtasks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    //Create subtask to project
    public Subtask createSubtask(Subtask subtask, int task_id) {
        Subtask createdSubtask = null;
        try {
            Connection conn = ConnectionManager.getConnection(db_url, uid, pwd);

            String SQL = "INSERT INTO subtask (subtask_name, start_date, end_date, task_id) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, subtask.getSubtask_name());
            pstmt.setObject(2, subtask.getStart_date());
            pstmt.setObject(3, subtask.getEnd_date());
            pstmt.setInt(4, task_id);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                int subtask_id = rs.getInt(1);
                createdSubtask = new Subtask(subtask_id, subtask.getSubtask_name(), subtask.getStart_date(), subtask.getEnd_date(), task_id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return createdSubtask;
    }




}