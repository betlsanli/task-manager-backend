package com.example.tm.controllers;

import com.example.tm.dto.Task.TaskRequestDTO;
import com.example.tm.dto.Task.TaskResponseDTO;
import com.example.tm.services.TaskService;
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
    private static final Logger log =  LoggerFactory.getLogger(TaskController.class);


    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all-task")
    public ResponseEntity<List<TaskResponseDTO>> getAllTask() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.getAll());
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/of-user/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> getAllTaskByUser(@PathVariable UUID userId) {
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

    @GetMapping("/of-project/{projectId}")
    public ResponseEntity<List<TaskResponseDTO>> getAllTaskByProject(@PathVariable UUID projectId) {
        try {
            if(projectId == null)
                throw new IllegalArgumentException("Project id cannot be null");
            return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllByProjectId(projectId));
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
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable UUID taskId) {
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
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody @Valid TaskRequestDTO taskCreate) {
        try {
            if(taskCreate == null)
                throw new IllegalArgumentException("Task cannot be null");
            return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskCreate));
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
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable UUID taskId, @RequestBody @Valid TaskRequestDTO taskUpdate) {
        try {
            if(taskId == null || taskUpdate == null)
                throw new IllegalArgumentException("Parameters cannot be null");
            return ResponseEntity.status(HttpStatus.CREATED).body(taskService.updateTask(taskId, taskUpdate));
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
