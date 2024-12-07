package com.example.tm.dto.Task;

import com.example.tm.dto.UserTaskProject.TaskAssignmentDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TaskDTO(
        UUID taskId,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt,
        String title,
        String description,
        String priority,
        String status,
        LocalDateTime dueDate,
        LocalDateTime startedAt,
        LocalDateTime completedAt,
        UUID projectId,
        List<TaskAssignmentDTO> taskAssignments
) {}
