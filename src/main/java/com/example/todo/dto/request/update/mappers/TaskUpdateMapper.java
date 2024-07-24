package com.example.todo.dto.request.update.mappers;

import com.example.todo.dto.request.update.TaskUpdate;
import com.example.todo.entities.AppUser;
import com.example.todo.enums.Priority.Priority;
import com.example.todo.enums.Status.Status;
import com.example.todo.entities.Task;
import com.example.todo.entities.Tasklist;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

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
                .build();
    }
}
