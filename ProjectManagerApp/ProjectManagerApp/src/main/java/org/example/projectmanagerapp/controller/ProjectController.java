package org.example.projectmanagerapp.controller;

import org.example.projectmanagerapp.entity.Project;
import org.springframework.web.bind.annotation.*;
import org.example.projectmanagerapp.service.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@Tag(name = "Projects", description = "Operations related to projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(summary = "Get all projects")
    @GetMapping
    public List<Project> getProjects() {
        return projectService.getAllProjects();
    }

    @Operation(summary = "Create project")
    @PostMapping
    public Project createProject(
            @RequestBody Project project) {
        return projectService.createProject(project);
    }

    @Operation(summary = "Get project by ID")
    @GetMapping("/{id}")
    public Project getProjectById(
            @Parameter(description = "ID of the project")
            @PathVariable Long id){
        return projectService.getProjectById(id);
    }

    @Operation(summary = "Delete project by ID")
    @DeleteMapping("/{id}")
    public void deleteProjectById(
            @Parameter(description = "ID of the project")
            @PathVariable Long id){
        projectService.deleteProjectById(id);
    }
    @Operation(summary = "Update project by ID")
    @PutMapping("/{id}")
    public Project updateProject(
            @Parameter(description = "ID of the project")
            @PathVariable Long id,
            @RequestBody Project updatedProject){
        return projectService.updateProject(id, updatedProject);
    }
}