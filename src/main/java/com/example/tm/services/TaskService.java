package com.example.tm.services;


import com.example.tm.dto.AppUser.AppUserMapper;
import com.example.tm.dto.Project.ProjectTaskPriorityCountDTO;
import com.example.tm.dto.Project.ProjectTaskStatusCountDTO;
import com.example.tm.dto.Task.TaskRequestDTO;
import com.example.tm.dto.Task.TaskMapper;
import com.example.tm.dto.Task.TaskResponseDTO;
import com.example.tm.entities.AppUser;
import com.example.tm.entities.Project;
import com.example.tm.entities.Task;
import com.example.tm.repositories.AppUserRepository;
import com.example.tm.repositories.ProjectRepository;
import com.example.tm.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;
    private final TaskMapper taskMapper;


    @Autowired
    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, AppUserRepository appUserRepository, AppUserMapper appUserMapper, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.appUserRepository = appUserRepository;
        this.appUserMapper = appUserMapper;
        this.taskMapper = taskMapper;
    }

    public List<TaskResponseDTO> getAll() {
        return taskMapper.toDtos(taskRepository.findAll());
    }

    public TaskResponseDTO getById(UUID id) {
        return taskMapper.toDto(taskRepository.findById(id).orElseThrow());
    }

    public List<TaskResponseDTO> getAllByProjectId(UUID projectId) {
        return taskMapper.toDtos(taskRepository.findTasksByProjectId(projectId));
    }

    public List<TaskResponseDTO> getAllByUser(UUID userId) {
        return taskMapper.toDtos(taskRepository.findTasksByAssigneeId(userId));
    }

    public List<TaskResponseDTO> getAllByIds(List<UUID> ids) {
        return taskMapper.toDtos(taskRepository.findAllById(ids));
    }

    public boolean deleteById(UUID id) {
        getById(id);
        taskRepository.deleteById(id);
        return true;
    }

    public long getTotalCount() {
        return taskRepository.count();
    }
    public long getTotalCountByProjectId(UUID projectId) {
        return taskRepository.countAllByProjectId(projectId);
    }
    public long getTotalCountByUserId(UUID userId) {
        AppUser user = appUserRepository.findById(userId).orElseThrow();
        return taskRepository.countAllByAssigneesContaining(user);
    }

    public List<ProjectTaskStatusCountDTO> getTaskStatusCount(UUID projectId) {
        return taskRepository.findTaskCountByProjectIdGroupedByStatus(projectId);
    }

    public List<ProjectTaskPriorityCountDTO> getTaskPriorityCount(UUID projectId) {
        return taskRepository.findTaskCountByProjectIdGroupedByPriority(projectId);
    }

    @Transactional
    public TaskResponseDTO createTask(TaskRequestDTO newTask) {
        List<AppUser> users = new ArrayList<>();
        if(newTask.assignees() != null || !newTask.assignees().isEmpty()){
            List<UUID> assigneeIds = newTask.assignees().stream()
                    .map(user -> user.userId()) // Assuming getUserID() gives the user ID from the AppUserDTO
                    .collect(Collectors.toList());

            users = appUserRepository.findAllById(assigneeIds);
        }
        Project prj = projectRepository.findById(newTask.projectId()).orElseThrow();
        Task toSave = taskMapper.toEntity(newTask);
        toSave.setProject(prj);
        toSave.setAssignees(users);
        return taskMapper.toDto(taskRepository.save(toSave));
    }

    @Transactional
    public TaskResponseDTO updateTask(UUID taskId, TaskRequestDTO updatedTask) {
        Task oldTask = taskRepository.findById(taskId).orElseThrow();
        List<AppUser> oldAssignees = oldTask.getAssignees();
        List<UUID> assigneeIds = updatedTask.assignees().stream()
                .map(user -> user.userId()) // Assuming getUserID() gives the user ID from the AppUserDTO
                .collect(Collectors.toList());

        List<AppUser> newAssignees = appUserRepository.findAllById(assigneeIds);
        Project prj = projectRepository.findById(updatedTask.projectId()).orElseThrow();

        Task toSave = taskMapper.toEntity(updatedTask);
        toSave.setId(taskId);
        toSave.setProject(prj);
        toSave.setAssignees(newAssignees);
        handleAssigneeUpdate(oldAssignees,newAssignees,oldTask);

        return taskMapper.toDto(taskRepository.save(toSave));
    }

    public void handleAssigneeUpdate(List<AppUser> oldAssignees, List<AppUser> newAssignees, Task oldTask) {
        for(AppUser oldAssignee : oldAssignees) {
            if(!newAssignees.contains(oldAssignee)) {
                oldAssignee.removeAssignedTask(oldTask);
            }
        }
    }

}
