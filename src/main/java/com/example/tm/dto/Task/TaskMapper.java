package com.example.tm.dto.Task;

import com.example.tm.dto.AppUser.AppUserMapper;
import com.example.tm.entities.Task;
import com.example.tm.enums.Priority.Priority;
import com.example.tm.enums.Status.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskMapper {
    private final AppUserMapper appUserMapper;

    @Autowired
    public TaskMapper(AppUserMapper appUserMapper) {
        this.appUserMapper = appUserMapper;
    }

    public TaskResponseDTO toDto(Task task){
        return new TaskResponseDTO(
                task.getTitle(),
                task.getDescription(),
                task.getPriority().toString(),
                task.getStatus().toString(),
                task.getDueDate(),
                task.getStartedAt(),
                task.getCompletedAt(),
                task.getCreatedAt(),
                task.getLastModifiedAt(),
                task.getProject().getId(),
                appUserMapper.toDtos(task.getAssignees())
        );
    }
    public List<TaskResponseDTO> toDtos(List<Task> tasks){
        List<TaskResponseDTO> dtos = new ArrayList<TaskResponseDTO>();
        for(Task task : tasks){
            dtos.add(toDto(task));
        }
        return dtos;
    }

    public Task toEntity(TaskRequestDTO dto){
        return Task.builder()
                .title(dto.title())
                .description(dto.description())
                .priority(Priority.valueOf(dto.priority()))
                .status(Status.valueOf(dto.status()))
                .dueDate(dto.dueDate())
                .startedAt(dto.startedAt())
                .completedAt(dto.completedAt())
                .build();
    }
    public List<Task> toEntities(List<TaskRequestDTO> dtos){
        List<Task> tasks = new ArrayList<>();
        for(TaskRequestDTO dto : dtos){
            tasks.add(toEntity(dto));
        }
        return tasks;
    }
}
