package org.example.projectmanagerapp.controller;

import org.example.projectmanagerapp.entity.Task;
import org.example.projectmanagerapp.entity.Project;
import org.example.projectmanagerapp.repository.TaskRepository;
import org.example.projectmanagerapp.repository.ProjectRepository;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskController(TaskRepository taskRepository, ProjectRepository projectRepository){
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @GetMapping
    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task){
        return taskRepository.save(task);
    }

    @PostMapping("/project/{projectId}")
    public Task createTask(
            @PathVariable Long projectId,
            @RequestBody Task task
    ){
        Project project = projectRepository.findById(projectId)
                .orElseThrow();

        task.setProject(project);

        return taskRepository.save(task);
    }
}