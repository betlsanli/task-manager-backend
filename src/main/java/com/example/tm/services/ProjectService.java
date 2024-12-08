package com.example.tm.services;

import com.example.tm.dto.Project.ProjectRequestDTO;
import com.example.tm.dto.Project.ProjectMapper;
import com.example.tm.dto.Project.ProjectResponseDTO;
import com.example.tm.entities.Project;
import com.example.tm.repositories.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    //private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public List<ProjectResponseDTO> getAll() {
        return projectMapper.toDtos(projectRepository.findAll());
    }

    public ProjectResponseDTO getById(UUID id) {
       return projectMapper.toDto(projectRepository.findById(id).orElseThrow());
    }

    public List<ProjectResponseDTO> getAllByIds(List<UUID> ids) {
        return projectMapper.toDtos(projectRepository.findAllById(ids));
    }

    public boolean deleteById(UUID id) {
        getById(id);
        projectRepository.deleteById(id);
        return true;
    }

    @Transactional
    public ProjectResponseDTO createProject(ProjectRequestDTO newProject) {

        Project toSave = projectMapper.toEntity(newProject);
        return projectMapper.toDto(projectRepository.save(toSave));
    }
    @Transactional
    public ProjectResponseDTO updateProject(UUID projectId, ProjectRequestDTO updatedProject) {

       // Project oldProject = projectRepository.findById(projectId).orElseThrow();

        Project newProject = projectMapper.toEntity(updatedProject);
        newProject.setId(projectId);

        return projectMapper.toDto(projectRepository.save(newProject));
    }
}
