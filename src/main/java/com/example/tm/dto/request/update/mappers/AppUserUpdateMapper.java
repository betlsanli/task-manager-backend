package com.example.tm.dto.request.update.mappers;

import com.example.tm.dto.request.update.AppUserUpdate;
import com.example.tm.entities.AppUser;
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
