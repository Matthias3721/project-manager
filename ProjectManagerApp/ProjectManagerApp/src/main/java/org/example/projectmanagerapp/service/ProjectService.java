package org.example.projectmanagerapp.service;
import org.example.projectmanagerapp.entity.Project;
import org.example.projectmanagerapp.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public Project createProject(Project project){
        if(project.getName() == null || project.getName().isBlank()){
            throw new IllegalArgumentException("Project name cannot be empty");
        }
        return projectRepository.save(project);
    }

    public Project getProjectById(Long id){
        return projectRepository.findById(id)
                .orElseThrow();
    }

    public void deleteProjectById(Long id){
        projectRepository.deleteById(id);
    }

    public Project updateProject(Long id, Project updatedProject){

        Project existingProject = getProjectById(id);

        existingProject.setName(updatedProject.getName());

        projectRepository.save(existingProject);

        return existingProject;

    }

}
