package com.example.tm.dto.request.create;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record ProjectCreate(
        @NotEmpty(message = "Title cannot be null or empty")
        String title,
        String description,
        @NotEmpty(message = "List must have at list one user")
        List<UUID> userIds
) {}
