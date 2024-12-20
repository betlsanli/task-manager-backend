package com.example.tm.controllers;

import com.example.tm.dto.ProjectAssignment.ProjectAssignmentRequestDTO;
import com.example.tm.dto.ProjectAssignment.ProjectAssignmentResponseDTO;
import com.example.tm.enums.Role.Role;
import com.example.tm.security.CustomUserDetails;
import com.example.tm.services.ProjectAssignmentService;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<List<ProjectAssignmentResponseDTO>> getAllByProjectId(@PathVariable UUID projectId, HttpSession session) {
        try {
            if(projectId == null)
                throw new IllegalArgumentException("Project id cannot be null");

            if (session == null || session.getAttribute("user") == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");

            if (userDetails.hasAuthority("ROLE_ADMIN") || projectAssignmentService.isProjectAssignedToUser(projectId, userDetails.getUserId())) {
                return ResponseEntity.status(HttpStatus.OK).body(projectAssignmentService.getAllProjectAssignmentsByProjectId(projectId));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

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

    @GetMapping("get-role/{projectId}")
    public ResponseEntity<List<String>> getRolesOfUserInProject(@PathVariable UUID projectId, HttpSession session) {
        try {
            if(projectId == null)
                throw new IllegalArgumentException("Project id cannot be null");

            if (session == null || session.getAttribute("user") == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");

            if (projectAssignmentService.isProjectAssignedToUser(projectId, userDetails.getUserId())) {
                List<ProjectAssignmentResponseDTO> projectAssignments = projectAssignmentService.getAllProjectAssignmentsByProject_IdAndUserId(projectId, userDetails.getUserId());
                List<String> roles = projectAssignments.stream().map(ProjectAssignmentResponseDTO::roleStr).toList();
                return ResponseEntity.status(HttpStatus.OK).body(roles);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

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
    public ResponseEntity<ProjectAssignmentResponseDTO> createProjectAssignment(@RequestBody @Valid ProjectAssignmentRequestDTO projectAssignmentRequestDTO,HttpSession session) {
        try {
            if(projectAssignmentRequestDTO == null)
                throw new IllegalArgumentException("Project Assignment DTO cannot be null");

            if (session == null || session.getAttribute("user") == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");

            if (userDetails.hasAuthority("ROLE_ADMIN") || projectAssignmentService.userHasProjectRole(projectAssignmentRequestDTO.projectId(), userDetails.getUserId(), Role.MANAGER)) {
                return ResponseEntity.status(HttpStatus.CREATED).body(projectAssignmentService.createProjectAssignment(projectAssignmentRequestDTO));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

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
    public ResponseEntity<Boolean> deleteProjectAssignment(@RequestParam UUID projectId, @RequestParam UUID userId, @RequestParam String role, HttpSession session) {
        try {
            if (projectId == null || userId == null || role == null || !role.isEmpty())
                throw new IllegalArgumentException("Delete parameters cannot be null or empty");

            if (session == null || session.getAttribute("user") == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");

            if (userDetails.hasAuthority("ROLE_ADMIN") || projectAssignmentService.userHasProjectRole(projectId, userDetails.getUserId(), Role.MANAGER)) {
                boolean isDeleted = projectAssignmentService.deleteProjectAssignment(projectId,role,userId);
                return ResponseEntity.status(HttpStatus.OK).body(isDeleted);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

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
