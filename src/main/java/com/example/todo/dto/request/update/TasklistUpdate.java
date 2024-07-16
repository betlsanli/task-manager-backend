package com.example.todo.dto.request.update;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TasklistUpdate (
        String title,
        String description,
        LocalDate lastModifiedAt,
        List<UUID> taskIds,
        List<UUID> userIds
){}