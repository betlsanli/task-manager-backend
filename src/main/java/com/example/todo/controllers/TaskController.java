package com.example.todo.controllers;

import com.example.todo.dto.AppUserDTO;
import com.example.todo.dto.TaskDTO;
import com.example.todo.dto.TasklistDTO;
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

    @Autowired
    public TaskController(TaskService taskService, AppUserService appUserService, TasklistService tasklistService) {
        this.taskService = taskService;
        this.appUserService = appUserService;
        this.tasklistService = tasklistService;
    }

    @GetMapping("/{userId}")
    public List<TaskDTO> getAllTaskByUser(@PathVariable UUID userId) {
        AppUserDTO user = appUserService.getById(userId);
        List<TasklistDTO> tasklists = tasklistService.getAllByUser(user);
        return taskService.getAllByTasklists(tasklists);
    }

    // duplicate of "/tasklist/{listId}/tasks"

    @GetMapping("/{listId}")
    public List<TaskDTO> getAllTaskByTasklist(@PathVariable UUID listId) {
        TasklistDTO tl = tasklistService.getById(listId);
        return taskService.getAllByTasklist(tl);
    }

    @GetMapping("/{taskId}")
    public TaskDTO getTaskById(@PathVariable UUID taskId) {
        return taskService.getById(taskId);
    }
}
