package com.example.eksamensprojekt_2sem.repository;

import com.example.eksamensprojekt_2sem.Model.Project;
import com.example.eksamensprojekt_2sem.Repository.ProjectRepositoryDB;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ComponentScan("com.example.eksamensprojekt_2sem.Repository.ProjectRepositoryDB")
public class ProjectRepositoryTest {

/*
    @Autowired
    private ProjectRepositoryDB projectRepositoryDB;

    @Test
    public void testCreateProject() {
        //Test data
        int user_id = 2;
        Project project = new Project(1, "Test Project", "Project description", LocalDate.now(), LocalDate.now(), user_id);

        //Create project method
        projectRepositoryDB.createProject(project, user_id);

        //Verify the project is created
        assertNotNull(project.getProject_id());
        assertEquals("Test Project", project.getProject_name());
        assertEquals("Project description", project.getProject_description());
    }


    @Test
    public void testDeleteProject() {
        //Test data
        int userId = 2;
        Project project = new Project(1, "Test Project", "Project description", LocalDate.now(), LocalDate.now(), userId);

        //Create project method
        projectRepositoryDB.createProject(project, userId);

        //Delete project method
        projectRepositoryDB.deleteProject(project.getProject_id());

        //Verify project is deleted
        assertNull(projectRepositoryDB.getProjectByProjectID(project.getProject_id()));
    }


    @Test
    public void testEditProject() {
        //Test data
        int userId = 2;
        Project project = new Project(10, "Test Project", "Project description", LocalDate.now(), LocalDate.now(), userId);

        //Edit project method
        projectRepositoryDB.createProject(project, userId);

        //Edit project values
        project.setProject_name("Updated Project");
        project.setProject_description("Updated description");
        project.setStart_date(LocalDate.now().plusDays(1));
        project.setEnd_date(LocalDate.now().plusDays(2));

        //Edit project method
        projectRepositoryDB.editProject(project, project.getProject_id(), userId);

        //Get updated project
        Project updatedProject = projectRepositoryDB.getProjectByProjectID(project.getProject_id());

        //Verify project is updated
        assertNotNull(updatedProject);
        assertEquals(project.getProject_name(), updatedProject.getProject_name());
        assertEquals(project.getProject_description(), updatedProject.getProject_description());
        assertEquals(project.getStart_date(), updatedProject.getStart_date());
        assertEquals(project.getEnd_date(), updatedProject.getEnd_date());
    }*/
}