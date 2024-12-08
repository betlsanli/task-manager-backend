package com.example.tm.dto.ProjectAssignment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProjectAssignmentRequestDTO(
        @NotNull
        UUID userId,
        @NotNull
        UUID projectId,
        @NotEmpty
        String role
) {}
