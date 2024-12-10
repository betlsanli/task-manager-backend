package com.example.tm.dto.Project;
import com.example.tm.entities.Project;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectMapper {

    public ProjectResponseDTO toDto(Project project){
        return new ProjectResponseDTO(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getCreatedAt(),
                project.getLastModifiedAt()
        );
    }

    public List<ProjectResponseDTO> toDtos(List<Project> projects){
        List<ProjectResponseDTO> dtos = new ArrayList<ProjectResponseDTO>();
        for(Project project : projects){
            dtos.add(toDto(project));
        }
        return dtos;
    }

    public Project toEntity(ProjectRequestDTO dto){
        return Project.builder()
                .title(dto.title())
                .description(dto.description())
                .build();
    }

    public List<Project> toEntities(List<ProjectRequestDTO> dtos){
        List<Project> projects = new ArrayList<>();
        for(ProjectRequestDTO dto : dtos){
            projects.add(toEntity(dto));
        }
        return projects;
    }

}
