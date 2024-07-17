package com.example.todo.controllers;

import com.example.todo.dto.request.create.TaskCreate;
import com.example.todo.dto.request.update.TaskUpdate;
import com.example.todo.entities.Task;
import com.example.todo.services.TaskService;
import com.example.todo.services.create.TaskCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final TaskCreateService taskCreateService;

    @Autowired
    public TaskController(TaskService taskService, TaskCreateService taskCreateService) {
        this.taskService = taskService;
        this.taskCreateService = taskCreateService;
    }

    @GetMapping("/{userId}")
    public List<Task> getAllTaskByUser(@PathVariable UUID userId) {
        return taskService.getAllByUser(userId);
    }

    // duplicate of "/tasklist/{listId}/tasks"

    @GetMapping("/{listId}")
    public List<Task> getAllTaskByTasklist(@PathVariable UUID listId) {
        return taskService.getAllByTasklist(listId);
    }

    @GetMapping("/{taskId}")
    public Task getTaskById(@PathVariable UUID taskId) {
        return taskService.getById(taskId);
    }

    @PostMapping("/create-task")
    public Task createTask(@RequestBody TaskCreate taskCreate) {
        return taskCreateService.createTask(taskCreate);
    }

    @DeleteMapping("/delete/{taskId}")
    public void deleteTask(@PathVariable UUID taskId) {
        taskService.deleteById(taskId);
    }

    @PutMapping("/edit/{taskId}")
    public Task updateTask(@PathVariable UUID taskId, @RequestBody TaskUpdate taskUpdate) {
        return taskCreateService.updateTask(taskId, taskUpdate);
    }
}
