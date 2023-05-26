package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
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
                LocalDate start_date = rs.getDate("start_date").toLocalDate();
                LocalDate end_date = rs.getDate("end_date").toLocalDate();

                projects.add(new Project(project_id, project_name, project_description, start_date, end_date, user_id));
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

            String SQL = "INSERT INTO project (project_name, project_description, start_date, end_date, user_id) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, project.getProject_name());
            pstmt.setString(2, project.getProject_description());
            pstmt.setObject(3, Date.valueOf(project.getStart_date()));
            pstmt.setObject(4, Date.valueOf(project.getEnd_date()));
            pstmt.setInt(5, user_id);
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
            String SQL = "UPDATE project SET project_name = ?, project_description = ?, start_date = ?, end_date = ? WHERE project_id = ? AND user_id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
                pstmt.setString(1, project.getProject_name());
                pstmt.setString(2, project.getProject_description());
                pstmt.setObject(3, Date.valueOf(project.getStart_date()));
                pstmt.setObject(4, Date.valueOf(project.getEnd_date()));
                pstmt.setInt(5, project_id);
                pstmt.setInt(6, user_id);

                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    //Get project by project_id and user_id
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
                LocalDate start_date = rs.getDate("start_date").toLocalDate();
                LocalDate end_date = rs.getDate("end_date").toLocalDate();

                project = new Project(project_id, project_name, project_description, start_date, end_date, user_id);
            }
            return project;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //Get project by project_id
    public Project getProjectByProjectID(int project_id) {
        Project project = null;
        try {
            Connection con = DriverManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM project WHERE project_id = ?;";
            PreparedStatement statement = con.prepareStatement(SQL);
            statement.setInt(1, project_id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String project_name = rs.getString("project_name");
                String project_description = rs.getString("project_description");
                int user_id = rs.getInt("user_id");
                LocalDate start_date = rs.getDate("start_date").toLocalDate();
                LocalDate end_date = rs.getDate("end_date").toLocalDate();

                project = new Project(project_id, project_name, project_description, start_date, end_date, user_id);
            }
            return project;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //delete project
    public void deleteProject(int projectId) {
        try {
            Connection connection = DriverManager.getConnection(db_url, uid, pwd);
            connection.setAutoCommit(false);

            //Delete subtask
            String SQL1 = "DELETE FROM subtask WHERE task_id IN (SELECT task_id FROM task WHERE project_id = ?)";
            PreparedStatement deleteSubtasksStatement = connection.prepareStatement(SQL1);
            deleteSubtasksStatement.setInt(1, projectId);
            deleteSubtasksStatement.executeUpdate();

            // Delete tasks
            String SQL2 = "DELETE FROM task WHERE project_id = ?";
            PreparedStatement deleteTasksStatement = connection.prepareStatement(SQL2);
            deleteTasksStatement.setInt(1, projectId);
            deleteTasksStatement.executeUpdate();

            // Delete project
            String SQL3 = "DELETE FROM project WHERE project_id = ?";
            PreparedStatement deleteProjectStatement = connection.prepareStatement(SQL3);
            deleteProjectStatement.setInt(1, projectId);
            deleteProjectStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Get project by task_id
    public int getProjectID(int task_id) {
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


    //Get time for all tasks and subtasks
    public Double getProjectCalculatedTime(int project_id) {
        double estimatedTime = 0;
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT SUM(COALESCE(t.hours, 0) + COALESCE(s.hours, 0)) AS totalTime FROM task AS t\n" +
                    "LEFT JOIN subtask AS s USING(task_id)\n" +
                    "WHERE t.project_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, project_id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                estimatedTime = rs.getDouble("totalTime");
            }
            return estimatedTime;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
