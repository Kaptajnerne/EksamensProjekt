package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.Model.Subtask;
import com.example.eksamensprojekt_2sem.Model.Task;
import com.example.eksamensprojekt_2sem.Util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubtaskRepositoryDB implements SubtaskIRepository {
    @Value("jdbc:mysql://localhost:3306/pct_db2")
    private String db_url;

    @Value("root")
    private String uid;

    @Value("1234")
    private String pwd;

    //Get subtasks from project_id
    public List<Subtask> getSubtasksByTaskID(int task_id) {
        List<Subtask> subtasks = new ArrayList<>();
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL1 = "SELECT * FROM subtask WHERE task_id = ?;";
            PreparedStatement pstmt1 = con.prepareStatement(SQL1);
            pstmt1.setInt(1, task_id);
            ResultSet rs1 = pstmt1.executeQuery();

            while (rs1.next()) {
                int subtask_id = rs1.getInt("subtask_id");
                String subtask_name = rs1.getString("subtask_name");
                double hours = rs1.getDouble("hours");

                subtasks.add(new Subtask(subtask_id, subtask_name, hours, task_id));
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

            String SQL = "INSERT INTO subtask (subtask_name, hours, task_id) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, subtask.getSubtask_name());
            pstmt.setDouble(2, subtask.getHours());
            pstmt.setInt(3, task_id);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                int subtask_id = rs.getInt(1);
                createdSubtask = new Subtask(subtask_id, subtask.getSubtask_name(), subtask.getHours(), task_id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return createdSubtask;
    }

    //Edit subtask
    public void editSubtask(Subtask subtask, int subtask_id, int task_id) {
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "UPDATE subtask SET subtask_name = ?, hours = ? WHERE subtask_id = ? AND task_id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
                pstmt.setString(1, subtask.getSubtask_name());
                pstmt.setDouble(2, subtask.getHours());
                pstmt.setInt(3, subtask_id);
                pstmt.setInt(4, task_id);

                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    //Get project from user_id and user_id
    public Subtask getSubtaskByIDs(int subtask_id, int task_id) {
        Subtask subtask = null;

        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM subtask WHERE subtask_id = ? AND task_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, subtask_id);
            pstmt.setInt(2, task_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String subtask_name = rs.getString("subtask_name");
                Double hours = rs.getDouble("hours");

                subtask = new Subtask(subtask_id,subtask_name,hours,task_id);
            }
            return subtask;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Delete subtask
    public void deleteSubtask(int subtask_id) {
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "DELETE FROM subtask WHERE subtask_id = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, subtask_id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Get subtask by id
    public Subtask getSubtaskByID(int subtask_id) {
        Subtask subtask = null;
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM subtask WHERE subtask_id = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, subtask_id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String subtask_name = rs.getString("subtask_name");
                double hours = rs.getDouble("hours");
                int task_id = rs.getInt("task_id");
                subtask = new Subtask(subtask_id, subtask_name, hours, task_id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subtask;
    }


}
