package com.example.todo.services;

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

    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
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

    public AppUser save(AppUser appUser) {
        appUser.setLastModifiedAt(LocalDateTime.now());
        return appUserRepository.save(appUser);
    }

    public void deleteById(UUID id) {
        appUserRepository.deleteById(id);
    }
}
