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

    @Value("jdbc:mysql://localhost:3306/pmdb")
    private String db_url;

    @Value("root")
    private String uid;

    @Value("1234")
    private String pwd;

    public List<Project> getProjects() {
        List<Project> projects = new ArrayList<>();

        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT projectname, duration FROM project;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int projectID = rs.getInt("projectid");
                String projectName = rs.getString("projectname");
                double duration = rs.getDouble("duration");

                projects.add(new Project(projectID, projectName, duration));
            }
            return projects;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
