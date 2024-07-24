package com.example.todo.dto.request.create.mappers;

import com.example.todo.dto.request.create.TasklistCreate;
import com.example.todo.entities.Tasklist;
import org.springframework.stereotype.Service;

@Service
public class TasklistCreateMapper {
    public Tasklist toEntity(TasklistCreate dto){
        return Tasklist.builder()
                .title(dto.title())
                .description(dto.description())
                .build();
    }
}
