package com.example.tm.dto.AppUser;
import com.example.tm.entities.AppUser;
import com.example.tm.security.CustomUserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserMapper {

    private final PasswordEncoder passwordEncoder;

    public AppUserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public AppUserResponseDTO toDto(AppUser user){
        return new AppUserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.isAdmin()
        );
    }
    public AppUserResponseDTO toDto(CustomUserDetails user){
        return new AppUserResponseDTO(
                user.getUserId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.hasAuthority("ROLE_ADMIN") ? true : false
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
                .password(passwordEncoder.encode(dto.password()))
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .isAdmin(dto.isAdmin())
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
