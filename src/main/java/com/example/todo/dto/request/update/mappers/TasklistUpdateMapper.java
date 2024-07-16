package com.example.todo.dto.request.update.mappers;

import com.example.todo.dto.request.update.TasklistUpdate;
import com.example.todo.entities.AppUser;
import com.example.todo.entities.Task;
import com.example.todo.entities.Tasklist;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TasklistUpdateMapper {

    public Tasklist toEntity(TasklistUpdate dto, UUID listId, List<Task> tasks, List<AppUser> appUsers){
        return Tasklist.builder()
                .id(listId)
                .title(dto.title())
                .description(dto.description())
                .lastModifiedAt(LocalDateTime.now())
                .tasks(tasks)
                .users(appUsers)
                .build();
    }
}
