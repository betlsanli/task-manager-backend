package com.example.tm.controllers;

import com.example.tm.dto.Project.ProjectRequestDTO;
import com.example.tm.dto.Project.ProjectResponseDTO;
import com.example.tm.services.AppUserService;
import com.example.tm.services.ProjectService;
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
@RequestMapping("/project")
@Validated
public class ProjectController {

    private final ProjectService projectService;
    private final AppUserService appUserService;
    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    public ProjectController(ProjectService projectService, AppUserService appUserService) {
        this.projectService = projectService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all-project")
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(projectService.getAll());
        }catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable UUID projectId) {
        try {
            if(projectId == null)
                throw new IllegalArgumentException("Project id cannot be null");
            return ResponseEntity.status(HttpStatus.OK).body(projectService.getById(projectId));
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

    @PostMapping("/create-project")
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody @Valid ProjectRequestDTO project) {
        try {
            if(project == null)
                throw new IllegalArgumentException("Project cannot be null");
            return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(project));
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

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<Boolean> deleteProject(@PathVariable UUID projectId) {
        try {
            if (projectId == null)
                throw new IllegalArgumentException("Project id cannot be null");
            boolean isDeleted = projectService.deleteById(projectId);
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

    @PutMapping("/edit/{projectId}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable UUID projectId, @RequestBody @Valid ProjectRequestDTO project) {
        try {
            if (projectId == null || project == null)
                throw new IllegalArgumentException("Project cannot be null");
            return ResponseEntity.status(HttpStatus.CREATED).body(projectService.updateProject(projectId, project));
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
