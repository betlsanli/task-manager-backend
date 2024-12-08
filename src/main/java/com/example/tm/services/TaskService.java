package com.example.tm.services;


import com.example.tm.dto.AppUser.AppUserMapper;
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

    @Transactional
    public TaskResponseDTO createTask(TaskRequestDTO newTask) {
        List<AppUser> users = new ArrayList<>();
        if(newTask.assignees() != null || !newTask.assignees().isEmpty()){
            users = appUserRepository.findAllById(newTask.assignees());
        }
        Task toSave = taskMapper.toEntity(newTask);
        toSave.setAssignees(users);
        return taskMapper.toDto(taskRepository.save(toSave));
    }

    @Transactional
    public TaskResponseDTO updateTask(UUID taskId, TaskRequestDTO updatedTask) {
        Task oldTask = taskRepository.findById(taskId).orElseThrow();
        List<AppUser> oldAssignees = oldTask.getAssignees();
        List<AppUser> newAssignees = appUserRepository.findAllById(updatedTask.assignees());
        Project prj = projectRepository.findById(updatedTask.projectId()).orElseThrow();

        Task toSave = taskMapper.toEntity(updatedTask);
        toSave.setId(taskId);
        toSave.setProject(prj);
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
