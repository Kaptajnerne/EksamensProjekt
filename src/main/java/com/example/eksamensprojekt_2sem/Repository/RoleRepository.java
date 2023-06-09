package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.Model.Role;
import com.example.eksamensprojekt_2sem.Util.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleRepository {

    //Get all roles
    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM role";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int role_id = rs.getInt("role_id");
                String role_name = rs.getString("role_name");
                Role role = new Role(role_id, role_name);
                roles.add(role);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roles;
    }

}
