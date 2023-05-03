package com.example.eksamensprojekt_2sem.Controller;

import com.example.eksamensprojekt_2sem.Model.Organization;
import com.example.eksamensprojekt_2sem.Repository.RepositoryDB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "")
@Controller
public class LoginController {

    private RepositoryDB repositoryDB;

    public LoginController(RepositoryDB repositoryDB) {
        this.repositoryDB = repositoryDB;
    }

    @GetMapping(path = "/")
    public String index() {
        return "index";
    }


    @GetMapping(path ="/home")
    public String homeOrg(HttpSession session) {
        session.setMaxInactiveInterval(30);
        return "home";
    }

    //Checks if user is in current session
    @GetMapping(path = "/signin")
    public String isUserConnected(HttpSession session, Model model) {
        Organization organization = new Organization();

        //If not connected redirect to login page. if connected continue to home page
        if (session.getAttribute("organization_id") == null) {
            model.addAttribute("organization", organization);

            return "signin";

        } else {
            return "redirect:/home";
        }
    }

    //Sign in with user
    @PostMapping(path = "/signin")
    public String signIn(HttpSession session, @ModelAttribute("org") Organization org) {

        try {
            Organization org2 = repositoryDB.signIn(org.getOrganization_name(), org.getPassword());
            if (org2 != null) {
                session.setAttribute("organization_id", org2.getOrganization_id());
                session.setMaxInactiveInterval(30);
                return "redirect:/home";
            } else {
                return "signin";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path="/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "signin";
    }

}
