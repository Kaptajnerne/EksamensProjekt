package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.Model.Task;
import com.example.eksamensprojekt_2sem.Util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository {
    @Value("jdbc:mysql://localhost:3306/pct_db2")
    private String db_url;

    @Value("root")
    private String uid;

    @Value("1234")
    private String pwd;


    //Get Task by project_id
    public List<Task> getTaskByProID(int project_id) {
        List<Task> tasks = new ArrayList<>();
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM task WHERE project_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, project_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int task_id = rs.getInt("task_id");
                String task_name = rs.getString("task_name");
                Double hours = rs.getDouble("hours");
                tasks.add(new Task(task_id, task_name, hours, project_id));
            }
            return tasks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Create Task
    public void createTask(Task task, int project_id) {
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "INSERT INTO task (task_name, hours, project_id) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, task.getTask_name());
            pstmt.setDouble(2, task.getHours());
            pstmt.setInt(3, project_id);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                int task_id = rs.getInt(1);
                task.setTask_id(task_id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Delete Task
    public void deleteTask(int task_id) {
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "DELETE FROM subtask WHERE task_id = ?";
            String SQL1 = "DELETE FROM task WHERE task_id = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            PreparedStatement pstmt1 = con.prepareStatement(SQL1);
            pstmt.setInt(1, task_id);
            pstmt.executeUpdate();
            pstmt1.setInt(1, task_id);
            pstmt1.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Task getTaskbyTaskId(int task_id) {
        Task task = null;
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM task WHERE task_id = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, task_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String task_name = rs.getString("task_name");
                Double hours = rs.getDouble("hours");
                int project_id = rs.getInt("project_id");
                task = new Task(task_id,task_name, hours, project_id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
                return task;
    }

    public int getProIDbyTaskID(int task_id){
        int project_id = 0;
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT project_id FROM task WHERE task_id = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, task_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                project_id = rs.getInt("project_id");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return project_id;
    }


}
