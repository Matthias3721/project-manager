package org.example.projectmanagerapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.projectmanagerapp.entity.Task;
import org.springframework.web.bind.annotation.*;
import org.example.projectmanagerapp.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Tasks", description = "Operations related to tasks")
public class TaskController {

    private final TaskService taskService;
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @Operation(summary = "Get all tasks")
    @GetMapping
    public List<Task> getTasks(){
        return taskService.getTasks();
    }

    @Operation(summary = "Add task")
    @PostMapping
    public Task createTask(@RequestBody Task task){
        return taskService.createTask(task);
    }

    @Operation(summary = "Create task for project")
    @PostMapping("/project/{projectId}")
    public Task createTask(
            @Parameter(description = "ID of the project")
            @PathVariable Long projectId,
            @RequestBody Task task
    ){
        return taskService.createTaskForProject(projectId, task);
    }

    @Operation(summary = "Get task by ID")
    @GetMapping("/{id}")
    public Task getTaskById(
            @Parameter(description = "ID of the task")
            @PathVariable Long id){
        return taskService.getTaskById(id);
    }

    @Operation(summary = "Delete task by ID")
    @DeleteMapping("/{id}")
    public void deleteTaskById(
            @Parameter(description = "ID of the task")
            @PathVariable Long id){
        taskService.deleteTaskById(id);
    }

    @Operation(summary = "Update task by ID")
    @PutMapping("/{id}")
    public Task updateTask(
            @Parameter(description = "ID of the task")
            @PathVariable Long id,
            @RequestBody Task updatedTask){
        return taskService.updateTask(id, updatedTask);
    }
}