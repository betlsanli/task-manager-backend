package com.example.tm.dto.Project;


import java.time.LocalDateTime;
import java.util.UUID;

public record ProjectResponseDTO(
        UUID id,
        String title,
        String description,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt
) {
}
