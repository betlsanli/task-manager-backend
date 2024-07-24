package com.example.todo.dto.request.update;

import com.example.todo.entities.AppUser;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TaskUpdate(
        @NotEmpty(message = "Title cannot be null or empty")
        String title,
        String description,
        String priority,
        String status,
        LocalDateTime dueDate,
        LocalDateTime startedAt,
        LocalDateTime completedAt,
        UUID parentId,
        @NotNull(message = "List id cannot be null or empty")
        List<UUID> assignees
) {}
