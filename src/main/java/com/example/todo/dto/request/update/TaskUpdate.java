package com.example.todo.dto.request.update;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskUpdate(
        String title,
        String description,
        String priority,
        String status,
        LocalDateTime dueDate,
        LocalDateTime lastModifiedAt,
        LocalDateTime startedAt,
        LocalDateTime completedAt,
        UUID parentId,
        UUID listId
) {}
