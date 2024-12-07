package com.example.tm.dto.Project;
import com.example.tm.entities.AppUser;
import com.example.tm.entities.Project;
import com.example.tm.entities.UserTaskProject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    @Mappings({
            @Mapping(target = "taskIds", source = "assignments", qualifiedByName = "toTaskIds"),
            @Mapping(target = "userIds", source = "users", qualifiedByName = "toUserIds"),
            @Mapping(target = "managerId", source = "manager.id"),
    })
    ProjectDTO toDto(Project project);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "lastModifiedAt", ignore = true),
            @Mapping(target = "manager.id", source = "managerId"),
            @Mapping(target = "users", source = "userIds", qualifiedByName = "toUsers"),
            @Mapping(target = "assignments", ignore = true),
            @Mapping(target = "projectId", ignore = true),
    })
    Project toEntity(ProjectDTO projectDTO);

    List<AppUser> toUsers(List<UUID> userIds);
    List<UUID> toUserIds(List<AppUser> users);
    default List<UUID> toTaskIds(List<UserTaskProject> assignments) {
        List<UUID> taskIds = new ArrayList<>();
        for (UserTaskProject assignment : assignments) {
            taskIds.add(assignment.getTask().getId());
        }
        return taskIds;
    }

}
