package org.example.projectmanagerapp.controller;

import org.example.projectmanagerapp.entity.Project;
import org.springframework.web.bind.annotation.*;
import org.example.projectmanagerapp.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> getProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id){
        return projectService.getProjectById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProjectById(@PathVariable Long id){
        projectService.deleteProjectById(id);
    }

    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Long id,@RequestBody Project updatedProject){
        return projectService.updateProject(id, updatedProject);
    }
}