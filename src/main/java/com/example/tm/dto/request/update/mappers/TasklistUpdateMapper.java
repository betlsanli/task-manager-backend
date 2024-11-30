package com.example.tm.dto.request.update.mappers;

import com.example.tm.dto.request.update.TasklistUpdate;
import com.example.tm.entities.Tasklist;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class TasklistUpdateMapper {

    public Tasklist toEntity(TasklistUpdate dto){
        return Tasklist.builder()
                .title(dto.title())
                .description(dto.description())
                .lastModifiedAt(LocalDateTime.now())
                .build();
    }
}
