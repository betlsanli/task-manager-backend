package com.example.tm.services;


import com.example.tm.dto.Task.TaskRequestDTO;
import com.example.tm.dto.Task.TaskMapper;
import com.example.tm.dto.Task.TaskResponseDTO;
import com.example.tm.entities.Project;
import com.example.tm.entities.Task;
import com.example.tm.repositories.ProjectRepository;
import com.example.tm.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;


    @Autowired
    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
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
        Task toSave = taskMapper.toEntity(newTask);
        return taskMapper.toDto(taskRepository.save(toSave));
    }

    @Transactional
    public TaskResponseDTO updateTask(UUID taskId, TaskRequestDTO updatedTask) {
        Project prj = projectRepository.findById(updatedTask.projectId()).orElseThrow();
        Task toSave = taskMapper.toEntity(updatedTask);
        toSave.setId(taskId);
        toSave.setProject(prj);
        return taskMapper.toDto(taskRepository.save(toSave));
    }

}
