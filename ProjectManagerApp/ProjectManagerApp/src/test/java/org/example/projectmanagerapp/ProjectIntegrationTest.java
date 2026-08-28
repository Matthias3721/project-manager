package org.example.projectmanagerapp;

import org.example.projectmanagerapp.entity.Project;
import org.example.projectmanagerapp.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.http.MediaType;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class ProjectIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void getAllProjects() throws Exception{
        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
    @Test
    public void createProject() throws Exception{
        mockMvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Integration Project\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Integration Project"));
    }

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres =
            new PostgreSQLContainer("postgres:17");


    @Test
    public void getProjectById() throws Exception{
        Project project = new Project();
        project.setName("GetById Project");
        projectRepository.save(project);

        mockMvc.perform(get("/api/projects/{id}", project.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("GetById Project"));
    }

    @Test
    public void deleteProject() throws Exception{
        Project project = new Project();
        project.setName("Delete Project");
        projectRepository.save(project);

        mockMvc.perform(delete("/api/projects/{id}", project.getId()))
                .andExpect(status().isOk());

        assertFalse(projectRepository.existsById(project.getId()));

    }

    @Test
    public void updateProject() throws Exception{
        Project project = new Project();
        project.setName("Old Name");
        projectRepository.save(project);
        mockMvc.perform(put("/api/projects/{id}", project.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"New Name\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Name"));

        Project updatedProject = projectRepository.findById(project.getId()).orElseThrow();

        assertEquals("New Name", updatedProject.getName());

    }

}