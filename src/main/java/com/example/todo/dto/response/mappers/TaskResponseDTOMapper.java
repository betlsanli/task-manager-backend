package com.example.todo.dto.response.mappers;

import com.example.todo.dto.response.TaskResponseDTO;
import com.example.todo.entities.Task;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TaskResponseDTOMapper {
    public TaskResponseDTO toDTO(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority().toString(),
                task.getStatus().toString(),
                task.getDueDate(),
                task.getStartedAt(),
                task.getCompletedAt(),
                task.getCreatedAt(),
                task.getLastModifiedAt(),
                (task.getParentTask() != null ? task.getParentTask().getId() : null),
                task.getBelongsTo().getId(),
                task.getSubTasks().stream().map(this::toDTO).collect(Collectors.toList()),
                task.getAssignees()
        );
    }
}
