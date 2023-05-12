package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
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


    //Create projects
    public void createProject(Project project, int user_id) {
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);

            String SQL = "INSERT INTO project (project_name, project_description, user_id) VALUES (?, ?, ?);";
            PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, project.getProject_name());
            pstmt.setString(2, project.getProject_description());
            pstmt.setInt(3, user_id);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                int project_id = rs.getInt(1);
                project.setProject_id(project_id);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //Update project
    public void editProject(Project project, int project_id, int user_id) {
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "UPDATE project SET project_name = ?, project_description = ? WHERE project_id = ? AND user_id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
                pstmt.setString(1, project.getProject_name());
                pstmt.setString(2, project.getProject_description());
                pstmt.setInt(3, project_id);
                pstmt.setInt(4, user_id);

                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    //Get project from user_id and user_id
    public Project getProjectByIDs(int project_id, int user_id) {
        Project project = null;

        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM project WHERE project_id = ? AND user_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, project_id);
            pstmt.setInt(2, user_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String project_name = rs.getString("project_name");
                String project_description = rs.getString("project_description");

                project = new Project(project_id, project_name, project_description, user_id);
            }
            return project;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Project getProjectByProjectID(int project_id) {
        Project project = null;
        try {
            Connection con = DriverManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM project WHERE project_id = ?;";
            PreparedStatement statement = con.prepareStatement(SQL);
            statement.setInt(1, project_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String project_name = resultSet.getString("project_name");
                String project_description = resultSet.getString("project_description");
                int user_id = resultSet.getInt("user_id");
                project = new Project(project_id, project_name, project_description, user_id);
            }
            return project;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteProject(int projectId) {
        try {
            Connection connection = DriverManager.getConnection(db_url, uid, pwd);
            PreparedStatement statement1 = connection.prepareStatement("DELETE FROM subtask WHERE task_id IN "
                    + "(SELECT task_id FROM task WHERE project_id = ?)");
            PreparedStatement statement2 = connection.prepareStatement("DELETE FROM task WHERE project_id = ?");
            PreparedStatement statement3 = connection.prepareStatement("DELETE FROM project WHERE project_id = ?");

            connection.setAutoCommit(false);

            statement1.setInt(1, projectId);
            statement1.executeUpdate();

            statement2.setInt(1, projectId);
            statement2.executeUpdate();

            statement3.setInt(1, projectId);
            statement3.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getProjectID (int task_id){
        int project_id = 0;
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT project_id from task WHERE task_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, task_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                project_id = rs.getInt("project_id");
            }
            return project_id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
