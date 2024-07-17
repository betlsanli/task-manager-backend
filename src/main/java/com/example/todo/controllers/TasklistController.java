package com.example.todo.controllers;

import com.example.todo.dto.request.create.TasklistCreate;
import com.example.todo.dto.request.update.TasklistUpdate;
import com.example.todo.entities.AppUser;
import com.example.todo.entities.Tasklist;
import com.example.todo.services.AppUserService;
import com.example.todo.services.TasklistService;
import com.example.todo.services.create.TasklistCreateService;
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
@RequestMapping("/tasklist")
@Validated
public class TasklistController {

    private final TasklistService tasklistService;
    private final AppUserService appUserService;
    private static final Logger log = LoggerFactory.getLogger(TasklistController.class);
    private final TasklistCreateService tasklistCreateService;

    @Autowired
    public TasklistController(TasklistService tasklistService, AppUserService appUserService, TasklistCreateService tasklistCreateService) {
        this.tasklistService = tasklistService;
        this.appUserService = appUserService;
        this.tasklistCreateService = tasklistCreateService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Tasklist>> getAllTasklistByUserId(@PathVariable UUID userId) {
        try {
            if(userId == null)
                throw new IllegalArgumentException("User id cannot be null");
            AppUser user = appUserService.getById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(tasklistService.getAllByUser(user));
        }catch (IllegalArgumentException iae){
            log.error(iae.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch(NoSuchElementException nsee){
            log.error(nsee.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch(Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{listId}")
    public ResponseEntity<Tasklist> getTasklistByListId(@PathVariable UUID listId) {
        try {
            if(listId == null)
                throw new IllegalArgumentException("List id cannot be null");
            return ResponseEntity.status(HttpStatus.OK).body(tasklistService.getById(listId));
        }catch (IllegalArgumentException iae){
            log.error(iae.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch(NoSuchElementException nsee){
            log.error(nsee.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch(Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("create-tasklist")
    public ResponseEntity<Tasklist> createTasklist(@RequestBody @Valid TasklistCreate tasklist) {
        try {
            if(tasklist == null)
                throw new IllegalArgumentException("Tasklist cannot be null");
            return ResponseEntity.status(HttpStatus.CREATED).body(tasklistCreateService.createTasklist(tasklist));
        }catch (IllegalArgumentException iae){
            log.error(iae.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch(NoSuchElementException nsee){
            log.error(nsee.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch(Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{listId}")
    public ResponseEntity<Boolean> deleteTasklist(@PathVariable UUID listId) {
        try {
            if (listId == null)
                throw new IllegalArgumentException("List id cannot be null");
            boolean isDeleted = tasklistService.deleteById(listId);
            return ResponseEntity.status(HttpStatus.OK).body(isDeleted);
        }catch (IllegalArgumentException iae){
            log.error(iae.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch(NoSuchElementException nsee){
            log.error(nsee.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch(Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/edit/{listId}")
    public ResponseEntity<Tasklist> updateTasklist(@PathVariable UUID listId, @RequestBody @Valid TasklistUpdate tasklist) {
        try {
            if (listId == null || tasklist == null)
                throw new IllegalArgumentException("Tasklist cannot be null");
            return ResponseEntity.status(HttpStatus.CREATED).body(tasklistCreateService.updateTasklist(listId, tasklist));
        }catch (IllegalArgumentException iae){
            log.error(iae.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch(NoSuchElementException nsee){
            log.error(nsee.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch(Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
