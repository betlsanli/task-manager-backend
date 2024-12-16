package com.example.tm.controllers;

import com.example.tm.dto.ProjectAssignment.ProjectAssignmentRequestDTO;
import com.example.tm.dto.ProjectAssignment.ProjectAssignmentResponseDTO;
import com.example.tm.services.ProjectAssignmentService;
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
@RequestMapping("/team")
@Validated
public class ProjectAssignmentController {

    private final ProjectAssignmentService projectAssignmentService;
    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    public ProjectAssignmentController(ProjectAssignmentService projectAssignmentService) {
        this.projectAssignmentService = projectAssignmentService;
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<List<ProjectAssignmentResponseDTO>> getAllByProjectId(@PathVariable UUID projectId) {
        try {
            if(projectId == null)
                throw new IllegalArgumentException("Project id cannot be null");
            return ResponseEntity.status(HttpStatus.OK).body(projectAssignmentService.getAllProjectAssignmentsByProjectId(projectId));
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

    //manager
    @PostMapping("/add-member")
    public ResponseEntity<ProjectAssignmentResponseDTO> createProjectAssignment(@RequestBody @Valid ProjectAssignmentRequestDTO projectAssignmentRequestDTO) {
        try {
            if(projectAssignmentRequestDTO == null)
                throw new IllegalArgumentException("Project Assignment DTO cannot be null");
            return ResponseEntity.status(HttpStatus.CREATED).body(projectAssignmentService.createProjectAssignment(projectAssignmentRequestDTO));
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

    //manager
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteProjectAssignment(@RequestParam UUID projectId, @RequestParam UUID userId, @RequestParam String role) {
        try {
            if (projectId == null || userId == null || role == null || !role.isEmpty())
                throw new IllegalArgumentException("Delete parameters cannot be null or empty");
            boolean isDeleted = projectAssignmentService.deleteProjectAssignment(projectId,role,userId);
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

}
