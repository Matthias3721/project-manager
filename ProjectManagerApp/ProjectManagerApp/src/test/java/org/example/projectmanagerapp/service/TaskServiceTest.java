package org.example.projectmanagerapp.service;

import org.example.projectmanagerapp.entity.Project;
import org.example.projectmanagerapp.entity.Task;
import org.example.projectmanagerapp.repository.ProjectRepository;
import org.example.projectmanagerapp.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskServiceTest {

    TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);

    TaskService taskService = new TaskService(taskRepository, projectRepository);

    @Test
    public void getTasks(){
        Task task = new Task();

        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<Task> result = taskService.getTasks();

        assertEquals(1, result.size());

        verify(taskRepository).findAll();

    }

    @Test
    public void getTaskById(){
        Task task = new Task();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(1L);

        assertEquals(task, result);

        verify(taskRepository).findById(1L);
    }

    @Test
    public void createTaskForProject(){
        Task task = new Task();
        task.setTitle("zadanie");

        Project project = new Project();

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.createTaskForProject(1L, task);

        assertEquals(project, result.getProject());

        verify(projectRepository).findById(1L);
        verify(taskRepository).save(task);
    }

    @Test
    public void updateTask(){
        Task oldTask = new Task();
        oldTask.setTitle("zadanie");

        Task newTask = new Task();
        newTask.setTitle("nowe zadanie");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(oldTask));
        when(taskRepository.save(oldTask)).thenReturn(oldTask);

        Task result = taskService.updateTask(1L, newTask);

        assertEquals("nowe zadanie", result.getTitle());
        verify(taskRepository).findById(1L);
        verify(taskRepository).save(oldTask);
    }


    @Test
    public void deleteTaskById(){
        taskService.deleteTaskById(1L);
        verify(taskRepository).deleteById(1L);
    }

    @Test
    public void shouldThrowWhenTaskNotFound(){

        when(taskRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () ->taskService.getTaskById(99L));
        verify(taskRepository).findById(99L);
    }

}
