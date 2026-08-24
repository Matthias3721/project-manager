package org.example.projectmanagerapp.service;

import org.example.projectmanagerapp.entity.Project;
import org.example.projectmanagerapp.entity.Task;
import org.example.projectmanagerapp.repository.ProjectRepository;
import org.example.projectmanagerapp.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository){
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    public Task createTask(Task task){
        return taskRepository.save(task);
    }

    public Task createTaskForProject(Long projectId, Task task){
        Project project = projectRepository.findById(projectId)
                .orElseThrow();

        task.setProject(project);

        return taskRepository.save(task);
    }

    public Task getTaskById(Long id){
        return taskRepository.findById(id)
                .orElseThrow();
    }

    public void deleteTaskById(Long id){
        taskRepository.deleteById(id);
    }

    public Task updateTask(Long id, Task updatedTask){
        Task existingTask = getTaskById(id);

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setTaskType(updatedTask.getTaskType());

        return taskRepository.save(existingTask);
    }
}
