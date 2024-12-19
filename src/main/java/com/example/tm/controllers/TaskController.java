package com.example.tm.controllers;

import com.example.tm.dto.Task.TaskRequestDTO;
import com.example.tm.dto.Task.TaskResponseDTO;
import com.example.tm.enums.Role.Role;
import com.example.tm.security.CustomUserDetails;
import com.example.tm.services.ProjectAssignmentService;
import com.example.tm.services.TaskService;
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
@RequestMapping("/task")
@Validated
public class TaskController {

    private final TaskService taskService;
    private static final Logger log =  LoggerFactory.getLogger(TaskController.class);
    private final ProjectAssignmentService projectAssignmentService;


    @Autowired
    public TaskController(TaskService taskService, ProjectAssignmentService projectAssignmentService) {
        this.taskService = taskService;
        this.projectAssignmentService = projectAssignmentService;
    }

    @GetMapping("/all-task")
    public ResponseEntity<List<TaskResponseDTO>> getAllTask(HttpSession session) {
        try {

            if (session == null || session.getAttribute("user") == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");

            if (userDetails.hasAuthority("ROLE_ADMIN")) {
                return ResponseEntity.status(HttpStatus.OK).body(taskService.getAll());
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get-total-count")
    public ResponseEntity<Long> getTotalCount(HttpSession session) {
        try {
            if (session == null || session.getAttribute("user") == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");
            if (userDetails.hasAuthority("ROLE_ADMIN")) {
                return ResponseEntity.status(HttpStatus.OK).body(taskService.getTotalCount());
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/of-user/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> getAllTaskByUser(@PathVariable UUID userId,HttpSession session) {
        try {
            if(userId == null)
                throw new IllegalArgumentException("User id cannot be null");

            if (session == null || session.getAttribute("user") == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");

            if (userDetails.hasAuthority("ROLE_ADMIN") || userDetails.getUserId() == userId) {
                return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllByUser(userId));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

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

    @GetMapping("/of-project/{projectId}")
    public ResponseEntity<List<TaskResponseDTO>> getAllTaskByProject(@PathVariable UUID projectId, HttpSession session) {
        try {
            if(projectId == null)
                throw new IllegalArgumentException("Project id cannot be null");

            if (session == null || session.getAttribute("user") == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");

            if (userDetails.hasAuthority("ROLE_ADMIN") || projectAssignmentService.isProjectAssignedToUser(projectId, userDetails.getUserId())) {
                return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllByProjectId(projectId));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

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
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable UUID taskId, HttpSession session) {
        try {
            if(taskId == null)
                throw new IllegalArgumentException("Task id cannot be null");

            if (session == null || session.getAttribute("user") == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");
            TaskResponseDTO taskResponseDTO = taskService.getById((taskId));
            if (userDetails.hasAuthority("ROLE_ADMIN") || projectAssignmentService.isProjectAssignedToUser(taskResponseDTO.projectId(), userDetails.getUserId())) {
                return ResponseEntity.status(HttpStatus.OK).body(taskResponseDTO);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
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
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody @Valid TaskRequestDTO taskCreate, HttpSession session) {
        try {
            if(taskCreate == null)
                throw new IllegalArgumentException("Task cannot be null");

            if (session == null || session.getAttribute("user") == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");
            if (userDetails.hasAuthority("ROLE_ADMIN") || projectAssignmentService.userHasProjectRole(taskCreate.projectId(), userDetails.getUserId(), Role.MANAGER)) {
                return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskCreate));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

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
    public ResponseEntity<Boolean> deleteTask(@PathVariable UUID taskId, HttpSession session) {
        try {
            if(taskId == null)
                throw new IllegalArgumentException("Task id cannot be null");

            if (session == null || session.getAttribute("user") == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");
            TaskResponseDTO toDelete = taskService.getById((taskId));
            if (userDetails.hasAuthority("ROLE_ADMIN") || projectAssignmentService.userHasProjectRole(toDelete.projectId(), userDetails.getUserId(), Role.MANAGER)) {
                boolean isDeleted = taskService.deleteById(taskId);
                return ResponseEntity.status(HttpStatus.OK).body(isDeleted);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

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
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable UUID taskId, @RequestBody @Valid TaskRequestDTO taskUpdate, HttpSession session) {
        try {
            if(taskId == null || taskUpdate == null)
                throw new IllegalArgumentException("Parameters cannot be null");

            if (session == null || session.getAttribute("user") == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");
            if (userDetails.hasAuthority("ROLE_ADMIN") || projectAssignmentService.isProjectAssignedToUser(taskUpdate.projectId(), userDetails.getUserId())) {
                return ResponseEntity.status(HttpStatus.CREATED).body(taskService.updateTask(taskId, taskUpdate));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

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
