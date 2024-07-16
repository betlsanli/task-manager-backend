package com.example.todo.dto.request.create.mappers;

import com.example.todo.dto.request.create.TasklistCreate;
import com.example.todo.entities.AppUser;
import com.example.todo.entities.Tasklist;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasklistCreateMapper {
    public Tasklist toEntity(TasklistCreate dto, List<AppUser> users){
        return Tasklist.builder()
                .title(dto.title())
                .description(dto.description())
                .users(users)
                .build();
    }
}
