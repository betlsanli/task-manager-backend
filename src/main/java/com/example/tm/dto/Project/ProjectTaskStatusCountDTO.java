package com.example.tm.dto.Project;

import com.example.tm.enums.Status.Status;

public record ProjectTaskStatusCountDTO(
        Status status,
        long count
) {}
