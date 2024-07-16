package com.example.todo.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TaskDTO(
        UUID id,
        String title,
        String desc,
        String priority,
        String status,
        List<TaskDTO> subTasks,
        LocalDateTime createdAt,
        LocalDateTime completedAt,
        LocalDateTime dueAt
) {}
