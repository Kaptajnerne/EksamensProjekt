package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Model.User;
import com.example.eksamensprojekt_2sem.Util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserRepositoryDB implements UserIRepository {
    @Value("jdbc:mysql://localhost:3306/pct_db2")
    private String db_url;

    @Value("root")
    private String uid;

    @Value("1234")
    private String pwd;


    //Sign in with user
    public User signIn(String username, String password) {
        User user = null;

        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM user WHERE username = ? AND password = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int user_id = rs.getInt("user_id");
                user = new User(user_id, username, password);
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Sign up user
    public void signUp(User user) {
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "INSERT INTO user (username ,password) VALUES (?, ?)";
            PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                int user_id = rs.getInt(1);
                user.setUser_id(user_id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //edit user
    public void editUser(User user, int user_id) {
        try {
            Connection conn = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "UPDATE user SET username = ?, password = ? WHERE user_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
                pstmt.setString(1, user.getUsername());
                pstmt.setString(2, user.getPassword());
                pstmt.setInt(3, user_id);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    //get user from id
    public User getUserFromId(int user_id) {
        try {
            Connection conn = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM user WHERE user_id = ?;";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            User user = null;

            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                user = new User(user_id, username, password);
            }
            return user;
        } catch (SQLException ex) {
            return null;
        }
    }


    //Get user id from project id
    public int getUserID (int project_id){
        int user_id = 0;
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT user_id from project WHERE project_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, project_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user_id = rs.getInt("user_id");
            }
            return user_id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //edit deadline
    /*public void editDeadline(Project project, int project_id, int user_id) {
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
    }*/

}
