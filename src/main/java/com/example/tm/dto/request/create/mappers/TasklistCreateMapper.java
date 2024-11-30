package com.example.tm.dto.request.create.mappers;

import com.example.tm.dto.request.create.TasklistCreate;
import com.example.tm.entities.Tasklist;
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
