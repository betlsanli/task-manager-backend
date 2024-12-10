package com.example.tm.services;

import com.example.tm.dto.ProjectAssignment.ProjectAssignmentMapper;
import com.example.tm.dto.ProjectAssignment.ProjectAssignmentRequestDTO;
import com.example.tm.dto.ProjectAssignment.ProjectAssignmentResponseDTO;
import com.example.tm.entities.ProjectAssignment;
import com.example.tm.entities.UserProjectRoleID;
import com.example.tm.repositories.AppUserRepository;
import com.example.tm.repositories.ProjectAssignmentRepository;
import com.example.tm.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectAssignmentService {
    private final ProjectAssignmentRepository projectAssignmentRepository;
    private final AppUserRepository appUserRepository;
    private final ProjectRepository projectRepository;
    private final ProjectAssignmentMapper projectAssignmentMapper;

    @Autowired
    public ProjectAssignmentService(ProjectAssignmentRepository projectAssignmentRepository, ProjectAssignmentMapper projectAssignmentMapper, ProjectAssignmentRepository projectASsignmentRepository, AppUserRepository appUserRepository, ProjectRepository projectRepository) {
        this.projectAssignmentRepository = projectAssignmentRepository;
        this.projectAssignmentMapper = projectAssignmentMapper;
        this.appUserRepository = appUserRepository;
        this.projectRepository = projectRepository;
    }

    public List<ProjectAssignmentResponseDTO> getAllProjectAssignmentsByProjectId(UUID projectId) {
        return projectAssignmentMapper.toDtos(projectAssignmentRepository.findAllByProject_Id(projectId));
    }

    public List<ProjectAssignmentResponseDTO> getAllProjectAssignmentsByUserId(UUID userId) {
        return projectAssignmentMapper.toDtos(projectAssignmentRepository.findAllByUser_Id(userId));
    }

    public List<ProjectAssignmentResponseDTO> getAllProjectAssignments() {
        return projectAssignmentMapper.toDtos(projectAssignmentRepository.findAll());
    }

    public boolean deleteProjectAssignment(UUID projectId, String role, UUID userId) {

        ProjectAssignment projectAssignment = projectAssignmentRepository.findById(new UserProjectRoleID(userId, projectId, role)).orElseThrow();
        projectAssignmentRepository.delete(projectAssignment);
        return true;
    }

    public ProjectAssignmentResponseDTO createProjectAssignment(ProjectAssignmentRequestDTO projectAssignmentRequestDTO) {
        ProjectAssignment toSave = projectAssignmentMapper.toEntity(projectAssignmentRequestDTO);
        toSave.setProject(projectRepository.findById(projectAssignmentRequestDTO.projectId()).orElseThrow());
        toSave.setUser(appUserRepository.findById(projectAssignmentRequestDTO.userId()).orElseThrow());
        return projectAssignmentMapper.toDto(projectAssignmentRepository.save(toSave));
    }

    //not updatable
}
