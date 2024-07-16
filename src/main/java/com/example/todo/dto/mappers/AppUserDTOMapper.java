package com.example.todo.dto.mappers;

import com.example.todo.dto.AppUserDTO;
import com.example.todo.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AppUserDTOMapper implements Function<AppUser, AppUserDTO> {
    private final TasklistDTOMapper tasklistDTOMapper;

    @Autowired
    public AppUserDTOMapper(TasklistDTOMapper tasklistDTOMapper) {
        this.tasklistDTOMapper = tasklistDTOMapper;
    }

    @Override
    public AppUserDTO apply(AppUser user) {
        return new AppUserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getTasklists().stream().map(tasklistDTOMapper).collect(Collectors.toList())
        );
    }

    public AppUser toEntity(AppUserDTO userDTO) {
        return AppUser.builder()
                .id(userDTO.id())
                .email(userDTO.email())
                .firstName(userDTO.firstName())
                .lastName(userDTO.lastName())
                .password("")
                .build();
    }
}
