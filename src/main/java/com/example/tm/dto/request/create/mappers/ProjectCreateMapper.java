package com.example.tm.dto.request.create.mappers;

import com.example.tm.dto.request.create.ProjectCreate;
import com.example.tm.entities.Project;
import org.springframework.stereotype.Service;

@Service
public class ProjectCreateMapper {
    public Project toEntity(ProjectCreate dto){
        return Project.builder()
                .title(dto.title())
                .description(dto.description())
                .build();
    }
}
