package org.example.projectmanagerapp.service;

import org.example.projectmanagerapp.entity.Project;
import org.example.projectmanagerapp.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import java.util.NoSuchElementException;

public class ProjectServiceTest {

    ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);

    ProjectService projectService = new ProjectService(projectRepository);


    @Test
    public void getAllProjects(){
        Project project1 = new Project();

        Project project2 = new Project();

        when(projectRepository.findAll()).thenReturn(List.of(project1, project2));

        List<Project> projects = projectService.getAllProjects();
        assertEquals(2, projects.size());
        verify(projectRepository).findAll();
    }

    @Test
    public void getProjectById(){
        Project project1 = new Project();
        project1.setName("projekt");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project1));

        Project result = projectService.getProjectById(1L);
        assertEquals("projekt", result.getName());
        verify(projectRepository).findById(1L);
    }

    @Test
    public void createProject(){
        Project project1 = new Project();
        project1.setName("projekt");

        when(projectRepository.save(project1)).thenReturn(project1);

        Project result = projectService.createProject(project1);
        assertEquals("projekt", result.getName());
        verify(projectRepository).save(project1);


    }

    @Test
    public void deleteProjectById(){
        projectService.deleteProjectById(1L);
        verify(projectRepository).deleteById(1L);

    }

    @Test
    public void updateProject(){
        Project oldProject = new Project();
        oldProject.setName("projekt");

        Project updatedProject = new Project();
        updatedProject.setName("updated projekt");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(oldProject));
        when(projectRepository.save(oldProject)).thenReturn(oldProject);

        Project result = projectService.updateProject(1L, updatedProject);
        assertEquals("updated projekt", result.getName());

        verify(projectRepository).findById(1L);
        verify(projectRepository).save(oldProject);

    }

    @Test
    public void shouldThrowWhenProjectNotFound(){
        when(projectRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> projectService.getProjectById(99L));

        verify(projectRepository).findById(99L);

    }

}
