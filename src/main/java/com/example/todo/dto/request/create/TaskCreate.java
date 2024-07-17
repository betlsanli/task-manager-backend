package com.example.todo.dto.request.create;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskCreate(
        @NotEmpty(message = "Title cannot be null or empty")
        String title,
        String description,
        String priority,
        String status,
        LocalDateTime dueDate,
        UUID parentId,
        @NotEmpty(message = "Listid cannot be null or empty")
        UUID listId
) {}
