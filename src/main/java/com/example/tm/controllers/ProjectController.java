package com.example.tm.controllers;

import com.example.tm.dto.Project.ProjectRequestDTO;
import com.example.tm.dto.Project.ProjectResponseDTO;
import com.example.tm.dto.ProjectAssignment.ProjectAssignmentResponseDTO;
import com.example.tm.enums.Role.Role;
import com.example.tm.security.CustomUserDetails;
import com.example.tm.services.ProjectAssignmentService;
import com.example.tm.services.ProjectService;
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
@RequestMapping("/project")
@Validated
public class ProjectController {

    private final ProjectService projectService;
    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);
    private final ProjectAssignmentService projectAssignmentService;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectAssignmentService projectAssignmentService) {
        this.projectService = projectService;
        this.projectAssignmentService = projectAssignmentService;
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-project")
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects(HttpSession session) {
        try {

            if (session == null || session.getAttribute("user") == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");
            if (userDetails.hasAuthority("ROLE_ADMIN")) {
                return ResponseEntity.status(HttpStatus.OK).body(projectService.getAll());
            } else {
                //for normal users, get only assigned projetcs
                List<UUID> assignedProjectsIds = projectAssignmentService.getAllProjectAssignmentsByUserId(userDetails.getUserId())
                        .stream().map(ProjectAssignmentResponseDTO::projectId).toList();
                return ResponseEntity.status(HttpStatus.OK).body(projectService.getAllByIds(assignedProjectsIds));
            }

        }catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable UUID projectId, HttpSession session) {
        try {
            if(projectId == null)
                throw new IllegalArgumentException("Project id cannot be null");

            if (session == null || session.getAttribute("user") == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");
            if (userDetails.hasAuthority("ROLE_ADMIN")) {
                return ResponseEntity.status(HttpStatus.OK).body(projectService.getById(projectId));
            } else {
                //for normal users, check if assigned
                if(projectAssignmentService.isProjectAssignedToUser(projectId, userDetails.getUserId())){
                    return ResponseEntity.status(HttpStatus.OK).body(projectService.getById(projectId));
                }
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

    @PostMapping("/create-project")
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody @Valid ProjectRequestDTO project, HttpSession session) {
        try {
            if(project == null)
                throw new IllegalArgumentException("Project cannot be null");

            if (session == null || session.getAttribute("user") == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");
            if (userDetails.hasAuthority("ROLE_ADMIN")) {
                return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(project));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

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
    public ResponseEntity<Boolean> deleteProject(@PathVariable UUID projectId, HttpSession session) {
        try {
            if (projectId == null)
                throw new IllegalArgumentException("Project id cannot be null");

            if (session == null || session.getAttribute("user") == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");
            if (userDetails.hasAuthority("ROLE_ADMIN")) {
                boolean isDeleted = projectService.deleteById(projectId);
                return ResponseEntity.status(HttpStatus.OK).body(isDeleted);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

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
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable UUID projectId, @RequestBody @Valid ProjectRequestDTO project, HttpSession session) {
        try {
            if (projectId == null || project == null)
                throw new IllegalArgumentException("Project cannot be null");

            if (session == null || session.getAttribute("user") == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");
            if (userDetails.hasAuthority("ROLE_ADMIN")
                    || projectAssignmentService.userHasProjectRole(projectId, userDetails.getUserId(), Role.MANAGER))
            {
                return ResponseEntity.status(HttpStatus.CREATED).body(projectService.updateProject(projectId, project));
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

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
