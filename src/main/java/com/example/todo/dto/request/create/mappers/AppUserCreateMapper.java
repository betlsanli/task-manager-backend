package com.example.todo.dto.request.create.mappers;

import com.example.todo.dto.request.create.AppUserCreate;
import com.example.todo.entities.AppUser;
import org.springframework.stereotype.Service;

@Service
public class AppUserCreateMapper {
    public AppUser toEntity(AppUserCreate dto){
        return AppUser.builder()
                .email(dto.email())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .password(dto.password())
                .build();
    }
}
