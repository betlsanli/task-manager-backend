package com.example.tm.dto.request.update.mappers;

import com.example.tm.dto.request.update.TaskUpdate;
import com.example.tm.enums.Priority.Priority;
import com.example.tm.enums.Status.Status;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TaskUpdateMapper {
    public Task toEntity(TaskUpdate dto){
        return Task.builder()
                .title(dto.title())
                .description(dto.description())
                .priority(dto.priority() != null ? Priority.valueOf(dto.priority()) : Priority.MEDIUM)
                .status(dto.status() != null ? Status.valueOf(dto.status()) : Status.TO_DO)
                .dueDate(dto.dueDate())
                .lastModifiedAt(LocalDateTime.now())
                .startedAt(dto.startedAt())
                .completedAt(dto.completedAt())
                .assignees(dto.assignees())
                .build();
    }
}
