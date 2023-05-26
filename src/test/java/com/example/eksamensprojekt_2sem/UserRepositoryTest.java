package com.example.eksamensprojekt_2sem;

import com.example.eksamensprojekt_2sem.Model.User;
import com.example.eksamensprojekt_2sem.Repository.UserRepositoryDB;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.*;
public class UserRepositoryTest {

   /* @Autowired
    private UserRepositoryDB userRepository;

    @Test
    public void testSignUp() {

        //Set values
        User user = new User(1, "username", "password");
        user.setUsername("testUser");
        user.setPassword("testPassword");

        //Signup method and set user_id
        userRepository.signUp(user);

        // Assert the user_id is set correctly
        assertNotNull(user.getUser_id());
        assertEquals("testUser", user.getUsername());
        assertEquals("testPassword", user.getPassword());
    }*/
}