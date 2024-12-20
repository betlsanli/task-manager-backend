package com.example.tm.dto.Project;

import com.example.tm.enums.Priority.Priority;


public record ProjectTaskPriorityCountDTO(
        Priority priority,
        long count
) {
}
