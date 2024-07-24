package com.example.todo.dto.request.create.mappers;

import com.example.todo.dto.request.create.TaskCreate;
import com.example.todo.enums.Priority.Priority;
import com.example.todo.enums.Status.Status;
import com.example.todo.entities.Task;
import com.example.todo.entities.Tasklist;
import org.springframework.stereotype.Service;

@Service
public class TaskCreateMapper {
    public Task toEntity(TaskCreate dto) {
        return Task.builder()
                .title(dto.title())
                .description(dto.description())
                .priority(dto.priority() != null ? Priority.valueOf(dto.priority()) : Priority.MEDIUM)
                .status(dto.status() != null ? Status.valueOf(dto.status()) : Status.TO_DO)
                .dueDate(dto.dueDate())
                .build();
    }
}
