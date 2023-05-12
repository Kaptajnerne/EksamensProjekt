package com.example.eksamensprojekt_2sem.Controller;

import com.example.eksamensprojekt_2sem.Model.User;
import com.example.eksamensprojekt_2sem.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "")
@org.springframework.stereotype.Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Index page shows sign in or sign up
    @GetMapping(path = "/")
    public String index() {
        return "index";
    }


    //Page users see when they sign in
    @GetMapping(path ="/home/{user_id}")
    public String homeOrg(Model model, @PathVariable int user_id) {
        model.addAttribute("user_id", user_id);
        return "home";
    }


    //Checks if user is in current session
    @GetMapping(path = "/signin")
    public String isUserConnected(HttpSession session, Model model) {
        //Organization object from session
        User user = (User) session.getAttribute("user");

        //If not connected redirect to login page. if connected continue to home page
        if (user == null) {
            model.addAttribute("user", new User());
            return "signin";
        } else {
            return "redirect:/home/" + user.getUser_id();
        }
    }

    //Sign in with user
    //TODO:: if wrong login input redirect to signin page and let user do it again
    @PostMapping(path = "/signin")
    public String signIn(HttpSession session, @ModelAttribute("user") User user) {
        try {
            User userLogin = userService.signIn(user.getUsername(), user.getPassword());
            if (userLogin != null) {
                session.setAttribute("user", userLogin);
                session.setMaxInactiveInterval(100);

                return "redirect:/home/" + userLogin.getUser_id();
            } else {
                return "signin";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //Sign up page
    @GetMapping(path = "/signup")
    public String showSignUp(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "signup";
    }

    //Sign up
    @PostMapping(path = "/signup")
    public String signup(@ModelAttribute ("organization") User user){
        userService.signUp(user);
        return "redirect:/signin";
    }


    //Sign out
    @GetMapping(path="/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/signin";
    }


    //Edit user page
    @GetMapping(path = "/editUser/{user_id}")
    public String showEditUser(@PathVariable("user_id") int user_id, Model model) {
        model.addAttribute("user_id", user_id);
        User user = userService.getUserFromId(user_id);
        model.addAttribute("user", user);
        return "editUser";
    }

    //Edit user
    @PostMapping(path = "/editUser/{user_id}")
    public String editUser(@PathVariable("user_id") int user_id, @RequestParam("username")  String user_name, String password) {
        User user = userService.getUserFromId(user_id);
        user.setUsername(user_name);
        user.setPassword(password);
        userService.editUser(user,user_id);
        return  "redirect:/home/"+ user_id;


    }
}
