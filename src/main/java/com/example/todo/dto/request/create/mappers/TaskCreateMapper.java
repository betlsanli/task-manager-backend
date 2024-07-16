package com.example.todo.dto.request.create.mappers;

import com.example.todo.dto.request.create.TaskCreate;
import com.example.todo.entities.Priority;
import com.example.todo.entities.Status;
import com.example.todo.entities.Task;
import com.example.todo.entities.Tasklist;
import org.springframework.stereotype.Service;

@Service
public class TaskCreateMapper {
    public Task toEntity(TaskCreate dto, Task parentTask, Tasklist belongTo) {
        return Task.builder()
                .title(dto.title())
                .description(dto.description())
                .priority(Priority.valueOf(dto.priority()))
                .status(Status.valueOf(dto.status()))
                .dueDate(dto.dueDate())
                .parentTask(parentTask)
                .belongsTo(belongTo)
                .build();
    }
}
