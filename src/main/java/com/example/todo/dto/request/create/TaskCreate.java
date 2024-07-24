package com.example.todo.dto.request.create;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TaskCreate(
        @NotEmpty(message = "Title cannot be null or empty")
        String title,
        String description,
        String priority,
        String status,
        LocalDateTime dueDate,
        UUID parentId,
        @NotNull(message = "Listid cannot be null or empty")
        UUID listId,
        List<UUID> assignees
) {}
