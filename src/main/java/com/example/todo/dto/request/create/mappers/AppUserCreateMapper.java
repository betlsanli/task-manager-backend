package com.example.todo.dto.request.create.mappers;

import com.example.todo.dto.request.create.AppUserCreate;
import com.example.todo.entities.AppUser;
import com.example.todo.entities.Tasklist;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AppUserCreateMapper {
    public AppUser toEntity(AppUserCreate dto){
        return AppUser.builder()
                .email(dto.email())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .tasklists(Collections.<Tasklist>emptyList())
                .password(dto.password())
                .build();
    }
}
