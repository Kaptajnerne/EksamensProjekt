package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.DTO.TaskSubtaskDTO;
import com.example.eksamensprojekt_2sem.Model.Subtask;
import com.example.eksamensprojekt_2sem.Model.Task;
import com.example.eksamensprojekt_2sem.Util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TaskRepositoryDB implements TaskIRepository {
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
                LocalDate start_date = rs.getDate("start_date").toLocalDate();
                LocalDate end_date = rs.getDate("end_date").toLocalDate();
                int status = rs.getInt("status");

                tasks.add(new Task(task_id, task_name, hours, start_date, end_date, status, project_id));
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
            String SQL = "INSERT INTO task (task_name, hours, start_date, end_date, status, project_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, task.getTask_name());
            pstmt.setDouble(2, task.getHours());
            pstmt.setObject(3, Date.valueOf(task.getStart_date()));
            pstmt.setObject(4, Date.valueOf(task.getEnd_date()));
            pstmt.setInt(5, task.getStatus());
            pstmt.setInt(6, project_id);
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

    //Edit task
    public void editTask(Task task, int task_id, int project_id) {
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "UPDATE task SET task_name = ?, hours = ?, start_date = ?, end_date = ?, status = ? WHERE task_id = ? AND project_id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
                pstmt.setString(1, task.getTask_name());
                pstmt.setDouble(2, task.getHours());
                pstmt.setObject(3, Date.valueOf(task.getStart_date()));
                pstmt.setObject(4, Date.valueOf(task.getEnd_date()));
                pstmt.setInt(5, task.getStatus());
                pstmt.setInt(6, task_id);
                pstmt.setInt(7, project_id);

                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    //Get project from user_id and user_id
    public Task getTaskByIDs(int task_id, int project_id) {
        Task task = null;

        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM task WHERE task_id = ? AND project_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, task_id);
            pstmt.setInt(2, project_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String task_name = rs.getString("task_name");
                Double hours = rs.getDouble("hours");
                LocalDate start_date = rs.getDate("start_date").toLocalDate();
                LocalDate end_date = rs.getDate("end_date").toLocalDate();
                int status = rs.getInt("status");

                task = new Task(task_id, task_name, hours, start_date, end_date, status, project_id);
            }
            return task;
        } catch (SQLException e) {
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

    //Get Task from task_id
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
                LocalDate start_date = rs.getDate("start_date").toLocalDate();
                LocalDate end_date = rs.getDate("end_date").toLocalDate();
                int status = rs.getInt("status");

                task = new Task(task_id, task_name, hours, start_date, end_date, status, project_id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return task;
    }

    //Get project_id by task_id
    public int getProIDbyTaskID(int task_id) {
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

    //Calculated time for task and subtask
    public Double getTaskCalculatedTime(int task_id) {
        double estimatedTime = 0;
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT SUM(COALESCE(t.hours, 0) + COALESCE(s.hours, 0)) AS totalTime FROM task AS t\n" +
                    "LEFT JOIN subtask AS s USING(task_id)\n" +
                    "WHERE t.task_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, task_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                estimatedTime = rs.getDouble("totalTime");
            }

            return estimatedTime;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<TaskSubtaskDTO> getTaskSubtasksByProID(int project_id) {
        List<TaskSubtaskDTO> taskSubtaskList = new ArrayList<>();
        Map<Integer, List<Subtask>> subtasksMap = new HashMap<>();

        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT t.task_id, t.task_name, t.hours AS task_hours, t.start_date AS task_start_date, t.end_date AS task_end_date, t.status AS task_status, t.project_id AS task_project_id, " +
                    "s.subtask_id, s.subtask_name, s.hours AS subtask_hours, s.start_date AS subtask_start_date, s.end_date AS subtask_end_date, s.status AS subtask_status, s.task_id AS subtask_task_id " +
                    "FROM task AS t " +
                    "INNER JOIN subtask AS s ON t.task_id = s.task_id " +
                    "WHERE t.project_id = ?;";

            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, project_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int task_id = rs.getInt("task_id");
                if (task_id >= 0) {
                    String task_name = rs.getString("task_name");
                    Double task_hours = rs.getDouble("task_hours");
                    LocalDate task_start_date = rs.getDate("task_start_date").toLocalDate();
                    LocalDate task_end_date = rs.getDate("task_end_date").toLocalDate();
                    int task_status = rs.getInt("task_status");

                    TaskSubtaskDTO taskSubtaskDTO = new TaskSubtaskDTO(task_id, task_name, task_hours, task_start_date, task_end_date, task_status, project_id, new ArrayList<>());
                    taskSubtaskList.add(taskSubtaskDTO);
                    subtasksMap.put(task_id, taskSubtaskDTO.getSubtasks());
                }

                int subtask_id = rs.getInt("subtask_id");
                if (subtask_id >= 0) {
                    String subtask_name = rs.getString("subtask_name");
                    Double subtask_hours = rs.getDouble("subtask_hours");
                    LocalDate subtask_start_date = rs.getDate("subtask_start_date").toLocalDate();
                    LocalDate subtask_end_date = rs.getDate("subtask_end_date").toLocalDate();
                    int subtask_status = rs.getInt("subtask_status");
                    int subtask_task_id = rs.getInt("subtask_task_id");

                    List<Subtask> subtasks = subtasksMap.get(subtask_task_id);
                    subtasks.add(new Subtask(subtask_id, subtask_name, subtask_hours, subtask_start_date, subtask_end_date, subtask_status, subtask_task_id));
                }
            }
            return taskSubtaskList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    public List<String> generateGanttChart(List<TaskSubtaskDTO> taskSubtaskList) {
        List<String> chartData = new ArrayList<>();
        chartData.add("['Task ID', 'Task Name', 'Resource', 'Start Date', 'End Date', 'Duration', 'Percent Complete', 'Dependencies']");

        for (TaskSubtaskDTO taskSubtaskDTO : taskSubtaskList) {
            int task_id = taskSubtaskDTO.getId();
            String task_name = taskSubtaskDTO.getName();
            LocalDate task_start_date = taskSubtaskDTO.getStart_date();
            LocalDate task_end_date = taskSubtaskDTO.getEnd_date();

            chartData.add("['" + task_id + "', '" + task_name + "', 'Task', " +
                    "new Date(" + task_start_date.getYear() + ", " + (task_start_date.getMonthValue() - 1) + ", " + task_start_date.getDayOfMonth() + "), " +
                    "new Date(" + task_end_date.getYear() + ", " + (task_end_date.getMonthValue() - 1) + ", " + task_end_date.getDayOfMonth() + "), null, 100, null]");

            List<Subtask> subtasks = taskSubtaskDTO.getSubtasks();
            for (Subtask subtask : subtasks) {
                int subtask_id = subtask.getSubtask_id();
                String subtask_name = subtask.getSubtask_name();
                LocalDate subtask_start_date = subtask.getStart_date();
                LocalDate subtask_end_date = subtask.getEnd_date();

                chartData.add("['" + subtask_id + "', '" + subtask_name + "', 'Subtask', " +
                        "new Date(" + subtask_start_date.getYear() + ", " + (subtask_start_date.getMonthValue() - 1) + ", " + subtask_start_date.getDayOfMonth() + "), " +
                        "new Date(" + subtask_end_date.getYear() + ", " + (subtask_end_date.getMonthValue() - 1) + ", " + subtask_end_date.getDayOfMonth() + "), null, 100, '" + task_id + "']");
            }
        }
        return chartData;
    }
    */



}