package com.example.todo.dto;

import java.util.List;
import java.util.UUID;

public record TasklistDTO (
        UUID id,
        String title,
        String desc,
        List<TaskDTO> tasks
){}
