package com.example.tm.dto.Task;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TaskRequestDTO(
        @NotEmpty
        String title,
        String description,
        String priority,
        String status,
        LocalDateTime dueDate,
        LocalDateTime startedAt,
        LocalDateTime completedAt,
        @NotNull
        UUID projectId,
        List<UUID> assignees
) {}
