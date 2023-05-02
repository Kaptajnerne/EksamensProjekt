package com.example.eksamensprojekt_2sem.Controller;

import com.example.eksamensprojekt_2sem.Model.Organization;
import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Repository.RepositoryDB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(path = "")
@org.springframework.stereotype.Controller
public class Controller {

    private RepositoryDB repositoryDB;

    public Controller(RepositoryDB repositoryDB) {
        this.repositoryDB = repositoryDB;
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

    @GetMapping(path ="/home")
    public String homePageOrg () {
        return "home";
    }

    //All Projects
    @GetMapping(path = "projects")
    public String showProjects(Model model) {
        List<Project> projects = repositoryDB.getProjects();
        model.addAttribute("projects", projects);
        return "projects";
    }
}
