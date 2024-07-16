package com.example.todo.services;

import com.example.todo.dto.request.create.AppUserCreate;
import com.example.todo.dto.request.create.mappers.AppUserCreateMapper;
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

@Service
public class AppUserService {


    private final AppUserRepository appUserRepository;
    private static final Logger log = LoggerFactory.getLogger(AppUserService.class);
    private final AppUserCreateMapper appUserCreateMapper;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, AppUserCreateMapper appUserCreateMapper) {
        this.appUserRepository = appUserRepository;
        this.appUserCreateMapper = appUserCreateMapper;
    }

    public List<AppUser> getAll() {
        return appUserRepository.findAll();
    }

    public AppUser getById(UUID id) {
        try {
            return appUserRepository.findById(id).orElseThrow();
        }catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return null;
        }
    }
    public List<AppUser> getAllByIds(List<UUID> ids) {
        return appUserRepository.findAllById(ids);
    }

    public AppUser createUser(AppUserCreate newUser) {
        if (newUser == null) {
            return null;
        }
        return appUserRepository.save(appUserCreateMapper.toEntity(newUser));
    }

    public void deleteById(UUID id) {
        appUserRepository.deleteById(id);
    }
}
