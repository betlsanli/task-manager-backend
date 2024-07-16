package com.example.todo.services;

import com.example.todo.dto.AppUserDTO;
import com.example.todo.dto.mappers.AppUserDTOMapper;
import com.example.todo.entities.AppUser;
import com.example.todo.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppUserService {


    private final AppUserRepository appUserRepository;
    private final AppUserDTOMapper appUserDTOMapper;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, AppUserDTOMapper appUserDTOMapper) {
        this.appUserRepository = appUserRepository;
        this.appUserDTOMapper = appUserDTOMapper;
    }

    public List<AppUserDTO> getAll() {
        return appUserRepository.findAll().stream().map(appUserDTOMapper::apply).collect(Collectors.toList());
    }

    public Optional<AppUserDTO> getById(UUID id) {
        return appUserRepository.findById(id).stream().map(appUserDTOMapper::apply).findFirst();
    }

    public AppUser save(AppUser appUser) {
        appUser.setLastModifiedAt(LocalDateTime.now());
        return appUserRepository.save(appUser);
    }

    public void deleteById(UUID id) {
        appUserRepository.deleteById(id);
    }
}
