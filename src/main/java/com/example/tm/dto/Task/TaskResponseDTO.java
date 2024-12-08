package com.example.tm.dto.Task;


import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponseDTO(
        String title,
        String description,
        String priority,
        String status,
        LocalDateTime dueDate,
        LocalDateTime startedAt,
        LocalDateTime completedAt,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt,
        UUID projectId
) {
}
