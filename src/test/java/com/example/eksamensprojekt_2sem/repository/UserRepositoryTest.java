package com.example.eksamensprojekt_2sem.repository;

import com.example.eksamensprojekt_2sem.Model.User;
import com.example.eksamensprojekt_2sem.Repository.UserRepositoryDB;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ComponentScan("com.example.eksamensprojekt_2sem.Repository.UserRepositoryDB")
public class UserRepositoryTest {

    @Autowired
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

        //Print user
        System.out.println("User ID: " + user.getUser_id());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password: " + user.getPassword());

    }
}