package com.example.todo.dto.request.update.mappers;

import com.example.todo.dto.request.update.AppUserUpdate;
import com.example.todo.entities.AppUser;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
public class AppUserUpdateMapper {
    public AppUser toEntity(AppUserUpdate dto){
        return AppUser.builder()
                .email(dto.email())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .password(dto.password())
                .lastModifiedAt(LocalDateTime.now())
                .build();
    }
}
