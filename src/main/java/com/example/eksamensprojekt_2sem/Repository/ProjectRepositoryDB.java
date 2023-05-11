package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepositoryDB implements ProjectIRepository {

    @Value("jdbc:mysql://localhost:3306/pct_db2")
    private String db_url;

    @Value("root")
    private String uid;

    @Value("1234")
    private String pwd;

    //---------------------------------PROJECT JDBC METHODS--------------------------------------//

    //Get projects from user_id
    public List<Project> getProjectsByID(int user_id) {
        List<Project> projects = new ArrayList<>();
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM project WHERE user_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int project_id = rs.getInt("project_id");
                String project_name = rs.getString("project_name");
                String project_description = rs.getString("project_description");

                projects.add(new Project(project_id, project_name, project_description, user_id));
            }
            return projects;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

   /* //Get project from user_id
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
}
