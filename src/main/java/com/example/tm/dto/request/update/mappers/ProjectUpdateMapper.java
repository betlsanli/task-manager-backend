package com.example.tm.dto.request.update.mappers;

import com.example.tm.dto.request.update.ProjectUpdate;
import com.example.tm.entities.Project;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ProjectUpdateMapper {

    public Project toEntity(ProjectUpdate dto){
        return Project.builder()
                .title(dto.title())
                .description(dto.description())
                .lastModifiedAt(LocalDateTime.now())
                .build();
    }
}
