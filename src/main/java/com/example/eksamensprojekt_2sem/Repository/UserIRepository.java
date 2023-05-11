package com.example.eksamensprojekt_2sem.Repository;

import com.example.eksamensprojekt_2sem.Model.User;

public interface UserIRepository {

    //Sign in with user
    public User signIn(String organization_name, String password);

    //Sign up
    public void signUp(User user);
}
