package com.example.todo.dto.mappers;

import com.example.todo.dto.TasklistDTO;
import com.example.todo.entities.Tasklist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TasklistDTOMapper implements Function<Tasklist, TasklistDTO> {

    private final TaskDTOMapper taskDTOMapper;

    @Autowired
    public TasklistDTOMapper(TaskDTOMapper taskDTOMapper) {
        this.taskDTOMapper = taskDTOMapper;
    }

    @Override
    public TasklistDTO apply(Tasklist tasklist) {
        return new TasklistDTO(
                tasklist.getId(),
                tasklist.getTitle(),
                tasklist.getDescription(),
                tasklist.getTasks().stream().map(taskDTOMapper::apply).collect(Collectors.toList())
        );
    }

    public Tasklist toEntity(TasklistDTO tasklistDTO) {
        return Tasklist.builder()
                .id(tasklistDTO.id())
                .title(tasklistDTO.title())
                .build();
    }
}
