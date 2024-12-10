package com.example.tm.dto.Task;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TaskRequestDTO(
        @NotEmpty(message = "title cannot be null or empty")
        String title,
        String description,
        String priority,
        String status,
        LocalDateTime dueDate,
        LocalDateTime startedAt,
        LocalDateTime completedAt,
        @NotNull(message = "projectId cannot be null")
        UUID projectId,
        @NotNull(message = "assignees cannot be null")
        List<UUID> assignees
) {}
