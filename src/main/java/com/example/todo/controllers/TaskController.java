package com.example.todo.controllers;

import com.example.todo.dto.request.create.TaskCreate;
import com.example.todo.entities.AppUser;
import com.example.todo.entities.Task;
import com.example.todo.entities.Tasklist;
import com.example.todo.services.AppUserService;
import com.example.todo.services.TaskService;
import com.example.todo.services.TasklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final AppUserService appUserService;
    private final TasklistService tasklistService;

    @Autowired
    public TaskController(TaskService taskService, AppUserService appUserService, TasklistService tasklistService) {
        this.taskService = taskService;
        this.appUserService = appUserService;
        this.tasklistService = tasklistService;
    }

    @GetMapping("/{userId}")
    public List<Task> getAllTaskByUser(@PathVariable UUID userId) {
        AppUser user = appUserService.getById(userId);
        List<Tasklist> tasklists = tasklistService.getAllByUser(user);
        return taskService.getAllByTasklists(tasklists);
    }

    // duplicate of "/tasklist/{listId}/tasks"

    @GetMapping("/{listId}")
    public List<Task> getAllTaskByTasklist(@PathVariable UUID listId) {
        Tasklist tl = tasklistService.getById(listId);
        return taskService.getAllByTasklist(tl);
    }

    @GetMapping("/{taskId}")
    public Task getTaskById(@PathVariable UUID taskId) {
        return taskService.getById(taskId);
    }

    @PostMapping("/create-task")
    public Task createTask(@RequestBody TaskCreate taskCreate) {
        return taskService.createTask(taskCreate);
    }

    @DeleteMapping("/delete/{taskId}")
    public void deleteTask(@PathVariable UUID taskId) {
        taskService.deleteById(taskId);
    }
}
