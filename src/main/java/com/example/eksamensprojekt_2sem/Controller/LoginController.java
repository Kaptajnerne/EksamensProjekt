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
    public String homeOrg() {
        return "home";
    }

    //Is user in current session
    @GetMapping(path = "/signin")
    public String isUserConnected(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
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
    public String signIn(HttpServletRequest request, @ModelAttribute("org") Organization org) {
        HttpSession session = request.getSession();

        try {
            Organization org2 = repositoryDB.signIn(org.getOrganization_name(), org.getPassword());
            if (org2 != null) {
                session.setAttribute("organization_id", org2.getOrganization_id());
                return "redirect:/home";
            } else {
                return "signin";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
