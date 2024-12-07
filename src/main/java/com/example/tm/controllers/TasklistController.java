package com.example.tm.controllers;

import com.example.tm.dto.request.create.ProjectCreate;
import com.example.tm.dto.request.update.ProjectUpdate;
import com.example.tm.entities.AppUser;
import com.example.tm.entities.Project;
import com.example.tm.services.AppUserService;
import com.example.tm.services.ProjectService;
import com.example.tm.services.create.ProjectCreateService;
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

    private final ProjectService projectService;
    private final AppUserService appUserService;
    private static final Logger log = LoggerFactory.getLogger(TasklistController.class);
    private final ProjectCreateService projectCreateService;

    @Autowired
    public TasklistController(ProjectService projectService, AppUserService appUserService, ProjectCreateService projectCreateService) {
        this.projectService = projectService;
        this.appUserService = appUserService;
        this.projectCreateService = projectCreateService;
    }

    @GetMapping("/all-list")
    public ResponseEntity<List<Project>> getAllTasklists() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(projectService.getAll());
        }catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/of-user/{userId}")
    public ResponseEntity<List<Project>> getAllTasklistByUserId(@PathVariable UUID userId) {
        try {
            if(userId == null)
                throw new IllegalArgumentException("User id cannot be null");
            AppUser user = appUserService.getById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(projectService.getAllByUser(user));
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
    public ResponseEntity<Project> getTasklistByListId(@PathVariable UUID listId) {
        try {
            if(listId == null)
                throw new IllegalArgumentException("List id cannot be null");
            return ResponseEntity.status(HttpStatus.OK).body(projectService.getById(listId));
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

    @PostMapping("/create-tasklist")
    public ResponseEntity<Project> createTasklist(@RequestBody @Valid ProjectCreate tasklist) {
        try {
            if(tasklist == null)
                throw new IllegalArgumentException("Project cannot be null");
            return ResponseEntity.status(HttpStatus.CREATED).body(projectCreateService.createProject(tasklist));
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
            boolean isDeleted = projectService.deleteById(listId);
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
    public ResponseEntity<Project> updateTasklist(@PathVariable UUID listId, @RequestBody @Valid ProjectUpdate tasklist) {
        try {
            if (listId == null || tasklist == null)
                throw new IllegalArgumentException("Project cannot be null");
            return ResponseEntity.status(HttpStatus.CREATED).body(projectCreateService.updateProject(listId, tasklist));
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
