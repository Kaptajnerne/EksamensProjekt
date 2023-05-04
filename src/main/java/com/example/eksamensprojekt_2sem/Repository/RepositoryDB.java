package com.example.eksamensprojekt_2sem.Repository;

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
        try (Connection conn = ConnectionManager.getConnection(db_url, uid, pwd)) {
            String SQL = "UPDATE organization SET organization_name = ?, password = ? WHERE organization_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(SQL)) {
                stmt.setString(1, organization.getOrganization_name());
                stmt.setString(2, organization.getPassword());
                stmt.setInt(3, organization_id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
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

            if (rs.next()) {
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

    //---------------------------------TASK JDBC METHODS-----------------------------------------//

    //---------------------------------SUBTASK JDBC METHODS--------------------------------------//


}