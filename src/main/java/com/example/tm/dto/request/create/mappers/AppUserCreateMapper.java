package com.example.tm.dto.request.create.mappers;

import com.example.tm.dto.request.create.AppUserCreate;
import com.example.tm.entities.AppUser;
import com.example.tm.entities.Project;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AppUserCreateMapper {
    public AppUser toEntity(AppUserCreate dto){
        return AppUser.builder()
                .email(dto.email())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .projects(Collections.<Project>emptyList())
                .password(dto.password())
                .build();
    }
}
