package com.example.tm.dto.Task;

import com.example.tm.entities.Task;
import com.example.tm.enums.Priority.Priority;
import com.example.tm.enums.Status.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(
        componentModel = "spring"
)
public interface TaskMapper {

    @Mappings({
            @Mapping(target = "projectId", source = "project.id"),
            @Mapping(target = "priorityStr", source = "priority.toString()"),
            @Mapping(target = "statusStr", source = "status.toString()"),
    })
    TaskDTO toDto(Task task);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "lastModifiedAt", ignore = true),
            @Mapping(target = "project.id", source = "projectId"),
            @Mapping(target = "priority", source = "priorityStr", qualifiedByName = "toPriority"),
            @Mapping(target = "status", source = "statusStr", qualifiedByName = "toStatus"),
    })
    Task toEntity(TaskDTO taskDTO);

    @Named("toPriority")
    default Priority toPriority(String priority){
        return priority == null ? Priority.MEDIUM : Priority.valueOf(priority);
    }

    @Named("toStatus")
    default Status toStatus(String status){
        return status == null ? Status.TO_DO : Status.valueOf(status);
    }
}
