package com.example.tm.dto.ProjectAssignment;

import com.example.tm.dto.AppUser.AppUserMapper;
import com.example.tm.entities.ProjectAssignment;
import com.example.tm.entities.UserProjectRoleID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class ProjectAssignmentMapper {


    private final AppUserMapper appUserMapper;

    @Autowired
    public ProjectAssignmentMapper(AppUserMapper appUserMapper) {
        this.appUserMapper = appUserMapper;
    }

    public ProjectAssignmentResponseDTO toDto(ProjectAssignment projectAssignment) {

        return new ProjectAssignmentResponseDTO(
                appUserMapper.toDto(projectAssignment.getUser()),
                projectAssignment.getId().getProjectId(),
                projectAssignment.getId().getRole().toString()
        );
    }
    public List<ProjectAssignmentResponseDTO> toDtos(List<ProjectAssignment> projectAssignments) {
        List<ProjectAssignmentResponseDTO> dtos = new ArrayList<>();
        for (ProjectAssignment projectAssignment : projectAssignments) {
            dtos.add(toDto(projectAssignment));
        }
        return dtos;
    }

    public ProjectAssignment toEntity(ProjectAssignmentRequestDTO dto){
        return ProjectAssignment.builder()
                .id(new UserProjectRoleID(dto.userId(),dto.projectId(),dto.role()))
                .build();
    }

    public List<ProjectAssignment> toEntities(List<ProjectAssignmentRequestDTO> dtos){
        List<ProjectAssignment> projectAssignments = new ArrayList<>();
        for (ProjectAssignmentRequestDTO dto : dtos) {
            projectAssignments.add(toEntity(dto));
        }
        return projectAssignments;
    }


}
