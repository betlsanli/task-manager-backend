package com.example.todo.services;

import com.example.todo.dto.AppUserDTO;
import com.example.todo.dto.mappers.AppUserDTOMapper;
import com.example.todo.entities.AppUser;
import com.example.todo.repositories.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppUserService {


    private final AppUserRepository appUserRepository;
    private final AppUserDTOMapper appUserDTOMapper;
    private static final Logger log = LoggerFactory.getLogger(AppUserService.class);

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, AppUserDTOMapper appUserDTOMapper) {
        this.appUserRepository = appUserRepository;
        this.appUserDTOMapper = appUserDTOMapper;
    }

    public List<AppUserDTO> getAll() {
        return appUserRepository.findAll().stream().map(appUserDTOMapper::apply).collect(Collectors.toList());
    }

    public AppUserDTO getById(UUID id) {
        try {
            return appUserRepository.findById(id).stream().map(appUserDTOMapper::apply).findFirst().orElseThrow();
        }catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public AppUser save(AppUser appUser) {
        appUser.setLastModifiedAt(LocalDateTime.now());
        return appUserRepository.save(appUser);
    }

    public void deleteById(UUID id) {
        appUserRepository.deleteById(id);
    }
}
