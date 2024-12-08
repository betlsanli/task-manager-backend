package com.example.tm.dto.AppUser;
import com.example.tm.entities.AppUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserMapper {

    public AppUserResponseDTO toDto(AppUser user){
        return new AppUserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }
    public List<AppUserResponseDTO> toDtos(List<AppUser> users){
        List<AppUserResponseDTO> dtos = new ArrayList<>();
        for(AppUser user : users){
            dtos.add(toDto(user));
        }
        return dtos;
    }

    public AppUser toEntity(AppUserRequestDTO dto){
        return AppUser.builder()
                .email(dto.email())
                .password(dto.password())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .build();
    }

    public List<AppUser> toEntities(List<AppUserRequestDTO> dtos){
        List<AppUser> entities = new ArrayList<>();
        for(AppUserRequestDTO dto : dtos){
            entities.add(toEntity(dto));
        }
        return entities;
    }

}
