package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositoryDB {

    @Value("jdbc:mysql://localhost:3306/pct_db")
    private String db_url;

    @Value("root")
    private String uid;

    @Value("1234")
    private String pwd;

    //---------------------------------ORGANIZATION JDBC METHODS---------------------------------//

    //---------------------------------EMPLOYEE JDBC METHODS-------------------------------------//

    //---------------------------------PROJECT JDBC METHODS--------------------------------------//

    //Get All Projects
    public List<Project> getProjects() {
        List<Project> projects = new ArrayList<>();
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM project;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int project_id = rs.getInt("project_id");
                String project_name = rs.getString("project_name");
                double estimated_time = rs.getDouble("estimated_time");
                int employee_id = rs.getInt("employee_id");
                int organization_id = rs.getInt("organization_id");

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