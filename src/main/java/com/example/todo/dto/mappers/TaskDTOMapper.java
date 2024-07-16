package com.example.todo.dto.mappers;

import com.example.todo.dto.TaskDTO;
import com.example.todo.entities.Task;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TaskDTOMapper implements Function<Task, TaskDTO> {

    @Override
    public TaskDTO apply(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority().toString(),
                task.getStatus().toString(),
                task.getSubTasks().stream().map(this::apply).collect(Collectors.toList()),
                task.getCreatedAt(),
                task.getCompletedAt(),
                task.getDueDate()
        );
    }

    public Task toEntity(TaskDTO dto) {
        return Task.builder()
                .id(dto.id())
                .title(dto.title())
                .build();
    }
}
