package com.example.todo.services;

import com.example.todo.entities.AppUser;
import com.example.todo.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppUserService {


    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public List<AppUser> getAll() {
        return appUserRepository.findAll();
    }

    public Optional<AppUser> getById(UUID id) {
        return appUserRepository.findById(id);
    }

    public AppUser save(AppUser appUser) {
        appUser.setLastModifiedAt(LocalDateTime.now());
        return appUserRepository.save(appUser);
    }

    public void deleteById(UUID id) {
        appUserRepository.deleteById(id);
    }
}
