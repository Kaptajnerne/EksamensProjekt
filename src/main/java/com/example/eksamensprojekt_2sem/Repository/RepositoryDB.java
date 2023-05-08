package com.example.eksamensprojekt_2sem.Repository;

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
import java.util.Arrays;
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

    //---------------------------------EMPLOYEE JDBC METHODS-------------------------------------//


    //---------------------------------PROJECT JDBC METHODS--------------------------------------//

    //Get projects from org
    public List<Project> getProjects(int organization_id) {
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
                String employeeIdsString = rs.getString("employee_id");
                String[] employeeIdsArray = employeeIdsString.split(",");

                // Convert array of strings to int
                //JDBC didn't support normal
                List<Integer> employee_ids = new ArrayList<>();
                for (String id : employeeIdsArray) {
                    employee_ids.add(Integer.parseInt(id));
                }

                projects.add(new Project(project_id, project_name, estimated_time, employee_ids, organization_id));
            }
            return projects;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //Add projects to org
    public Project addProject(Project project, int organization_id) {
        Project project1 = null;
        try {
            Connection conn = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "INSERT INTO project (project_name, estimated_time, employee_id, organization_id) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, project.getProject_name());
            pstmt.setDouble(2, project.getEstimated_time());

            List<Integer> employeeIds = project.getEmployee_id();
            Array employeeIdsArray = conn.createArrayOf("INTEGER", employeeIds.toArray());
            pstmt.setArray(3, employeeIdsArray);
            pstmt.setInt(4, organization_id);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                int project_id = rs.getInt(1);
                project1 = new Project(project_id, project.getProject_name(), project.getEstimated_time(), project.getEmployee_id(), organization_id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return project1;
    }
    //---------------------------------TASK JDBC METHODS-----------------------------------------//

    //---------------------------------SUBTASK JDBC METHODS--------------------------------------//


}