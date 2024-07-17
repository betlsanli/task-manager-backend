package com.example.todo.dto.request.update;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record TasklistUpdate (
        @NotEmpty(message = "Title cannot be null or empty")
        String title,
        String description,
        List<UUID> taskIds,
        @NotEmpty(message = "List must have at least one user")
        List<UUID> userIds
){}