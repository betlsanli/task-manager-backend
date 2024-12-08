package com.example.tm.dto.Project;

import jakarta.validation.constraints.NotEmpty;


public record ProjectRequestDTO(
        @NotEmpty(message = "Title cannot be null or empty")
        String title,
        String description
) {}
