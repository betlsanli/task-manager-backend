package com.example.tm.dto.response;

import com.example.tm.entities.AppUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TaskResponseDTO (
        UUID id,
        String title,
        String description,
        String priority,
        String status,
        LocalDateTime dueDate,
        LocalDateTime startedAt,
        LocalDateTime completedAt,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt,
        UUID parentId,
        UUID listId,
        List<TaskResponseDTO> subTasks,
        List<AppUser> assignees
){}
