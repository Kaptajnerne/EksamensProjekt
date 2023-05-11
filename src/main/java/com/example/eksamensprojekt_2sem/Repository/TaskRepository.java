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

    public Task createTask(Task task, int project_id) {
        Task tsk = null;
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "INSERT INTO task (task_name, start_date, end_date, project_id) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, task.getTask_name());
            pstmt.setDate(2, Date.valueOf(task.getStart_date()));
            pstmt.setDate(3, Date.valueOf(task.getEnd_date()));
            pstmt.setInt(4, project_id);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                int task_id = rs.getInt(1);
                tsk = new Task(task_id, task.getTask_name(), task.getStart_date(), task.getEnd_date(), project_id);
            }
            return tsk;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
                LocalDate start_date = rs.getDate("start_date").toLocalDate();
                LocalDate end_date = rs.getDate("end_date").toLocalDate();

                tasks.add(new Task(task_id, task_name, start_date, end_date, project_id));
            }
            return tasks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
