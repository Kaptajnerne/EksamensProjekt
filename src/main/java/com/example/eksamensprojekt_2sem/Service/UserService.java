package com.example.eksamensprojekt_2sem.Service;

import com.example.eksamensprojekt_2sem.Model.User;
import com.example.eksamensprojekt_2sem.Repository.UserRepositoryDB;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepositoryDB userRepositoryDB;

    public UserService(UserRepositoryDB userRepositoryDB) {
        this.userRepositoryDB = userRepositoryDB;
    }

    //Sign in
    public User signIn(String username, String password){
        return userRepositoryDB.signIn(username, password);
    }

    //Sign up
    public void signUp(User user) {
        userRepositoryDB.signUp(user);
    }

    //Is username taken
    public boolean isUsernameTaken(String username) {
        return userRepositoryDB.isUsernameTaken(username);
    }

    //Get user_id from project_id
    public int getUserID(int project_id) {
        return userRepositoryDB.getUserID(project_id);
    }

    //Edit user
    public void editUser(User user, int user_id) {
        userRepositoryDB.editUser(user, user_id);
    }

    //Get user from id
    public User getUserFromId(int user_id) {
        return userRepositoryDB.getUserFromId(user_id);
    }

}
