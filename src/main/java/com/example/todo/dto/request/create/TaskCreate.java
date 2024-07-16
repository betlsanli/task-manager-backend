package com.example.todo.dto.request.create;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskCreate(
        String title,
        String description,
        String priority,
        String status,
        LocalDateTime dueDate,
        UUID parentId,
        UUID listId
) {}
