package com.example.todo.dto.request.create;

import java.util.List;
import java.util.UUID;

public record TasklistCreate(
        String title,
        String description,
        List<UUID> userIds
) {}
