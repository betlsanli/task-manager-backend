package com.example.todo.dto.request.update.mappers;

import com.example.todo.dto.request.update.AppUserUpdate;
import com.example.todo.entities.AppUser;
import com.example.todo.entities.Tasklist;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AppUserUpdateMapper {
    public AppUser toEntity(AppUserUpdate dto, UUID userId, List<Tasklist> tasklists){
        return AppUser.builder()
                .id(userId)
                .email(dto.email())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .password(dto.password())
                .tasklists(tasklists)
                .lastModifiedAt(LocalDateTime.now())
                .build();
    }
}
