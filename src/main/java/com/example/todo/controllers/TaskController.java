package com.example.todo.controllers;

import com.example.todo.dto.request.create.TaskCreate;
import com.example.todo.dto.request.update.TaskUpdate;
import com.example.todo.entities.Task;
import com.example.todo.services.TaskService;
import com.example.todo.services.create.TaskCreateService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    private final TaskService taskService;
    private final TaskCreateService taskCreateService;
    private static final Logger log =  LoggerFactory.getLogger(TaskController.class);


    @Autowired
    public TaskController(TaskService taskService, TaskCreateService taskCreateService) {
        this.taskService = taskService;
        this.taskCreateService = taskCreateService;
    }

    @GetMapping("/all-task")
    public ResponseEntity<List<Task>> getAllTask() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.getAll());
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/of-user/{userId}")
    public ResponseEntity<List<Task>> getAllTaskByUser(@PathVariable UUID userId) {
        try {
            if(userId == null)
                throw new IllegalArgumentException("User id cannot be null");
            return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllByUser(userId));
        }catch (IllegalArgumentException ie) {
            log.error(ie.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (NoSuchElementException nse) {
            log.error(nse.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // duplicate of "/tasklist/{listId}/tasks"

    @GetMapping("/of-list/{listId}")
    public ResponseEntity<List<Task>> getAllTaskByTasklist(@PathVariable UUID listId) {
        try {
            if(listId == null)
                throw new IllegalArgumentException("List id cannot be null");
            return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllByTasklist(listId));
        }catch (IllegalArgumentException iae){
            log.error(iae.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch(NoSuchElementException nse){
            log.error(nse.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable UUID taskId) {
        try {
            if(taskId == null)
                throw new IllegalArgumentException("Task id cannot be null");
            return ResponseEntity.status(HttpStatus.OK).body(taskService.getById(taskId));
        }catch (IllegalArgumentException iae){
            log.error(iae.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (NoSuchElementException nse){
            log.error(nse.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/create-task")
    public ResponseEntity<Task> createTask(@RequestBody @Valid TaskCreate taskCreate) {
        try {
            if(taskCreate == null)
                throw new IllegalArgumentException("TaskCreate cannot be null");
            return ResponseEntity.status(HttpStatus.CREATED).body(taskCreateService.createTask(taskCreate));
        }catch (IllegalArgumentException iae){
            log.error(iae.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (NoSuchElementException nse){
            log.error(nse.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable UUID taskId) {
        try {
            if(taskId == null)
                throw new IllegalArgumentException("Task id cannot be null");
            boolean isDeleted = taskService.deleteById(taskId);
            return ResponseEntity.status(HttpStatus.OK).body(isDeleted);
        }catch (IllegalArgumentException iae){
            log.error(iae.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (NoSuchElementException nse){
            log.error(nse.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/edit/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable UUID taskId, @RequestBody @Valid TaskUpdate taskUpdate) {
        try {
            if(taskId == null || taskUpdate == null)
                throw new IllegalArgumentException("Parameters cannot be null");
            return ResponseEntity.status(HttpStatus.CREATED).body(taskCreateService.updateTask(taskId, taskUpdate));
        }catch (IllegalArgumentException iae){
            log.error(iae.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (NoSuchElementException nse){
            log.error(nse.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
