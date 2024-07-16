package com.example.todo.controllers;

import com.example.todo.entities.AppUser;
import com.example.todo.entities.Tasklist;
import com.example.todo.services.AppUserService;
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
@RequestMapping("/tasklist")
public class TasklistController {

    private final TasklistService tasklistService;
    private final AppUserService appUserService;
    private static final Logger log = LoggerFactory.getLogger(TasklistController.class);

    @Autowired
    public TasklistController(TasklistService tasklistService, AppUserService appUserService) {
        this.tasklistService = tasklistService;
        this.appUserService = appUserService;
    }

    @GetMapping("/{userId}")
    public List<Tasklist> getAllTasklistByUserId(@PathVariable UUID userId) {
        AppUser user = appUserService.getById(userId);
        return tasklistService.getAllByUser(user);
    }

    // duplicate of "/task/{listId}"

//    @GetMapping("/{listId}/tasks")
//    public List<Task> getAllTaskByListId(@PathVariable UUID listId) {
//        Tasklist tl = tasklistService.getById(listId).orElse(null);
//        if(tl == null) {
//            log.error("Tasklist not found");
//            return null;
//        }
//        return taskService.getAllByTasklist(tl);
//    }

    @GetMapping("/{listId}")
    public Tasklist getTasklistByListId(@PathVariable UUID listId) {
        return tasklistService.getById(listId);
    }

}
