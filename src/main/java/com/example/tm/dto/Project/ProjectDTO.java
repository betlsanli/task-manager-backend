package com.example.tm.dto.Project;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProjectDTO(
        UUID projectId,
        @NotEmpty(message = "Title cannot be null or empty")
        String title,
        String description,
        @NotNull(message = "Tasks cannot be null")
        List<UUID> taskIds,
        @NotEmpty(message = "Project must have at least one user")
        List<UUID> userIds,
        @NotNull(message = "There must be a manager in a project")
        UUID managerId,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt
) {}
