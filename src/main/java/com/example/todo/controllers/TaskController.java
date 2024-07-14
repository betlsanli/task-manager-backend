package com.example.todo.controllers;

import com.example.todo.entities.AppUser;
import com.example.todo.entities.Task;
import com.example.todo.entities.Tasklist;
import com.example.todo.services.AppUserService;
import com.example.todo.services.TaskService;
import com.example.todo.services.TasklistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final AppUserService appUserService;
    private final TasklistService tasklistService;
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    public TaskController(TaskService taskService, AppUserService appUserService, TasklistService tasklistService) {
        this.taskService = taskService;
        this.appUserService = appUserService;
        this.tasklistService = tasklistService;
    }

    @GetMapping("/{userId}")
    public List<Task> getAllTaskByUser(@PathVariable UUID userId) {
        AppUser user = appUserService.getById(userId).orElse(null);
        if (user == null) {
            log.error("User not found");
            return null;
        }
        List<Tasklist> tasklists = tasklistService.getAllByUser(user);
        return taskService.getAllByTasklists(tasklists);
    }

    // duplicate of "/tasklist/{listId}/tasks"

    @GetMapping("/{listId}")
    public List<Task> getAllTaskByTasklist(@PathVariable UUID listId) {
        Tasklist tl = tasklistService.getById(listId).orElse(null);
        if (tl == null) {
            log.error("Tasklist not found");
            return null;
        }
        return taskService.getAllByTasklist(tl);
    }

    @GetMapping("/{taskId}")
    public Task getTaskById(@PathVariable UUID taskId) {
        Task task = taskService.getById(taskId).orElse(null);
        if (task == null) {
            log.error("Task not found");
            return null;
        }
        return task;
    }
}
