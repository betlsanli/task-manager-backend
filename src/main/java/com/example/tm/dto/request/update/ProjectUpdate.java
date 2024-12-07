package com.example.tm.dto.request.update;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ProjectUpdate(
        @NotEmpty(message = "Title cannot be null or empty")
        String title,
        String description,
        @NotNull(message = "Tasks cannot be null")
        List<UUID> taskIds,
        @NotEmpty(message = "List must have at least one user")
        List<UUID> userIds
){}