package com.example.tm.dto.Task;


import com.example.tm.dto.AppUser.AppUserResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TaskResponseDTO(
        UUID id,
        String title,
        String description,
        String priority,
        String status,
        LocalDateTime dueDate,
        LocalDateTime startedAt,
        LocalDateTime completedAt,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt,
        UUID projectId,
        List<AppUserResponseDTO> assignees
) {
}
