package com.example.tm.dto.ProjectAssignment;

import com.example.tm.dto.AppUser.AppUserMapper;

import com.example.tm.entities.ProjectAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",
uses = {AppUserMapper.class})
public interface ProjectAssignmentMapper {

    @Mappings({
            @Mapping(target = "id", source = "new UserProjectRoleID(userId, projectId, role)"),
    })
    ProjectAssignment toEntity(ProjectAssignmentRequestDTO projectAssignmentRequestDTO);

    @Mappings({
            @Mapping(target = "userDto", source = "user"),
            @Mapping(target = "roleStr", source = "role.toString()")
    })
    ProjectAssignmentResponseDTO toDto(ProjectAssignment projectAssignment);

}
