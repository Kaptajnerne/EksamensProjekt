package com.example.eksamensprojekt_2sem.Controller;

import com.example.eksamensprojekt_2sem.Model.Organization;
import com.example.eksamensprojekt_2sem.Repository.RepositoryDB;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "")
@Controller
public class LoginController {

    private RepositoryDB repositoryDB;

    public LoginController(RepositoryDB repositoryDB) {
        this.repositoryDB = repositoryDB;
    }

    //Index page shows sign in or sign up
    @GetMapping(path = "/")
    public String index() {
        return "index";
    }

    //Page users see when they sign in
    //TODO:: Session timer doesn't translate over to org home page. "session.setMaxInactiveInterval(10);"
    @GetMapping(path ="/home/{organization_id}")
    public String homeOrg(Model model, @PathVariable int organization_id, HttpSession session) {
        model.addAttribute("organization_id", organization_id);
        return "home";
    }

    //Checks if user is in current session
    @GetMapping(path = "/signin")
    public String isUserConnected(HttpSession session, Model model) {
        //Organization object from session
        Organization organization = (Organization) session.getAttribute("organization");

        //If not connected redirect to login page. if connected continue to home page
        if (organization == null) {
            model.addAttribute("organization", new Organization());
            return "signin";
        } else {
            return "redirect:/home/" + organization.getOrganization_id();
        }
    }


    //Sign in with user
    @PostMapping(path = "/signin")
    public String signIn(HttpSession session, @ModelAttribute("org") Organization org) {
        try {
            Organization orgLogin = repositoryDB.signIn(org.getOrganization_name(), org.getPassword());
            if (orgLogin != null) {
                session.setAttribute("organization", orgLogin);
                session.setMaxInactiveInterval(10);

                return "redirect:/home/" + orgLogin.getOrganization_id();
            } else {
                return "signin";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //Sign out
    @GetMapping(path="/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/signin";
    }
}
