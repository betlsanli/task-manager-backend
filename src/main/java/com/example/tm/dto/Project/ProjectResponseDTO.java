package com.example.tm.dto.Project;


import java.time.LocalDateTime;

public record ProjectResponseDTO(
        String title,
        String description,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt
) {
}
