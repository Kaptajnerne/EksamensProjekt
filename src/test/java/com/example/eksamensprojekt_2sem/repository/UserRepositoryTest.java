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
/*

    @Autowired
    private UserRepositoryDB userRepository;


    @Test
    public void testSignUp() {

        //Test data
        User user = new User(1, "username", "password");
        user.setUsername("testUser");
        user.setPassword("testPassword");

        //Signup method and set user_id
        userRepository.signUp(user);

        //Verify the user_id is set correctly
        assertNotNull(user.getUser_id());
        assertEquals("testUser", user.getUsername());
        assertEquals("testPassword", user.getPassword());

        //Print user
        System.out.println("User ID: " + user.getUser_id());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password: " + user.getPassword());

    }

    @Test
    public void testSignIn() {

        //Test data - Already existing user in the database
        String username = "KEA";
        String password = "1234";

        //SignIn method
        User signInUser = userRepository.signIn(username, password);

        //Verify successful result of the signIn method
        assertNotNull(signInUser);
        assertEquals(username, signInUser.getUsername());
        assertEquals(password, signInUser.getPassword());
    }

    @Test
    public void testFailedSignIn() {
        // Test data - Nonexistent user in the database
        String username = "IKEA";
        String password = "1234";

        //SignIn method
        User signInUser = userRepository.signIn(username, password);

        //Verify failed result of the signIn method
        assertNull(signInUser);
    }
*/
}