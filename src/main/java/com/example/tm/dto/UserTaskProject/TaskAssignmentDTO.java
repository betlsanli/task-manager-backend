package com.example.tm.dto.UserTaskProject;

import java.util.UUID;

public record TaskAssignmentDTO(
        UUID userId,
        String role
) {}
